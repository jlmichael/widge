package widge.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Utility class which create a singleton Hibernate SessionFactory, as recommended
 * by the Hibernate manual.
 */
public class HibernateUtil {
	private static class SingletonHolder {
		private static SessionFactory INSTANCE =
			new AnnotationConfiguration()
				.configure("hibernate.common.cfg.xml")
				.configure("hibernate.cfg.xml")
				.buildSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return SingletonHolder.INSTANCE;
	}

}