package widge.model.dao;

import widge.model.Game;
import widge.model.Good;
import widge.model.MarketOrder;
import widge.model.Player;

import java.util.List;

public interface MarketOrderDAO {

    public List<MarketOrder> getMarketOrdersForGame(Game game);
    public List<MarketOrder> getMarketOrdersForGameAndSeller(Game game, Player seller);
    public List<MarketOrder> getMarketOrdersForGameAndBuyer(Game game, Player buyer);
    public MarketOrder getMarketOrderById(Integer id);
    public void addMarketOrder(MarketOrder marketOrder);
    public MarketOrder updateMarketOrder(MarketOrder order);
    public void deleteMarketOrder(MarketOrder order);

}
