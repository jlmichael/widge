widge
=====

A simple economic sim I've been messing around with, total work in progress.  This doc may not match what's in the code
just yet...

1 Introduction
==============

Widge is a very simple, turn-based economy simulator. Players in the game world extract raw materials, build a hierarchy
of goods, and buy and sell goods on the open market in an attempt to maximize their net worth. The game, such as it is,
consists of a set of server scripts, a database, and a public, RESTful API through which players submit their orders.
The scripts execute each turn, processing each player's commands, provide feedback, and execute market orders. Each
game ends when a certain number of turns are completed or certain global conditions are met.

1.1 Design Goals and Philosophy
===============================

I'm building Widge for a few reasons:

    to familiarize myself with building public, RESTful APIs and using enabling technologies like RESTlets.
    to build a service-based game with no UI, enabling myself and others to experiment with different front-end
    technologies

1.2 Gameplay Description
========================

A few of the elements of gameplay are described here.

1.2.1 Creating a player account
===============================

A user needs to create an account in order to play Widge. This document uses "player" and "player account" somewhat
interchangeably. A player, once created, can join multiple Widge game instances hosted on the server. To create a
player, the user submits a name and password to the server via the API, and an optional email address. The password gets
salted, md5'd, and the resulting digest is all that gets stored in the database. If the user submits an email address,
the game will use it to supply notifications of turn events, etc.

1.2.2 Joining a game
====================

A player can join multiple games of Widge. To do so, a user simply submits a join request via the API. Only games not
already in progress can be joined.

1.2.3 Extracting raw materials
==============================

A player can get raw materials for building products by extracting them from the world. This is done by submitting the
appropriate command for the upcoming turn via the API. This action costs a certain amount of money, which increases as
more players in the game extract that resource, simulating scarcity. Upon completion of the command, the player has the
cost deducted from his stores and a nearly-fixed (somewhat variable) amount of the extracted good added to their
holdings.

1.2.4 Building intermediate, finished goods
===========================================

Raw materials can be used to build intermediate and finished goods. For example, 4 units of Oil and 1 unit of Iron might
combine to form a unit of the intermediate good, Steel. Likewise, 2 units of Steel and 1 unit of Plastic (itself an
intermediate good) might combine to form a unit of the finished product, Automobile. All such builds have a fixed,
constant cost based on the number of units being combined. Players can request a list of all "recipes" like these from
the API. To build a specific good, the player submits the appropriate command via the API. When the command is executed,
the player has the cost of building deducted from their account, the input goods removed from their inventory, and the
intermediate or finished good added to their inventory.

1.2.5 Buying and selling goods
==============================

Depending on the economics of extraction and the state of the markets, it may be more economical for a player to
purchase the raw materials and/or intermediate goods needed to build a good on the market rather than go through the
process of extracting and building them. Or, if there is high demand for a good the player happens to have in abundance,
he can make good money by selling his surplus on the open market. A player can get the current list of "ask" (offers to
sell) and "bid" (offers to buy) offers for a particular good via the API. He can then issue his own ask or bid offer via
the API. If it is an ask offer, the quantity of goods to be sold is deducted from the player's stores and held in escrow
until the order is filled or canceled. Conversely, if it is a bid offer, the amount of money needed to cover the
purchase is deducted from the player's account and held in escrow until the order is filled or canceled. When a turn is
executed, and after all player commands have been processed, the server scripts attempt to fill any orders by matching
ask orders to appropriate bid orders. The exact algorithm for doing so will be discussed later. At the beginning of the
turn after an order is filled, the seller will have his account increased by the asking price * the number of units
sold. Any units remaining in the original ask offer are left on the market. Conversely, the buyer will have the
appropriate quantity of the good added to his inventory. If the order was filled at a price less than the original bid
price, the buyer will also receive any leftover monies in his account.

1.2.6 End of game and winning
=============================

The game ends when either a preset number of turns have been executed, or when a set of conditions are satisfied in the
world, such as a certain number of each finished product sold. When the server script detects that the end condition has
been met, it calculates each player's net worth. The algorithm for doing so will be described later, but briefly, it
amounts to totaling cash plus a fair market value for each good in the user's inventory. Players are then ranked by net
worth, with the highest being declared the winner. Points are then allotted to each participating player and tallied in
their player account record.

2 Game Objects
==============

What follows is a brief description of some of the objects in the game. I've split the list into Objects and Relations.
Objects are the standalone object in the game, and Relations are mapping objects that describe how the Object relate to
one another.

2.1 Objects
===========

2.1.2 Player
============

The Player object represents a player account in the game world. The Player consists of an id (never displayed,
invisible to all users, including the owner), a name, a password, a score, and an optional email address. Players can
join Games, and within a Game Players can hold goods, issue commands, extract resources, buy and sell goods, and
accumulate points.

