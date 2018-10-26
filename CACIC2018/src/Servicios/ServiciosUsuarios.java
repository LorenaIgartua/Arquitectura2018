package Servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Entidades.Empresa;
import Entidades.Tema;
import Entidades.Trabajo;
import Entidades.Usuario;

public class ServiciosUsuarios {
	
	
	//crea un usuario, desde un nombre y un apellido
	public static Usuario altaUsuario (String nombre, String apellido, EntityManager entitymanager) {		
		entitymanager.getTransaction( ).begin( );

		Usuario nuevo = new Usuario();
		nuevo.setNombre(nombre);
		nuevo.setApellido(apellido);
		
		entitymanager.persist(nuevo);
		entitymanager.getTransaction( ).commit( );
		return nuevo;
	}
	
	//genera lista de todos los Usuarios.
	public static List<Usuario> listarUsuarios (EntityManager entitymanager) {		
		String jpql = "SELECT u FROM Usuario u";
		Query query = entitymanager.createQuery(jpql);
		List<Usuario> resultados = query.getResultList();
		return resultados;
	}
	
	//devuelve un usuario, a partir de un nombre y apellido
	public static Usuario buscarUsuarioPorNombreYApellido (String nombre, String apellido, EntityManager entitymanager) {		
		String jpql = "SELECT u FROM Usuario u WHERE u.nombre = ?1 AND u.apellido = ?2";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, nombre);
		query.setParameter(2, apellido);
		List<Usuario> resultados = query.getResultList();
		return resultados.get(0);
	}
	
	//busca al usuario por nombre y apellido, y le agrega un conocimiento
	public static void agregarConocimiento (String nombre, String apellido, String tema, EntityManager entitymanager) {		
		Tema aux = ServiciosTemas.buscarTemaPorNombre(tema, entitymanager);
		Usuario usuario = buscarUsuarioPorNombreYApellido(nombre, apellido, entitymanager);
		
		agregarConocimiento(usuario, aux, entitymanager);
	}
	
	//agrega un conocimiento al usuario indicado
	public static void agregarConocimiento (Usuario usuario, Tema tema, EntityManager entitymanager) {		
		entitymanager.getTransaction( ).begin( );
		
		usuario.agregarConocimiento(tema);
		
		entitymanager.persist(usuario);
		entitymanager.getTransaction( ).commit( );
	}
	
	//busca al usuario por nombre y apellido, y le agrega un lugar de trabajo
	public static void agregarLugarDeTrabajo (String nombre, String apellido, String empresa, EntityManager entitymanager) {		
		Empresa aux = ServiciosEmpresas.buscarEmpresaPorNombre(empresa, entitymanager);
		Usuario usuario = buscarUsuarioPorNombreYApellido(nombre, apellido, entitymanager);
		
		agregarLugarDeTrabajo(usuario, aux, entitymanager);
	}
	
	//agrega un lugar de trabajo al usuario indicado
	public static void agregarLugarDeTrabajo (Usuario usuario, Empresa empresa, EntityManager entitymanager) {		
		entitymanager.getTransaction( ).begin( );
		
		usuario.setEmpresa(empresa);
		
		entitymanager.persist(usuario);
		entitymanager.getTransaction( ).commit( );
	}
	
	//busca al usuario por nombre y apellido, y retorna una lista de los trabajos realizados
	public static List<Trabajo> buscarTrabajoPorAutor (String nombre, String apellido, EntityManager entitymanager) {		
		Usuario usuario = buscarUsuarioPorNombreYApellido(nombre, apellido, entitymanager);
		
		return buscarTrabajoPorAutor(usuario, entitymanager);
	}
	
	//retorna una lista de los trabajos realizados por un autor
	public static List<Trabajo> buscarTrabajoPorAutor (Usuario autor, EntityManager entitymanager) {
		int id = autor.getId();
		String jpql = "SELECT t FROM Trabajo t JOIN t.autores a WHERE a.id = ?1";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, id);
		List<Trabajo> resultados = query.getResultList();
		
		return resultados;
	}
	
	//retorna lista de usuario que NO trabajan con él	
	public static List<Usuario> listaUsuarioDistintaEmpresa (Usuario usuario, EntityManager entitymanager) {	
		int id = usuario.getEmpresa().getId();
		String jpql = "SELECT u FROM Usuario u JOIN u.empresa e WHERE e.id != ?1";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, id);
		List<Usuario> resultados = query.getResultList();
		
		return resultados;
	}
	
	//retorna lista de usuario que NO trabajan con los autores de un trabajo	
	public static List<Usuario> listarCandidatosAEvalucacionDistintaEmpresa (Trabajo trabajo, EntityManager entitymanager) {	
		String jpql = "SELECT u FROM Usuario u WHERE u.empresa NOT IN ( SELECT a.empresa FROM Trabajo t JOIN t.autores a"
										+ " WHERE t.id = ?1) ";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, trabajo.getId());
		List<Usuario> resultados = query.getResultList();
		
		return resultados;
	}
	
	//indica si el usuario conoce TODOS los temas de un trabajo particular
	public static boolean conoceTodosLosTemas (Usuario usuario, Trabajo trabajo, EntityManager entitymanager) {	
		String jpql = "SELECT p FROM Trabajo t JOIN t.palabrasClave p WHERE t.id = ?1 "
				+ "  AND p.id NOT IN ( SELECT c.id FROM Usuario u JOIN u.conocimientos c"
										+ " WHERE u.id = ?2) ";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, trabajo.getId());
		query.setParameter(2, usuario.getId());
		List<Tema> resultados = query.getResultList();
		
		return resultados.isEmpty();
	}
	
	//indica si el usuario conoce UNO de los temas de un trabajo particular
	public static boolean conoceUnoDeLosTemas (Usuario usuario, Trabajo trabajo, EntityManager entitymanager) {		
		String jpql = "SELECT c FROM Usuario u JOIN u.conocimientos c WHERE u.id = ?1  "
				+ "AND c.id IN (SELECT p.id FROM Trabajo t JOIN t.palabrasClave p "
				+ "WHERE t.id = ?2 )";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, usuario.getId());
		query.setParameter(2, trabajo.getId());
		List<Tema> resultados = query.getResultList();
		
		return !resultados.isEmpty();
	}
	
	//retorna una lista de trabajos cuyos autores NO trabajan con el usuario indicado
	public static List<Trabajo> listarTrabajosDeAutoresConDistintaEmpresa (Usuario usuario, EntityManager entitymanager) {	
		String jpql = "SELECT t FROM Trabajo t "
				+ " WHERE t.id NOT IN (SELECT tr.id FROM Trabajo tr JOIN tr.autores a "
									+ " WHERE a.empresa = ?1) ";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, usuario.getEmpresa());
		List<Trabajo> resultados = query.getResultList();
		
		return resultados;
	}
		
	
	//busca a un evaluador, y luego indica si un evaluador dado es general o experto
	public static boolean esEvaluadorGeneral (String nombre, String apellido, EntityManager entitymanager) {		
		Usuario usuario = buscarUsuarioPorNombreYApellido(nombre, apellido, entitymanager);
		
		return esEvaluadorGeneral(usuario, entitymanager);
	}
	
	//indica si un evaluador dado es general o experto
	public static boolean esEvaluadorGeneral (Usuario usuario, EntityManager entitymanager) {				
		int id = usuario.getId();
		String jpql = "SELECT u FROM Usuario u JOIN u.conocimientos c WHERE c.id = ?1 AND c.esEspecifico = $2";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, id);
		query.setParameter(1, true);
		List<Usuario> resultados = query.getResultList();
		
		return resultados.isEmpty();
	}
	
}
