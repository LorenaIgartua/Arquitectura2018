package Servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Entidades.Evaluacion;
import Entidades.Trabajo;
import Entidades.Usuario;

public class ServiciosEvaluaciones {

	//crea una evaluacion, desde el titulo de un trabajo
	public static void altaEvaluacion (String tituloTrabajo, EntityManager entitymanager) {		
		Trabajo trabajo = ServiciosTrabajos.buscarTrabajoPorNombre(tituloTrabajo, entitymanager);
		
		altaEvaluacion(trabajo, entitymanager);
	}
	
	//crea una evaluacion, desde un trabajo
	public static void altaEvaluacion (Trabajo trabajo, EntityManager entitymanager) {	
		List<Usuario> evaluadores = ServiciosCongreso.listarCandidatosParaSerEvaluadores(trabajo, entitymanager);
		
		entitymanager.getTransaction( ).begin( );
		
				while ((evaluadores.size() > 0) && (cantidadEvaluacionesPorEvaluador(evaluadores.get(0), entitymanager) < 3)
							&& (cantidadEvaluadoresPorTrabajo(trabajo, entitymanager) < 3)) {
					Evaluacion nueva = new Evaluacion();
					nueva.setTrabajo(trabajo);
					nueva.setEvaluador(evaluadores.get(0));
					evaluadores.remove(0);
					entitymanager.persist(nueva);
					}
		
		
		entitymanager.getTransaction( ).commit( );
	}
	
	//genera lista de todas las Evaluaciones.
	public static List<Evaluacion> listarEvaluaciones (EntityManager entitymanager) {		
		String jpql = "SELECT e FROM Evaluacion e";
		Query query = entitymanager.createQuery(jpql);
		List<Evaluacion> resultados = query.getResultList();
		return resultados;
	}
	
	//busca un evaluador desde su nombre y apellido, y luego genera lista de sus Evaluaciones
	public static List<Evaluacion> buscarEvaluacionPorEvaluador (String nombreAutor, String apellidoAutor, EntityManager entitymanager) {		
		Usuario evaluador = ServiciosUsuarios.buscarUsuarioPorNombreYApellido(nombreAutor, apellidoAutor, entitymanager);

		return buscarEvaluacionPorEvaluador(evaluador,entitymanager);
	}
	
	//genera lista de las Evaluaciones de un evaluador determinado
	public static List<Evaluacion> buscarEvaluacionPorEvaluador (Usuario evaluador, EntityManager entitymanager) {		
		String jpql = "SELECT e FROM Evaluacion e WHERE e.evaluador = ?1";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, evaluador);
		List<Evaluacion> resultados = query.getResultList();
		return resultados;
	}
	
	//busca un trabajo desde su titulo, y luego genera lista de sus Evaluaciones
	public static List<Evaluacion> buscarEvaluacionPorTrabajo (String titulo, EntityManager entitymanager) {		
		Trabajo trabajo = ServiciosTrabajos.buscarTrabajoPorNombre(titulo, entitymanager);
		
		return buscarEvaluacionesPorTrabajo(trabajo,entitymanager);
	}
	
	//genera lista de las Evaluaciones de un trabajo determinado
	public static List<Evaluacion> buscarEvaluacionesPorTrabajo (Trabajo trabajo, EntityManager entitymanager) {		
		String jpql = "SELECT e FROM Evaluacion e WHERE e.trabajo = ?1";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, trabajo);
		List<Evaluacion> resultados = query.getResultList();
		return resultados;
	}
	
	
	//busca un evaluador desde su nombre y apellido, y luego retorna cantidad de evaluaciones asignadas
	public static int cantidadEvaluacionesPorEvaluador (String nombreAutor, String apellidoAutor, EntityManager entitymanager) {		
		Usuario evaluador = ServiciosUsuarios.buscarUsuarioPorNombreYApellido(nombreAutor, apellidoAutor, entitymanager);
		
		return cantidadEvaluacionesPorEvaluador(evaluador, entitymanager);
	}
	
	//retorna cantidad de evaluaciones asignadas a un evaluador determinado
	public static int cantidadEvaluacionesPorEvaluador (Usuario evaluador, EntityManager entitymanager) {		
		return buscarTrabajosDeUnEvaluador(evaluador, entitymanager).size();
	}
	
	//busca un trabajo desde su titulo, y luego retorna cantidad de evaluaciones asignadas
	public static int cantidadEvaluadoresPorTrabajo (String titulo, EntityManager entitymanager) {		
		Trabajo trabajo = ServiciosTrabajos.buscarTrabajoPorNombre(titulo, entitymanager);
		
		return cantidadEvaluadoresPorTrabajo(trabajo,entitymanager);
	}
	
	//retorna cantidad de evaluaciones asignadas a un trabajo determinado
	public static int cantidadEvaluadoresPorTrabajo(Trabajo trabajo, EntityManager entitymanager) {		
		return buscarEvaluacionesPorTrabajo(trabajo,entitymanager).size();	
	}
	
	//busca un trabajo desde su titulo, y luego retorna la lista de sus Evaluadores
	public static List<Usuario> buscarEvaluadoresPorTrabajo (String titulo, EntityManager entitymanager) {		
		Trabajo trabajo = ServiciosTrabajos.buscarTrabajoPorNombre(titulo, entitymanager);
		
		return buscarEvaluadoresPorTrabajo(trabajo,entitymanager);
	}
	
	//retorna la lista de los Evaluadores de un trabajo determinado
	public static List<Usuario> buscarEvaluadoresPorTrabajo (Trabajo trabajo, EntityManager entitymanager) {		
		String jpql = "SELECT e FROM Evaluacion e JOIN e.evaluador e WHERE e.trabajo = ?1";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, trabajo);
		List<Usuario> resultados = query.getResultList();
		return resultados;
	}
	
	//busca un evaluador desde su nombre y apellido, y luego retorna lista de trabajos a evaluar
	public static List<Trabajo> buscarTrabajosDeUnEvaluador (String nombreAutor, String apellidoAutor, EntityManager entitymanager) {		
		Usuario evaluador = ServiciosUsuarios.buscarUsuarioPorNombreYApellido(nombreAutor, apellidoAutor, entitymanager);
		
		return buscarTrabajosDeUnEvaluador(evaluador,entitymanager);
	}
	
	//retorna lista de trabajos a evaluar por un evaluador determinado
	public static List<Trabajo> buscarTrabajosDeUnEvaluador (Usuario evaluador, EntityManager entitymanager) {	
		String jpql = "SELECT t FROM Evaluacion e JOIN e.trabajo t WHERE e.evaluador = ?1";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, evaluador);
		List<Trabajo> resultados = query.getResultList();
		return resultados;
	}
	
	//genera lista de las Evaluaciones de un evaluador determinado, y en el rango de fechas indicado
	public static List<Evaluacion> buscarEvaluacionPorRangoFechas (LocalDate inicio, LocalDate fin, Usuario evaluador, EntityManager entitymanager) {		
		String jpql = "SELECT e FROM Evaluacion e JOIN e.evaluador u "
				+ " WHERE u.id = ?1 AND e.fecha BETWEEN ?2 AND ?3 ";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, evaluador.getId());
		query.setParameter(2, inicio);
		query.setParameter(3, fin);	
		List<Evaluacion> resultados = query.getResultList();
		
		return resultados;
	}
	
	
}