2.1.2 Game
==========

The Game object represents a single instance of a Widge game on the server. Games define boundaries between players,
actions, goods, etc., such that each is self-contained and player actions do not span Game boundaries. A Game consists
of an (invisible) id, a name, and an ending condition. Games have Turns (see below).

2.1.3 Command
=============

The Command object represents a command that Players can issue. Each consists of an (invisible) id, a name, a function
name (invisible to players), and a description. The function name refers to the function the server executes in order to
implement the command. When a player issues a Command, it gets added to the queue as a PlayerCommand object (see below
under Relations)

2.1.4 Good
==========

A Good object represents a raw material or manufactured good in the game world. It consists of an (invisible) id, a
name, and a type (either 'RAW', 'INTERMEDIATE', or 'FINISHED'). Goods can be combined into other goods or sold on the
open market.

2.1.5 FabricationFormula
========================

A FabricationFormula object represents the recipe used to produce a Good from lower Goods in the hierarchy. It consists
of an (invisible) id, the finished Good being produced, an input Good needed to fabricate the finished good, and the
quantity of input needed. The set of all FabricationFormula objects with the same finished Good represents the total
recipe needed for that finished Good.

2.1.6 Turn
==========

A Turn object represents a sequential turn in a Game. For each Turn in a Game, a Player can execute a single Command. A
Turn consists of an (invisible) id, the Game to which the Turn refers, the sequential turn number, a start time for when
the Turn begins executing, and an end time for when the Turn execution ends.

2.1.7 MarketOrder
=================

A MarketOrder object represents a single order to either buy or sell the given Good at a given price. It consists of an
(invisible) id, the Game in which the MarketOrder exists, the Good being bought or sold, the Player executing the order,
the type of order (ask or bid), the quantity of the Good being bought or sold, and the time at which the order was
placed. When a MarketOrder is completely filled (quantity left to buy or sell is 0), it is removed from the game.

2.2 Relations
=============

2.2.1 PlayerGame
================

The PlayerGame object maps Players to Games. It consists of an (invisible) id, a Player, and a Game.

2.2.2 PlayerCommand
===================

The PlayerCommand object maps Players to Commands they have issued. For a given Game, Player, and Turn, only one
PlayerCommand object may exist, as Players can only execute one Command per Turn. It consists of an (invisible) id, the
Game to which it refers, the issuing Player, the Command being issued, four parameters (not all of which may need to be
used for every Command), the Turn it should be executed on, a timestamp for when it is executed, and a log of the
results of the execution.

2.2.3 PlayerGood
================

The PlayerGood object represents the Players' inventories by mapping Goods to Players. It consists of an (invisible) id,
the Game to which it refers, the Player holding the Good, the Good in question, and the quantity of that Good that the
user possesses.

2.2.4 FilledMarketOrder
=======================

The FilledMarketOrder represents the fulfillment of a pair of ask and bid MarketOrders. It consists of an (invisible)
id, the Game to which it refers, the ask MarketOrder that was filled, the bid MarketOrder that was filled, the quantity
of Goods that changed hands, the Turn at which the orders were filled, and the timestamp of the MarketOrders being
filled. The set of all FilledMarketOrder objects for a given Good represent the market activity for that Good. This set
of FilledMarketOrder objects are what get used in calculating fair market value of any remaining Goods at the end of the
Game.

3 Server Scripts, Processes, Algorithms, etc.
=============================================

This section documents how the server side game logic operates. The Widge server is very simple. Each heartbeat, it
checks the list of Game objects to see if any have a Turn that needs executing. For each one it finds, it executes the
Game Script (see below). It then updates the Game objects to note that the Turn was executed, and gives it a timestamp.
Any Players with an email address will have the results of the Turn compiled and sent to them in an email. Players
without an email address will have to fetch the results themselves by checking the log attribute of their PlayerCommand
objects and refreshing their list of FilledMarketOrder objects.

3.1 Game Script
===============

The Game Script is responsible for executing each Player's selected Command for the given Turn for which it is running.
At a pseudocode level, it is doing the following:

> for each Game with Turns ready to execute
>   get Turn to execute and mark as started
>   for each PlayerCommand for this Game and Turn
>     lookup Command object and get function name
>     execute function name with parameters provided in PlayerCommand
>     update PlayerCommand with timestamp, log of output
>   execute Market Script (see below)
>   mark Turn as finished
>   if Game's ending conditions are met
>     for each PlayerGame object for this Game
>       calculate Player's net worth using Net Worth Algorithm (see below)
>     rank players by Net Worth
>     allocate points to Players based on rank
>     notify Players of ranking

3.2 Market Script
=================

