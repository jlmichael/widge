package widge.service.helper;

import org.apache.log4j.Logger;
import widge.model.Game;
import widge.model.MarketOrder;
import widge.model.Player;
import widge.model.dao.MarketOrderDAO;
import widge.model.dao.PlayerDAO;
import widge.model.dao.handler.DAOHandler;
import widge.service.WidgeRequestStore;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

public class MarketHelper extends AbstractHelper {

    private static Logger logger = Logger.getLogger(MarketHelper.class);

    public MarketHelper(DAOHandler daoHandler, WidgeRequestStore requestStore) {
        super(daoHandler, requestStore);
    }


    @GET
    @Path("/marketorder")
    @Produces("application/json")
    public List<MarketOrder> getMarketOrders(@QueryParam("seller") String sellerId, @QueryParam("buyer") String buyerId) {
        logger.info("MarketHelper servicing a call to getMarketOrders");

        // Get the game from the request store
        Game game = requestStore.getRequestGame();
        if(game == null) {
            logger.error("No game defined for marketorder endpoint and one is required");
            return null;
        }

        PlayerDAO playerDAO = daoHandler.getPlayerDAO();
        MarketOrderDAO marketOrderDAO = daoHandler.getMarketOrderDAO();

        if(sellerId != null && !sellerId.equals("")) {
            logger.info("Filtering on seller = " + sellerId);
            Integer idInt;
            try {
                idInt = Integer.valueOf(sellerId);
            } catch (NumberFormatException e) {
                logger.error("Could not format string sellerId: " + sellerId + " to integer", e);
                return null;
            }
            Player seller = playerDAO.getPlayerById(idInt);
            if(seller == null) {
                logger.error("Request for market orders for a null seller: " + sellerId);
                return null;
            }
            return marketOrderDAO.getMarketOrdersForGameAndSeller(game, seller);
        } else if(buyerId != null && !buyerId.equals("")) {
            logger.info("Filtering on buyer = " + buyerId);
            Integer idInt;
            try {
                idInt = Integer.valueOf(buyerId);
            } catch (NumberFormatException e) {
                logger.error("Could not format string buyerId: " + buyerId + " to integer", e);
                return null;
            }
            Player buyer = playerDAO.getPlayerById(idInt);
            if(buyer == null) {
                logger.error("Request for market orders for a null buyer: " + buyerId);
                return null;
            }
            return marketOrderDAO.getMarketOrdersForGameAndBuyer(game, buyer);
        }

        return marketOrderDAO.getMarketOrdersForGame(game);
    }

}
