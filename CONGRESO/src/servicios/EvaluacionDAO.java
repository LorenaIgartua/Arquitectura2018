package servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Evaluacion;
import entidades.Trabajo;
import entidades.Usuario;


public class EvaluacionDAO implements DAO <Evaluacion, Integer> {
	
	private static EvaluacionDAO daoEvaluacion;
	
	private EvaluacionDAO(){}

	public static EvaluacionDAO getInstance() {
		if(daoEvaluacion == null)
			daoEvaluacion = new EvaluacionDAO();
		return daoEvaluacion;
	}
	
	public List<Evaluacion> asignarEvaluador (Trabajo trabajo) {	
		List<Evaluacion> retorno = new ArrayList<Evaluacion>();
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = null;
		
		String consulta = " SELECT e FROM Evaluacion e WHERE e.trabajo = ?1 ";
		
		Query query2 = entityManager.createQuery(consulta);
		query2.setParameter(1, trabajo);
		List<Evaluacion> trabajos = query2.getResultList();
		
		if ( trabajos.size() < 3) {
			
		
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
														+ " WHERE e2.trabajo = ?1 "
														+ " AND u = e2.evaluador) ";			
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
									+ " AND u.id NOT IN (SELECT e2.evaluador FROM Evaluacion e2 "
															+ " WHERE e2.trabajo = ?1  AND u = e2.evaluador ) ";			
			}
		
		 
			Query query = entityManager.createQuery(jpql);
				query.setMaxResults(1);
				query.setParameter(1, trabajo.getId());
				List<Usuario> resultados = query.getResultList();
				entityManager.close();
				
				for (Usuario u : resultados) {
					Evaluacion nueva = new Evaluacion();
					nueva.setTrabajo(trabajo);
					nueva.setEvaluador(u);
					
					retorno.add(nueva);
					EvaluacionDAO.getInstance().persist(nueva);
				}
		}
			return retorno;
		}

	
	//busca un evaluador desde su nombre y apellido, y luego genera lista de sus Evaluaciones
	public List<Evaluacion> buscarEvaluacionPorEvaluador (String nombreAutor, String apellidoAutor) {		
		Usuario evaluador = UsuarioDAO.getInstance().buscarUsuarioPorNombreYApellido(nombreAutor, apellidoAutor);

		return buscarEvaluacionPorEvaluador(evaluador);
	}
	
	//genera lista de las Evaluaciones de un evaluador determinado
	public List<Evaluacion> buscarEvaluacionPorEvaluador (Usuario evaluador) {	
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT e FROM Evaluacion e WHERE e.evaluador = ?1";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, evaluador);
		List<Evaluacion> resultados = query.getResultList();
		entityManager.close();
		
		return resultados;
	}
	
	//busca un trabajo desde su titulo, y luego genera lista de sus Evaluaciones
	public List<Evaluacion> buscarEvaluacionPorTrabajo (String titulo) {		
		Trabajo trabajo = TrabajoDAO.getInstance().buscarTrabajoPorNombre(titulo);
		
		return buscarEvaluacionesPorTrabajo(trabajo);
	}
	
	//genera lista de las Evaluaciones de un trabajo determinado
	public List<Evaluacion> buscarEvaluacionesPorTrabajo (Trabajo trabajo) {		
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT e FROM Evaluacion e WHERE e.trabajo = ?1";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, trabajo);
		List<Evaluacion> resultados = query.getResultList();
		entityManager.close();
		
		return resultados;
	}
	
	
	//busca un trabajo desde su titulo, y luego retorna la lista de sus Evaluadores
	public List<Usuario> buscarEvaluadoresPorTrabajo (String titulo) {		
		Trabajo trabajo = TrabajoDAO.getInstance().buscarTrabajoPorNombre(titulo);
		
		return buscarEvaluadoresPorTrabajo(trabajo);
	}
	
	//retorna la lista de los Evaluadores de un trabajo determinado
	public List<Usuario> buscarEvaluadoresPorTrabajo (Trabajo trabajo) {	
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT e FROM Evaluacion e JOIN e.evaluador e WHERE e.trabajo = ?1";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, trabajo);
		List<Usuario> resultados = query.getResultList();
		entityManager.close();
		
		return resultados;
	}
	
	//busca un evaluador desde su nombre y apellido, y luego retorna lista de trabajos a evaluar
	public List<Trabajo> buscarTrabajosDeUnEvaluador (String nombreAutor, String apellidoAutor) {		
		Usuario evaluador = UsuarioDAO.getInstance().buscarUsuarioPorNombreYApellido(nombreAutor, apellidoAutor);
		
		return buscarTrabajosDeUnEvaluador(evaluador);
	}
	
	//retorna lista de trabajos a evaluar por un evaluador determinado
	public List<Trabajo> buscarTrabajosDeUnEvaluador (Usuario evaluador) {	
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT t FROM Evaluacion e JOIN e.trabajo t WHERE e.evaluador = ?1";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, evaluador);
		List<Trabajo> resultados = query.getResultList();
		entityManager.close();
		
		return resultados;
	}
	
	//genera lista de las Evaluaciones de un evaluador determinado, y en el rango de fechas indicado
	public List<Trabajo> buscarEvaluacionPorRangoFechas (LocalDate inicio, LocalDate fin, Usuario evaluador) {		
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT e.trabajo FROM Evaluacion e  "
				+ " WHERE e.evaluador = ?1 AND e.fecha BETWEEN ?2 AND ?3 ";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, evaluador);
		query.setParameter(2, inicio);
		query.setParameter(3, fin);	
		List<Trabajo> resultados = query.getResultList();
		entityManager.close();
		
		return resultados;
	}

	public boolean delete(Integer id) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction( ).begin( );
		Evaluacion evaluacion = entityManager.find(Evaluacion.class, id );
		if (evaluacion != null) {
			entityManager.remove(evaluacion);
			entityManager.getTransaction( ).commit( );
			entityManager.close();
			return true;
		}
		else {
			entityManager.close();
			return false;
		}
	}

	public Evaluacion persist(Evaluacion evaluacion) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(evaluacion);
		entityManager.getTransaction().commit();
		entityManager.close();
		return evaluacion;
	}

	public Evaluacion update(Integer id, Evaluacion newEntityValues) {
		// TODO Auto-generated method stub
		return null;
	}

	public Evaluacion findById(Integer id) {
		EntityManager entityManager = EMF.createEntityManager();
		Evaluacion evaluacion = entityManager.find(Evaluacion.class, id);
		
		entityManager.close();
		return evaluacion;	
	}

	public List<Evaluacion> findAll() {
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT e FROM Evaluacion e";
		Query query = entityManager.createQuery(jpql);
		List<Evaluacion> resultados = query.getResultList();
		entityManager.close();
		
		return resultados;
	}
	

	
	
	
	
}