The Market Script is responsible for matching ask and bid MarketOrders to facilitate the buying and selling of Goods in
the world. In a nutshell, it is trying to find a seller of goods who is asking for a price less than what a buyer of
that good is offering. It fills the order at the asking price (not the bid price, importantly), and executes orders in
the order in which they are placed (advantaging faster players). At a pseudocode level, it is doing the following:

> get all MarketOrder objects and split into ask and bid lists
> sort both lists by order time
> for each ask MarketOrder
>   while ask MarketOrder's quantity > 0
>     for each bid MarketOrder
>       if bid price < ask price, continue
>       else we can fill these orders
>       quantity exchanged = max(bid quantity, ask quantity)
>       add quantity exchanged * ask price to seller's account
>       add/update PlayerGood object for buyer, adding quantity exchanged of this Good
>       create a new FilledMarketOrder object for this exchange
>       if quantity exchanged >= bid quantity
>         remove bid MarketOffer from bid list and from game
>         subtract quantity exchanged from ask MarketOffer's quantity
>         for each FilledMarketOffers for this bid offer
>           sum up all ask prices * quantity bought (get total cost)
>           sum up all quantity bought (get total units bought)
>         subtract total cost from total units bought * bid price
>         add this amount to buyer's account (credit them with unspent monies)
>       else
>         remove ask MarketOffer from ask list and from game
>         subtract quantity exchanged from bid MarketOffer's quantity
>         break
3.3 Net Worth Algorithm

The Net Worth Algorithm is used at the end of the game to determine a winner. In a nutshell, it is totaling each
Player's cash plus the "fair market value" of each Good in his inventory, where "fair market value" is a weighted
average of closing prices for the previous few turns. In pseudocode, it looks something like this:

> for each Good
>   for i = 0 .. 9
>     for each FilledMarketOrder object for this Good where Turn was i turns ago
>       total sales += quantity exchanged * ask price
>       total units sold += quantity
>     weighted price = total sales / total units sold * (1 - i * .1)
>     weighted numerator += weighted price
>     weighted denominator += (1 - i * .1)
>   fair market value (FMV) for this Good = weighted numerator / weighted denominator
>   hash the Good to its FMV
>
> for each PlayerGame object for this Game
>   get Player
>   net worth = Player's cash account
>   for each PlayerGood for this Game and Player
>     if Good type is FINISHED
>       call Simulated Demand Function for this Good (see below)
>       net worth += simulated demand price * quantity
>     else
>       get FMV for this Good from hash
>       net worth += FMV * quantity

3.4 Simulated Demand Function
=============================

As an economic sim, Widge faces a fundamental problem in that the Goods Players are supposed to be building have no
utility or value in and of themselves, except that they can be used to build higher Goods in the hierarchy. Goods that
are FINISHED, though, have absolutely no utility in the game, and hence all lower Goods also have no utility or value,
and so on down to RAW Goods. Absent some way of assigning or simulating demand for finished Goods, there would be
absolutely no market for any of the other Goods, and hence no reason to do anything in the game. This is kind of a
problem. There are a few ways I can think of to deal with this:

- You can make the production of finished products a necessary condition to end the game. Something like "The game ends
when 10 of Good X have been produced." But with no value to those Goods, every Player has a negative incentive to
produce them, which will lead to stalemates, I think.
- You can assign a fixed value for each finished Good, which the Player will get when he sells them. This would
basically model infinite demand at a set price. The problem with this is that it doesn't respond to market forces for
the lower Goods necessary to produce the finished Good; at some point it may become uneconomical to produce the finished
good at all. I guess that would force down prices of the constituent goods, so maybe that's ok and perfectly legit.
- You can try to simulate finite demand by starting from a fixed price for each finished Good. Each turn, accumulate
some amount of "demand units." If demand is positive, increase the price for that Good by a corresponding amount, and
sell all open orders for the Good at the new price. Then subtract the amount sold from demand. If demand is negative,
don't sell any of the product and instead reduce the price for those Goods by a corresponding amount.

I'm not sure which way to go on this yet. The second option is the simplest, and may be absolutely sufficient for our
needs. The geek in me wants to give option 3 a whirl, though. We'll see how dev goes.

4 Interface
===========

4.1 Description
===============

The Widge API is the public, RESTful API used to interact with the game world. Like good APIs, the Widge API uses the
Game Objects listed above to form the "nouns", and the various HTTP operations form the "verbs" that act on those nouns.
So, for example, to create a new Player, the user might POST a Player object to the URL http://foo.bar/widge/Player.
Similarly, to get a list of all Players, the user might send a GET request to the URL http://foo.bar/widge/Player. And
to get just a single Player by name, the user might send a GET request to the URL http://foo.bar/widge/Player/<name>.

Widge uses JSON as its representation of Objects in the game. Clients will need to be able to understand the Object
models and create, pass and decode JSON representations of them. Later work may allow for different formats, such as
XML.