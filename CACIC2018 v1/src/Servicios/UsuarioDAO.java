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

public class UsuarioDAO implements DAO <Usuario, Integer> {
	
	private static UsuarioDAO daoUsuario;
	
	private UsuarioDAO(){}

	public static UsuarioDAO getInstance() {
		if(daoUsuario==null)
			daoUsuario = new UsuarioDAO();
		return daoUsuario;
	}
	
//	//crea un usuario, desde un nombre y un apellido
//	public static Usuario altaUsuario (String nombre, String apellido, EntityManager entitymanager) {		
//		entitymanager.getTransaction( ).begin( );
//
//		Usuario nuevo = new Usuario();
//		nuevo.setNombre(nombre);
//		nuevo.setApellido(apellido);
//		
//		entitymanager.persist(nuevo);
//		entitymanager.getTransaction( ).commit( );
//		return nuevo;
//	}
//	
//	//genera lista de todos los Usuarios.
//	public static List<Usuario> listarUsuarios (EntityManager entitymanager) {		
//		String jpql = "SELECT u FROM Usuario u";
//		Query query = entitymanager.createQuery(jpql);
//		List<Usuario> resultados = query.getResultList();
//		return resultados;
//	}
	
	//devuelve TODOS los datos de un usuario
	public void buscarDatosUsuario (Usuario usuario) {		
		int id = usuario.getId();
		EntityManager entityManager = EMF.createEntityManager();
		String jpqlUser = "SELECT u FROM Usuario u WHERE u.id = ?1";
		Query queryUser = entityManager.createQuery(jpqlUser);
		queryUser.setParameter(1, id);
		List<Usuario> user = queryUser.getResultList();
		
		String jpqlTemas = "SELECT c FROM Usuario u JOIN u.conocimientos c WHERE u.id = ?1";
		Query queryTemas = entityManager.createQuery(jpqlTemas);
		queryTemas.setParameter(1, id);
		List<Tema> conocimientos = queryTemas.getResultList();
		
		String jpqlTrabajos = "SELECT t FROM Usuario u JOIN u.articulosPropios t WHERE u.id = ?1";
		Query queryTrabajos = entityManager.createQuery(jpqlTrabajos);
		queryTrabajos.setParameter(1, id);
		List<Trabajo> trabajos = queryTrabajos.getResultList();
		
		String jpqlEvaluador = "SELECT e FROM Evaluacion e WHERE e.evaluador = ?1";
		Query queryEvaluador = entityManager.createQuery(jpqlEvaluador);
		queryEvaluador.setParameter(1, usuario);
		List<Trabajo> evaluaciones = queryEvaluador.getResultList();
		
		System.out.println(user.toString() + ",\n con conocimientos en " + conocimientos.toString() + 
				",\n autor de= " + trabajos.toString() + ", \n y designado evaluador en " + evaluaciones.toString());
		entityManager.close();
	}
	
	//devuelve un usuario, a partir de un nombre y apellido
	public Usuario buscarUsuarioPorNombreYApellido (String nombre, String apellido) {		
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT u FROM Usuario u WHERE u.nombre = ?1 AND u.apellido = ?2";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, nombre);
		query.setParameter(2, apellido);
		List<Usuario> resultados = query.getResultList();
		entityManager.close();
		return resultados.get(0);
	}
	
//	//busca al usuario por nombre y apellido, y le agrega un conocimiento
//	public static void agregarConocimiento (String nombre, String apellido, String tema, EntityManager entitymanager) {		
//		Tema aux = TemaDAO.buscarTemaPorNombre(tema, entitymanager);
//		Usuario usuario = buscarUsuarioPorNombreYApellido(nombre, apellido, entitymanager);
//		
//		agregarConocimiento(usuario, aux, entitymanager);
//	}
//	
//	//agrega un conocimiento al usuario indicado
//	public static void agregarConocimiento (Usuario usuario, Tema tema, EntityManager entitymanager) {		
//		entitymanager.getTransaction( ).begin( );
//		
//		usuario.agregarConocimiento(tema);
//		
//		entitymanager.persist(usuario);
//		entitymanager.getTransaction( ).commit( );
//	}
//	
//	//busca al usuario por nombre y apellido, y le agrega un lugar de trabajo
//	public static void agregarLugarDeTrabajo (String nombre, String apellido, String empresa, EntityManager entitymanager) {		
//		Empresa aux = EmpresaDAO.buscarEmpresaPorNombre(empresa, entitymanager);
//		Usuario usuario = buscarUsuarioPorNombreYApellido(nombre, apellido, entitymanager);
//		
//		agregarLugarDeTrabajo(usuario, aux, entitymanager);
//	}
//	
//	//agrega un lugar de trabajo al usuario indicado
//	public static void agregarLugarDeTrabajo (Usuario usuario, Empresa empresa, EntityManager entitymanager) {		
//		entitymanager.getTransaction( ).begin( );
//		
//		usuario.setEmpresa(empresa);
//		
//		entitymanager.persist(usuario);
//		entitymanager.getTransaction( ).commit( );
//	}
	
	//busca al usuario por nombre y apellido, y retorna una lista de los trabajos realizados
	public List<Trabajo> buscarTrabajoPorAutor (String nombre, String apellido) {		
		Usuario usuario = buscarUsuarioPorNombreYApellido(nombre, apellido);
		
		return buscarTrabajoPorAutor(usuario);
	}
	
