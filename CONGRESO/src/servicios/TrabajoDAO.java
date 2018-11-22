package servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Evaluacion;
import entidades.Tema;
import entidades.Trabajo;
import entidades.Usuario;

//import entidades.Tema;
//import entidades.Trabajo;
//import entidades.Usuario;

public class TrabajoDAO implements DAO <Trabajo, Integer> {
	
	private static TrabajoDAO daoTrabajo;
	
	private TrabajoDAO(){}

	public static TrabajoDAO getInstance() {
		if(daoTrabajo==null)
			daoTrabajo = new TrabajoDAO();
		return daoTrabajo;
	}
	
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
	
	
	public List<Usuario> autoresDeUnTrabajo (Trabajo trabajo) {		
		int id = trabajo.getId();
		EntityManager entityManager = EMF.createEntityManager();	
		String jpqlAutor = "SELECT u FROM Trabajo t JOIN t.autores u WHERE t.id = ?1";
		Query queryAutor = entityManager.createQuery(jpqlAutor);
		queryAutor.setParameter(1, id);
		List<Usuario> autores = queryAutor.getResultList();
		
		entityManager.close();
		return autores;
	}
	
	public List<Tema> temasDeUnTrabajo (Trabajo trabajo) {		
		int id = trabajo.getId();
		EntityManager entityManager = EMF.createEntityManager();	
		String jpql = "SELECT c FROM Trabajo t JOIN t.palabrasClave c WHERE t.id = ?1";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, id);
		List<Tema> result = query.getResultList();
		
		entityManager.close();
		return result;
	}
	
	public Trabajo persist(Trabajo trabajo) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(trabajo);
		entityManager.getTransaction().commit();
		entityManager.close();
//		EvaluacionDAO.getInstance().asignarEvaluador(trabajo);
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

	public Trabajo findById(Integer id) {
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
	
	//retorna lista de trabajos de un autor y revisor en una determinada área de investigación
	public List<Trabajo> buscarTrabajosSegunAutorEvaluadorTema (Usuario autor, Usuario evaluador, Tema tema) {	
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT t FROM Evaluacion e JOIN e.trabajo t JOIN t.autores a JOIN t.palabrasClave p "
				+ "WHERE a.id = ?1 AND e.evaluador = ?2 AND p.id = ?3";
		
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, autor.getId());
		query.setParameter(2, evaluador);
		query.setParameter(3, tema.getId());
		List<Trabajo> resultados = query.getResultList();
		entityManager.close();
		
		return resultados;
		}
	
	//busca un trabajo por su titulo, y luego retorna lista de evaluadores que pueden asignarse
	public List<Usuario> listarCandidatosParaSerEvaluadores (String tituloTrabajo) {		
		Trabajo trabajo = TrabajoDAO.getInstance().buscarTrabajoPorNombre(tituloTrabajo);
		
		return listarCandidatosParaSerEvaluadores(trabajo);
	}
	
	//retorna lista de evaluadores que pueden asignarse a un determinado trabajo
		public List<Usuario> listarCandidatosParaSerEvaluadores (Trabajo trabajo) {		
			EntityManager entityManager = EMF.createEntityManager();
			String jpql = null;
				if (trabajo.isEsPoster()) {			
					jpql = "SELECT DISTINCT u FROM Usuario u JOIN u.conocimientos c "
							+ " WHERE  u.empresa NOT IN ( SELECT a.empresa FROM Trabajo t JOIN t.autores a"
																	+ " WHERE t.id = ?1) "
											+ " AND c IN ( SELECT p FROM Trabajo t JOIN t.palabrasClave p "
																				+ " WHERE t.id = ?1 ) "
									+ " AND u.id NOT IN (SELECT e.evaluador FROM Evaluacion e "
														+ " GROUP BY e.evaluador "
														+ "HAVING COUNT (e.evaluador) > 2 ) "
									+ " AND u.id NOT IN (SELECT e2.evaluador FROM Evaluacion e2 "
														+ " WHERE e2.trabajo = ?1  AND u = e2.evaluador )";			
				}
				else {
					jpql = "SELECT DISTINCT u FROM Usuario u JOIN u.conocimientos c "
													+ " WHERE u.empresa NOT IN ( SELECT a.empresa FROM Trabajo t JOIN t.autores a"
																									+ " WHERE t.id = ?1) "
					
											+	" AND NOT EXISTS ( SELECT p FROM Trabajo t JOIN t.palabrasClave p WHERE t.id = ?1 "
																	+ "  AND p.id NOT IN ( SELECT c.id FROM Usuario u2 JOIN u2.conocimientos c"
																							+ " WHERE u2.id = u.id) )" 
										+ " AND u.id NOT IN (SELECT e.evaluador FROM Evaluacion e "
																+ " GROUP BY e.evaluador "
																+ "HAVING COUNT (e.evaluador) > 2 ) "
										+ "AND u.id NOT IN (SELECT e2.evaluador FROM Evaluacion e2 "
															+ " WHERE e2.trabajo = ?1  AND u = e2.evaluador )";			
				}
					
					Query query = entityManager.createQuery(jpql);
					query.setParameter(1, trabajo.getId());
					List<Usuario> resultados = query.getResultList();
					entityManager.close();
					
				return resultados;
		}
		
}
