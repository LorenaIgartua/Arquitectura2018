package servicios;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.glassfish.jersey.servlet.ServletContainer;



@WebListener
public class EMF implements ServletContextListener {
	
	private static EntityManagerFactory emf;

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContainer arg0) {
		emf = Persistence.createEntityManagerFactory("CACIC2018");
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContainer arg0) {
		emf.close();
	}

	public static EntityManager createEntityManager() {
		if (emf == null) {
		throw new IllegalStateException("Context is not initialized yet.");
//			emf = Persistence.createEntityManagerFactory("CACIC2018");
		}
		return emf.createEntityManager();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		emf.close();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		emf = Persistence.createEntityManagerFactory("CACIC2018");
	}
}