package Servicios;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
	private static EntityManagerFactory emf;


	public static void contextDestroyed() {
		if(emf!=null)
			emf.close();
	}

	public static EntityManager createEntityManager() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("CACIC2018");
		}
		return emf.createEntityManager();
	}
}