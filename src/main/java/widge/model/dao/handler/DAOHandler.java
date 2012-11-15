package widge.model.dao.handler;

import widge.model.dao.*;

// A class for managing and bundling the various DAOs.  It was getting really cumbersome to pass around instances of
// each DAO to the constructors of each Helper object.  This lets me pass around one master object that contains
// references to each DAO I might need.  In practice, this gets built once by the RESTeasy framework and added to its
// list of singletons.
public class DAOHandler {

    private CommandTemplateDAO commandTemplateDAO;
    private FabricationFormulaDAO fabricationFormulaDAO;
    private FilledMarketOrderDAO filledMarketOrderDAO;
    private GameDAO gameDAO;
    private GoodDAO goodDAO;
    private MarketOrderDAO marketOrderDAO;
    private PlayerCommandDAO playerCommandDAO;
    private PlayerDAO playerDAO;
    private PlayerGameDAO playerGameDAO;
    private PlayerGoodDAO playerGoodDAO;
    private TokenDAO tokenDAO;
    private TurnDAO turnDAO;

    public DAOHandler() {
    }

    public DAOHandler(CommandTemplateDAO commandTemplateDAO,
                      FabricationFormulaDAO fabricationFormulaDAO,
                      FilledMarketOrderDAO filledMarketOrderDAO,
                      GameDAO gameDAO,
                      GoodDAO goodDAO,
                      MarketOrderDAO marketOrderDAO,
                      PlayerCommandDAO playerCommandDAO,
                      PlayerDAO playerDAO,
                      PlayerGameDAO playerGameDAO,
                      PlayerGoodDAO playerGoodDAO,
                      TokenDAO tokenDAO,
                      TurnDAO turnDAO) {
        this.commandTemplateDAO = commandTemplateDAO;
        this.fabricationFormulaDAO = fabricationFormulaDAO;
        this.filledMarketOrderDAO = filledMarketOrderDAO;
        this.gameDAO = gameDAO;
        this.goodDAO = goodDAO;
        this.marketOrderDAO = marketOrderDAO;
        this.playerCommandDAO = playerCommandDAO;
        this.playerDAO = playerDAO;
        this.playerGameDAO = playerGameDAO;
        this.playerGoodDAO = playerGoodDAO;
        this.tokenDAO = tokenDAO;
        this.turnDAO = turnDAO;
    }

    public CommandTemplateDAO getCommandTemplateDAO() {
        return commandTemplateDAO;
    }

    public void setCommandTemplateDAO(CommandTemplateDAO commandTemplateDAO) {
        this.commandTemplateDAO = commandTemplateDAO;
    }

    public FabricationFormulaDAO getFabricationFormulaDAO() {
        return fabricationFormulaDAO;
    }

    public void setFabricationFormulaDAO(FabricationFormulaDAO fabricationFormulaDAO) {
        this.fabricationFormulaDAO = fabricationFormulaDAO;
    }

    public FilledMarketOrderDAO getFilledMarketOrderDAO() {
        return filledMarketOrderDAO;
    }

    public void setFilledMarketOrderDAO(FilledMarketOrderDAO filledMarketOrderDAO) {
        this.filledMarketOrderDAO = filledMarketOrderDAO;
    }

    public GameDAO getGameDAO() {
        return gameDAO;
    }

    public void setGameDAO(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public GoodDAO getGoodDAO() {
        return goodDAO;
    }

    public void setGoodDAO(GoodDAO goodDAO) {
        this.goodDAO = goodDAO;
    }

    public MarketOrderDAO getMarketOrderDAO() {
        return marketOrderDAO;
    }

    public void setMarketOrderDAO(MarketOrderDAO marketOrderDAO) {
        this.marketOrderDAO = marketOrderDAO;
    }

    public PlayerCommandDAO getPlayerCommandDAO() {
        return playerCommandDAO;
    }

    public void setPlayerCommandDAO(PlayerCommandDAO playerCommandDAO) {
        this.playerCommandDAO = playerCommandDAO;
    }

    public PlayerDAO getPlayerDAO() {
        return playerDAO;
    }

    public void setPlayerDAO(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public PlayerGameDAO getPlayerGameDAO() {
        return playerGameDAO;
    }

    public void setPlayerGameDAO(PlayerGameDAO playerGameDAO) {
        this.playerGameDAO = playerGameDAO;
    }

    public PlayerGoodDAO getPlayerGoodDAO() {
        return playerGoodDAO;
    }

    public void setPlayerGoodDAO(PlayerGoodDAO playerGoodDAO) {
        this.playerGoodDAO = playerGoodDAO;
    }

    public TokenDAO getTokenDAO() {
        return tokenDAO;
    }

    public void setTokenDAO(TokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    public TurnDAO getTurnDAO() {
        return turnDAO;
    }

    public void setTurnDAO(TurnDAO turnDAO) {
        this.turnDAO = turnDAO;
    }
}
