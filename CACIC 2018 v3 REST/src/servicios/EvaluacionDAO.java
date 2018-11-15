package servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Evaluacion;
import entidades.Trabajo;
import entidades.Usuario;

//import entidades.Evaluacion;
//import entidades.Tema;
//import entidades.Trabajo;
//import entidades.Usuario;

public class EvaluacionDAO implements DAO <Evaluacion, Integer> {
	
	private static EvaluacionDAO daoEvaluacion;
	
	private EvaluacionDAO(){}

	public static EvaluacionDAO getInstance() {
		if(daoEvaluacion == null)
			daoEvaluacion = new EvaluacionDAO();
		return daoEvaluacion;
	}

	//crea una evaluacion, desde el titulo de un trabajo
//	public void asignarEvaluador (String tituloTrabajo) {		
//		Trabajo trabajo = TrabajoDAO.getInstance().buscarTrabajoPorNombre(tituloTrabajo);
//		
//		asignarEvaluador(trabajo);
//	}
	
	//crea una evaluacion, desde un trabajo
//	public void asignarEvaluador (Trabajo trabajo) {	
//		List<Usuario> evaluadores = ServiciosCongreso.listarCandidatosParaSerEvaluadores(trabajo);
////		EntityManager entityManager = EMF.createEntityManager();
////		entityManager.getTransaction( ).begin( );
//		
//				while ((evaluadores.size() > 0) && (EvaluacionDAO.getInstance().cantidadEvaluacionesPorEvaluador(evaluadores.get(0)) < 3)
//						&& (EvaluacionDAO.getInstance().cantidadEvaluadoresPorTrabajo(trabajo) < 3)) {
//					Evaluacion nueva = new Evaluacion();
//					nueva.setTrabajo(trabajo);
//					nueva.setEvaluador(evaluadores.get(0));
//					evaluadores.remove(0);
//					
//					this.persist(nueva);
//					}
////		return null;
////				entityManager.getTransaction( ).commit( );
////				entityManager.close();
//	}
	
//	//genera lista de todas las Evaluaciones.
//	public static List<Evaluacion> listarEvaluaciones (EntityManager entitymanager) {		
//		String jpql = "SELECT e FROM Evaluacion e";
//		Query query = entitymanager.createQuery(jpql);
//		List<Evaluacion> resultados = query.getResultList();
//		return resultados;
//	}
	
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
	
	
	//busca un evaluador desde su nombre y apellido, y luego retorna cantidad de evaluaciones asignadas
	public int cantidadEvaluacionesPorEvaluador (String nombreAutor, String apellidoAutor) {		
		Usuario evaluador = UsuarioDAO.getInstance().buscarUsuarioPorNombreYApellido(nombreAutor, apellidoAutor);
		
		return cantidadEvaluacionesPorEvaluador(evaluador);
	}
	
	//retorna cantidad de evaluaciones asignadas a un evaluador determinado
	public int cantidadEvaluacionesPorEvaluador (Usuario evaluador) {		
		return buscarTrabajosDeUnEvaluador(evaluador).size();
	}
	
	//busca un trabajo desde su titulo, y luego retorna cantidad de evaluaciones asignadas
	public int cantidadEvaluadoresPorTrabajo (String titulo) {		
		Trabajo trabajo = TrabajoDAO.getInstance().buscarTrabajoPorNombre(titulo);
		
		return cantidadEvaluadoresPorTrabajo(trabajo);
	}
	
	//retorna cantidad de evaluaciones asignadas a un trabajo determinado
	public int cantidadEvaluadoresPorTrabajo(Trabajo trabajo) {		
//		EntityManager entityManager = EMF.createEntityManager();
//		String jpql = "SELECT COUNT(e) FROM Evaluacion e WHERE e.trabajo = ?1";
//		Query query = entityManager.createQuery(jpql);
//		query.setParameter(1, trabajo);
//		List<Evaluacion> resultados = query.getResultList();
//		entityManager.close();
//		
//		return resultados;
		return buscarEvaluacionesPorTrabajo(trabajo).size();	
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
	public List<Evaluacion> buscarEvaluacionPorRangoFechas (LocalDate inicio, LocalDate fin, Usuario evaluador) {		
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT e FROM Evaluacion e JOIN e.evaluador u "
				+ " WHERE u.id = ?1 AND e.fecha BETWEEN ?2 AND ?3 ";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, evaluador.getId());
		query.setParameter(2, inicio);
		query.setParameter(3, fin);	
		List<Evaluacion> resultados = query.getResultList();
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

	public Evaluacion findById(int id) {
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

	@Override
	public Evaluacion findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
