package servicios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Tema;

public class TemaDAO implements DAO <Tema, Integer> {
	
	private static TemaDAO daoTema;
	
	private TemaDAO(){}

	public static TemaDAO getInstance() {
		if(daoTema==null)
			daoTema = new TemaDAO();
		return daoTema;
	}
	
	//devuelve un tema, a partir de un nombre
	public static Tema buscarTemaPorNombre (String nombre, EntityManager entitymanager) {		
		String jpql = "SELECT t FROM Tema t WHERE t.nombre = ?1";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, nombre);
		List<Tema> resultados = query.getResultList();
		return resultados.get(0);
	}
	
	//retorna lista de temas especificos (o generales)
	public static List<Tema> buscarTemasPorTipo (boolean tipo, EntityManager entitymanager) {		
		String jpql = "SELECT t FROM Tema t WHERE t.esEspecifico = ?1";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, tipo);
		List<Tema> resultados = query.getResultList();
		return resultados;
	}

	public Tema persist(Tema tema) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(tema);
		entityManager.getTransaction().commit();
		entityManager.close();
		return tema;
	}

	public Tema update(Integer id, Tema actual) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction().begin();
		Tema tema = entityManager.find(Tema.class, id);
		tema.setNombre(actual.getNombre());
		tema.setEsEspecifico(actual.esEspecifico());
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
		return tema;	
	}

	public Tema findById(Integer id) {
		EntityManager entityManager = EMF.createEntityManager();
		Tema tema = entityManager.find(Tema.class, id);
		
		entityManager.close();
		return tema;		
	}

	public List<Tema> findAll() {
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT t FROM Tema t";
		Query query = entityManager.createQuery(jpql);
		List<Tema> resultados = query.getResultList();
		return resultados;
	}

	public boolean delete(Integer id) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction( ).begin( );
		Tema tema = entityManager.find(Tema.class, id );
		if (tema != null) {
			entityManager.remove(tema);
			entityManager.getTransaction( ).commit( );
			entityManager.close();
			return true;
		}
		else {
			entityManager.close();
			return false;
		}
	}
	
}
