package Servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Entidades.Tema;
import Entidades.Trabajo;
import Entidades.Usuario;

public class TrabajoDAO implements DAO <Trabajo, Integer> {
	
	private static TrabajoDAO daoTrabajo;
	
	private TrabajoDAO(){}

	public static TrabajoDAO getInstance() {
		if(daoTrabajo==null)
			daoTrabajo = new TrabajoDAO();
		return daoTrabajo;
	}
	
//	//crea un trabajo, desde un titulo
//	public static Trabajo altaTrabajo (String titulo, EntityManager entitymanager) {		
//		entitymanager.getTransaction( ).begin( );
//
//		Trabajo trabajo = new Trabajo();
//		trabajo.setTitulo(titulo);
//		
//		entitymanager.persist( trabajo );
//		entitymanager.getTransaction( ).commit( );
//		return trabajo;
//	}
//	
//	//genera lista de todos los Trabajos.
//	public static List<Trabajo> listarTrabajos (EntityManager entitymanager) {		
//		String jpql = "SELECT t FROM Trabajo t";
//		Query query = entitymanager.createQuery(jpql);
//		List<Trabajo> resultados = query.getResultList();
//		return resultados;
//	}
//	
	//devuelve un trabajo, a partir de titulo ingresado
	public Trabajo buscarTrabajoPorNombre (String nombre) {	
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT t FROM Trabajo t WHERE t.titulo = ?1";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, nombre);
		List<Trabajo> resultados = query.getResultList();
		entityManager.close();
		return resultados.get(0);
	}
	
	public void buscarDatosTrabajo (Trabajo trabajo) {		
		int id = trabajo.getId();
		EntityManager entityManager = EMF.createEntityManager();
		String jpqlTrabajo = "SELECT t FROM Trabajo t WHERE t.id = ?1";
		Query queryTrabajo = entityManager.createQuery(jpqlTrabajo);
		queryTrabajo.setParameter(1, id);
		List<Usuario> paper = queryTrabajo.getResultList();
		
		String jpqlTemas = "SELECT c FROM Trabajo t JOIN t.palabrasClave c WHERE t.id = ?1";
		Query queryTemas = entityManager.createQuery(jpqlTemas);
		queryTemas.setParameter(1, id);
		List<Tema> conocimientos = queryTemas.getResultList();
		
		String jpqlAutor = "SELECT u FROM Trabajo t JOIN t.autores u WHERE t.id = ?1";
		Query queryAutor = entityManager.createQuery(jpqlAutor);
		queryAutor.setParameter(1, id);
		List<Trabajo> autores = queryAutor.getResultList();
		
		String jpqlEvaluador = "SELECT e FROM Evaluacion e WHERE e.trabajo = ?1";
		Query queryEvaluador = entityManager.createQuery(jpqlEvaluador);
		queryEvaluador.setParameter(1, trabajo);
		List<Trabajo> evaluaciones = queryEvaluador.getResultList();
		
		System.out.println(paper.toString() + ",\n que trata de " + conocimientos.toString() 
		+ ",\n autores= " + autores.toString() + ", \n y evaluado por " + evaluaciones.toString() );
		entityManager.close();
	}
	
//	//busca al trabajo por titulo, y le agrega un tema
//	public static void agregarPalabraClave (String titulo, String tema, EntityManager entitymanager) {	
//		Tema aux = TemaDAO.buscarTemaPorNombre(tema, entitymanager);
//		Trabajo trabajo = buscarTrabajoPorNombre(titulo, entitymanager);
//		
//		agregarPalabraClave(trabajo, aux, entitymanager);
//	}
//	
//	//agrega un tema al trabajo indicado
//	public static void agregarPalabraClave (Trabajo trabajo, Tema tema, EntityManager entitymanager) {	
//			entitymanager.getTransaction( ).begin( );
//			trabajo.agregarPalabraClave(tema);
//			
//			entitymanager.persist(trabajo);
//			entitymanager.getTransaction( ).commit( );
//	
//	}
//	
//	//busca al usuario por nombre y apellido, busca el trabajo por titulo, y le agrega un autor
//	public static void agregarAutor (String titulo, String nombre, String apellido, EntityManager entitymanager) {	
//		Usuario autor = UsuarioDAO.buscarUsuarioPorNombreYApellido(nombre, apellido, entitymanager);
//		Trabajo trabajo = buscarTrabajoPorNombre(titulo, entitymanager);
//		
//		agregarAutor(trabajo, autor, entitymanager);
//	}
//	
//	//agrega un autor al trabajo indicado
//	public static void agregarAutor (Trabajo trabajo, Usuario autor, EntityManager entitymanager) {	
//		entitymanager.getTransaction( ).begin( );
//		trabajo.agregarAutor(autor);
//		
//		entitymanager.persist(trabajo);
//		entitymanager.getTransaction( ).commit( );
//
//	}
//
//	//busca el trabajo por titulo, y luego setea si es Poster o no
//	public static void setEsPoster (String titulo, Boolean esPoster, EntityManager entitymanager) {	
//		Trabajo trabajo = buscarTrabajoPorNombre(titulo, entitymanager);
//		
//		setEsPoster(trabajo, esPoster, entitymanager);
//	
//	}
//	
//	//setea si es Poster o no
//	public static void setEsPoster (Trabajo trabajo, Boolean esPoster, EntityManager entitymanager) {	
//		entitymanager.getTransaction( ).begin( );
//		trabajo.setEsPoster(esPoster);
//		
//		entitymanager.persist(trabajo);
//		entitymanager.getTransaction( ).commit( );
//
//	}

	public Trabajo persist(Trabajo trabajo) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(trabajo);
		entityManager.getTransaction().commit();
		entityManager.close();
		return trabajo;
	}

	public Trabajo update(Integer id, Trabajo actual) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction().begin();
		Trabajo trabajo = entityManager.find(Trabajo.class, id );
		trabajo.setAutor(actual.getAutores());
		trabajo.setEsPoster(actual.isEsPoster());
		trabajo.setPalabrasClave(actual.getPalabrasClave());
		trabajo.setTitulo(actual.getTitulo());
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
		return trabajo;	
	}

	public Trabajo findById(int id) {
		EntityManager entityManager = EMF.createEntityManager();
		Trabajo trabajo = entityManager.find(Trabajo.class, id);
		
		entityManager.close();
		return trabajo;		
	}

	public List<Trabajo> findAll() {
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT t FROM Trabajo t";
		Query query = entityManager.createQuery(jpql);
		List<Trabajo> resultados = query.getResultList();
		entityManager.close();
		return resultados;
	}

	public boolean delete(Integer id) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction( ).begin( );
		Trabajo trabajo = entityManager.find(Trabajo.class, id );
		if (trabajo != null) {
			entityManager.remove(trabajo);
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
