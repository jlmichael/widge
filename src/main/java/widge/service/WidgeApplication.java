package widge.service;

import org.hibernate.SessionFactory;
import widge.model.dao.CommandTemplateDAO;
import widge.model.dao.GoodDAO;
import widge.model.dao.PlayerDAO;
import widge.model.dao.handler.DAOHandler;
import widge.model.dao.impl.*;
import widge.util.HibernateUtil;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class WidgeApplication extends Application
{
   HashSet<Object> singletons = new HashSet<Object>();

   public WidgeApplication()
   {
       SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
       // Initialize a new DAOHandler
       DAOHandler daoHandler = new DAOHandler(
               new CommandTemplateDAOImpl(sessionFactory),
               new FabricationFormulaDAOImpl(sessionFactory),
               new FilledMarketOrderDAOImpl(sessionFactory),
               new GameDAOImpl(sessionFactory),
               new GoodDAOImpl(sessionFactory),
               new MarketOrderDAOImpl(sessionFactory),
               new PlayerCommandDAOImpl(sessionFactory),
               new PlayerDAOImpl(sessionFactory),
               new PlayerGameDAOImpl(sessionFactory),
               new PlayerGoodDAOImpl(sessionFactory),
               new TokenDAOImpl(sessionFactory),
               new TurnDAOImpl(sessionFactory)
       );

       singletons.add(new Widge(daoHandler));

   }

   @Override
   public Set<Class<?>> getClasses()
   {
        HashSet<Class<?>> set = new HashSet<Class<?>>();
        return set;
   }

   @Override
   public Set<Object> getSingletons()
   {
      return singletons;
   }


}
