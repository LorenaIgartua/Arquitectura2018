package Servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Entidades.Tema;
import Entidades.Trabajo;
import Entidades.Usuario;

public class ServiciosTrabajos {
	
	//crea un trabajo, desde un titulo
	public static Trabajo altaTrabajo (String titulo, EntityManager entitymanager) {		
		entitymanager.getTransaction( ).begin( );

		Trabajo trabajo = new Trabajo();
		trabajo.setTitulo(titulo);
		
		entitymanager.persist( trabajo );
		entitymanager.getTransaction( ).commit( );
		return trabajo;
	}
	
	//genera lista de todos los Trabajos.
	public static List<Trabajo> listarTrabajos (EntityManager entitymanager) {		
		String jpql = "SELECT t FROM Trabajo t";
		Query query = entitymanager.createQuery(jpql);
		List<Trabajo> resultados = query.getResultList();
		return resultados;
	}
	
	//devuelve un trabajo, a partir de titulo ingresado
	public static Trabajo buscarTrabajoPorNombre (String nombre, EntityManager entitymanager) {		
		String jpql = "SELECT t FROM Trabajo t WHERE t.titulo = ?1";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, nombre);
		List<Trabajo> resultados = query.getResultList();
		return resultados.get(0);
	}
	
	//busca al trabajo por titulo, y le agrega un tema
	public static void agregarPalabraClave (String titulo, String tema, EntityManager entitymanager) {	
		Tema aux = ServiciosTemas.buscarTemaPorNombre(tema, entitymanager);
		Trabajo trabajo = buscarTrabajoPorNombre(titulo, entitymanager);
		
		agregarPalabraClave(trabajo, aux, entitymanager);
	}
	
	//agrega un tema al trabajo indicado
	public static void agregarPalabraClave (Trabajo trabajo, Tema tema, EntityManager entitymanager) {	
			entitymanager.getTransaction( ).begin( );
			trabajo.agregarPalabraClave(tema);
			
			entitymanager.persist(trabajo);
			entitymanager.getTransaction( ).commit( );
	
	}
	
	//busca al usuario por nombre y apellido, busca el trabajo por titulo, y le agrega un autor
	public static void agregarAutor (String titulo, String nombre, String apellido, EntityManager entitymanager) {	
		Usuario autor = ServiciosUsuarios.buscarUsuarioPorNombreYApellido(nombre, apellido, entitymanager);
		Trabajo trabajo = buscarTrabajoPorNombre(titulo, entitymanager);
		
		agregarAutor(trabajo, autor, entitymanager);
	}
	
	//agrega un autor al trabajo indicado
	public static void agregarAutor (Trabajo trabajo, Usuario autor, EntityManager entitymanager) {	
		entitymanager.getTransaction( ).begin( );
		trabajo.agregarAutor(autor);
		
		entitymanager.persist(trabajo);
		entitymanager.getTransaction( ).commit( );

	}

	//busca el trabajo por titulo, y luego setea si es Poster o no
	public static void setEsPoster (String titulo, Boolean esPoster, EntityManager entitymanager) {	
		Trabajo trabajo = buscarTrabajoPorNombre(titulo, entitymanager);
		
		setEsPoster(trabajo, esPoster, entitymanager);
	
	}
	
	//setea si es Poster o no
	public static void setEsPoster (Trabajo trabajo, Boolean esPoster, EntityManager entitymanager) {	
		entitymanager.getTransaction( ).begin( );
		trabajo.setEsPoster(esPoster);
		
		entitymanager.persist(trabajo);
		entitymanager.getTransaction( ).commit( );

	}
}