	//retorna una lista de los trabajos realizados por un autor
	public List<Trabajo> buscarTrabajoPorAutor (Usuario autor) {
		EntityManager entityManager = EMF.createEntityManager();
		int id = autor.getId();
		String jpql = "SELECT t FROM Trabajo t JOIN t.autores a WHERE a.id = ?1";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, id);
		List<Trabajo> resultados = query.getResultList();
		entityManager.close();
		
		return resultados;
	}
	
	//retorna lista de usuario que NO trabajan con él	
	public List<Usuario> listaUsuarioDistintaEmpresa (Usuario usuario) {
		EntityManager entityManager = EMF.createEntityManager();
		int id = usuario.getEmpresa().getId();
		String jpql = "SELECT u FROM Usuario u JOIN u.empresa e WHERE e.id != ?1";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, id);
		List<Usuario> resultados = query.getResultList();
		entityManager.close();
		
		return resultados;
	}
	
	//retorna lista de usuario que NO trabajan con los autores de un trabajo	
	public List<Usuario> listarCandidatosAEvalucacionDistintaEmpresa (Trabajo trabajo) {	
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT u FROM Usuario u WHERE u.empresa NOT IN ( SELECT a.empresa FROM Trabajo t JOIN t.autores a"
										+ " WHERE t.id = ?1) ";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, trabajo.getId());
		List<Usuario> resultados = query.getResultList();
		entityManager.close();
		
		return resultados;
	}
	
	//indica si el usuario conoce TODOS los temas de un trabajo particular
	public boolean conoceTodosLosTemas (Usuario usuario, Trabajo trabajo) {	
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT p FROM Trabajo t JOIN t.palabrasClave p WHERE t.id = ?1 "
				+ "  AND p.id NOT IN ( SELECT c.id FROM Usuario u JOIN u.conocimientos c"
										+ " WHERE u.id = ?2) ";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, trabajo.getId());
		query.setParameter(2, usuario.getId());
		List<Tema> resultados = query.getResultList();
		entityManager.close();
		
		return resultados.isEmpty();
	}
	
	//indica si el usuario conoce UNO de los temas de un trabajo particular
	public boolean conoceUnoDeLosTemas (Usuario usuario, Trabajo trabajo) {	
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT c FROM Usuario u JOIN u.conocimientos c WHERE u.id = ?1  "
				+ "AND c.id IN (SELECT p.id FROM Trabajo t JOIN t.palabrasClave p "
				+ "WHERE t.id = ?2 )";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, usuario.getId());
		query.setParameter(2, trabajo.getId());
		List<Tema> resultados = query.getResultList();
		entityManager.close();
		return !resultados.isEmpty();
	}
	
	//retorna una lista de trabajos cuyos autores NO trabajan con el usuario indicado
	public List<Trabajo> listarTrabajosDeAutoresConDistintaEmpresa (Usuario usuario) {	
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT t FROM Trabajo t "
				+ " WHERE t.id NOT IN (SELECT tr.id FROM Trabajo tr JOIN tr.autores a "
									+ " WHERE a.empresa = ?1) ";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, usuario.getEmpresa());
		List<Trabajo> resultados = query.getResultList();
		entityManager.close();
		
		return resultados;
	}
		
	
	//busca a un evaluador, y luego indica si un evaluador dado es general o experto
	public boolean esEvaluadorGeneral (String nombre, String apellido) {		
		Usuario usuario = buscarUsuarioPorNombreYApellido(nombre, apellido);
		
		return esEvaluadorGeneral(usuario);
	}
	
	//indica si un evaluador dado es general o experto
	public boolean esEvaluadorGeneral (Usuario usuario) {
		EntityManager entityManager = EMF.createEntityManager();
		int id = usuario.getId();
		String jpql = "SELECT u FROM Usuario u JOIN u.conocimientos c WHERE c.id = ?1 AND c.esEspecifico = $2";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, id);
		query.setParameter(1, true);
		List<Usuario> resultados = query.getResultList();
		entityManager.close();
		return resultados.isEmpty();
	}

	public Usuario persist(Usuario usuario) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(usuario);
		entityManager.getTransaction().commit();
		entityManager.close();
		return usuario;
	}

	public Usuario update(Integer id, Usuario actual) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction().begin();
		Usuario usuario = entityManager.find(Usuario.class, id );
		usuario.setApellido(actual.getApellido());
		usuario.setNombre(actual.getNombre());
		usuario.setConocimientos(actual.getConocimientos());
		usuario.setArticulosPropios(actual.getArticulosPropios());
		usuario.setEmpresa(actual.getEmpresa());
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
		return usuario;	
	}

	public Usuario findById(int id) {
		EntityManager entityManager = EMF.createEntityManager();
		Usuario usuario = entityManager.find(Usuario.class, id);
		
		entityManager.close();
		return usuario;		
	}

	public List<Usuario> findAll() {
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT u FROM Usuario u";
		Query query = entityManager.createQuery(jpql);
		List<Usuario> resultados = query.getResultList();
		entityManager.close();
		return resultados;
	}

	public boolean delete(Integer id) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction( ).begin( );
		Usuario usuario = entityManager.find(Usuario.class, id );
		if (usuario != null) {
			entityManager.remove(usuario);
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
