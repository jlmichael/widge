package widge.model.dao;

import widge.model.FilledMarketOrder;
import widge.model.Game;
import widge.model.Player;

import java.util.List;

public interface FilledMarketOrderDAO {

    public List<FilledMarketOrder> getFilledMarketOrdersForGame(Game game);
    public FilledMarketOrder getFilledMarketOrderById(Integer id);
    public List<FilledMarketOrder> getFilledMarketOrdersForGameAndSeller(Game game, Player seller);
    public List<FilledMarketOrder> getFilledMarketOrdersForGameAndBuyer(Game game, Player buyer);
    public void addFilledMarketOrder(FilledMarketOrder order);

}
