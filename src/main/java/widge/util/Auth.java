package widge.util;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.AcceptedByMethod;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import widge.model.dao.TokenDAO;
import widge.model.dao.impl.TokenDAOImpl;
import widge.model.Player;
import widge.model.Token;
import widge.service.Widge;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Provider
@ServerInterceptor
public class Auth implements PreProcessInterceptor, AcceptedByMethod {
    private static final String SALT = "My name is Davey!  I live in the sea!";
    private static Logger logger = Logger.getLogger(Auth.class);
    public static final int TOKEN_TIMEOUT_IN_SECONDS = 60 * 30;

    public static String getSaltedDigest(String unsaltedPassword) {
        String saltedPassword = unsaltedPassword + SALT;
        return hashPassword(saltedPassword);
    }

    /*
     * authPlayer - Attempt to authenticate to the passed in player using the passed in password
     * @param player - The player we are trying to authenticate to
     * @param password - The password used to authenticate
     */
    public static boolean authPlayer(Player player, String password) {
        String saltedPasswordDigest = getSaltedDigest(password);
        if(!saltedPasswordDigest.equals(player.getPassword())) {
            logger.error("Password mismatch for player name " + player.getName());
            return false;
        }
        return true;
    }
    
    public static String hashPassword(String password) {
        String hashword = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            BigInteger hash = new BigInteger(1, md5.digest());
            hashword = hash.toString(16);
        } catch (NoSuchAlgorithmException nsae) {
            // ignore
        }
        return hashword;
    }

    /*
     * preProcess - Preprocess the incoming request to see if it requires authorization
     * @param request - The HttpRequest we are checking
     * @param method - The ResourceMethod we are attempting to execute
     */
    public ServerResponse preProcess(HttpRequest request, ResourceMethod method) throws Failure, WebApplicationException {
        // This method requires authorization.  We get the user id and the token from the HTTP headers.
        HttpHeaders httpHeaders = request.getHttpHeaders();
        List<String> userIds = httpHeaders.getRequestHeader(Widge.HTTP_HEADER_WIDGE_PLAYER_ID);
        Integer userId = null;
        if(userIds != null && userIds.size() != 0) {
            try {
                userId = Integer.valueOf(userIds.get(0));
                logger.info("User id from header is: " + userId);
            } catch (NumberFormatException e) {
                logger.error("Got a non-numeric userId header: " + userIds.get(0), e);
                return createUnauthorizedServerResponse();
            }
        }

        List<String> userTokenKeys = httpHeaders.getRequestHeader(Widge.HTTP_HEADER_WIDGE_PLAYER_TOKEN);
        String userTokenKey = null;
        if(userTokenKeys != null && userTokenKeys.size() != 0) {
            userTokenKey = userTokenKeys.get(0);
            logger.info("user token key from header is: " + userTokenKey);
        }

        TokenDAO tokenDao = new TokenDAOImpl(HibernateUtil.getSessionFactory());
        Token token = tokenDao.getTokenByTokenKey(userTokenKey);

        // If the token's player is the same as our player Id, and not expired, then we are authorized.
        // Update the expiration for the token and return null (indicating that the endpoint should be executed)
        if(token != null && token.getPlayer().getId().equals(userId) &&
                token.getTokenExpiration() != null && token.getTokenExpiration().after(new Date())) {
            token.updateTokenExpiration(TOKEN_TIMEOUT_IN_SECONDS);
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                session.update(token);
                session.getTransaction().commit();
            } catch (HibernateException e) {
                logger.error("Error when trying to update token expiration: ", e);
                session.getTransaction().rollback();
            }

            return null;
        }

        // If we get here, either the token didn't exist, didn't match, or was expired.
        // Return an unauthorized response.
        return createUnauthorizedServerResponse();
    }

    private ServerResponse createUnauthorizedServerResponse() {
        ServerResponse response = new ServerResponse();
        response.setStatus(403);
        return response;
    }

    public boolean accept(Class declaring, Method method) {
        // Get the Method and check to see if it has the RequieresAuthorization annotation
        logger.info("In accept for method: " + method.getName() + " of class: " + declaring.getName());
        Annotation[] annotations = method.getAnnotations();
        for(Annotation annotation : annotations) {
            logger.info("got an annotation: " + annotation.toString());
        }
        if(method.isAnnotationPresent(RequiresAuthorization.class)) {
            logger.info("Needs auth");
            return true;
        }
        logger.info("no auth needed");
        return false;
    }
}
