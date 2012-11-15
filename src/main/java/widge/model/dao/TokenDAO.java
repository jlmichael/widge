package widge.model.dao;

import widge.model.Player;
import widge.model.Token;

import java.util.List;

public interface TokenDAO {

    public void addToken(Token token);
    public List<Token> getTokensByPlayer(Player player);
    public void deleteToken(Token token);
    public Token getTokenByTokenKey(String tokenKey);
}
