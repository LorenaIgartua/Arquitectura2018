package servicios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Tema;
import entidades.Trabajo;
import entidades.Usuario;


public class UsuarioDAO implements DAO <Usuario, Integer> {
	
	private static UsuarioDAO daoUsuario;
	
	private UsuarioDAO(){}

	public static UsuarioDAO getInstance() {
		if(daoUsuario==null)
			daoUsuario = new UsuarioDAO();
		return daoUsuario;
	}
	
	public List<Tema> conocimientosDeUnUsuario (Usuario usuario) {		
		int id = usuario.getId();
		EntityManager entityManager = EMF.createEntityManager();	
		String jpql = "SELECT c FROM Usuario u JOIN u.conocimientos c WHERE u.id = ?1";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, id);
		List<Tema> result = query.getResultList();
		
		entityManager.close();
		return result;
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

	public Usuario findById(Integer id) {
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
	
	//retorna lista de trabajos que pueden asignarse un evaluador particular
	public List<Trabajo> listarTrabajosParaAsignar (String nombreUsuario, String apellidoUsuario) {		
		Usuario evaluador = UsuarioDAO.getInstance().buscarUsuarioPorNombreYApellido(nombreUsuario, apellidoUsuario);		
		
		return listarTrabajosParaAsignar(evaluador);
	}
	
	public List<Trabajo> listarTrabajosParaAsignar (Usuario evaluador) {		
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = null;
		
		jpql =  "SELECT t6 FROM Trabajo t6 "
				+ " WHERE t6 IN ( SELECT DISTINCT t FROM Trabajo t JOIN t.palabrasClave p  "
								+ " WHERE t.id NOT IN (SELECT e.trabajo FROM Evaluacion e "
														+ " GROUP BY e.trabajo "
														+ " HAVING COUNT (e.trabajo) > 2 ) "
									+ " AND t.id NOT IN (SELECT tr.id FROM Trabajo tr JOIN tr.autores a "
														+ " WHERE a.empresa = ?1) "
								+ " AND p IN  ( SELECT c FROM Usuario u JOIN u.conocimientos c "
											+ " WHERE u.id = ?2 ) "
								+ " AND t.esPoster = 1 ) "
				+ "  OR t6 IN  ( SELECT DISTINCT t FROM Trabajo t JOIN t.palabrasClave p  "
								+ " WHERE t.id NOT IN (SELECT e.trabajo FROM Evaluacion e "
														+ " GROUP BY e.trabajo "
														+ " HAVING COUNT (e.trabajo) > 2 ) "
									+ " AND t.id NOT IN (SELECT tr.id FROM Trabajo tr JOIN tr.autores a "
														+ " WHERE a.empresa = ?1) "
								+ " AND NOT EXISTS ( SELECT p2 FROM Trabajo t2 JOIN t2.palabrasClave p2 "
															+ " WHERE t2.id = t.id "
															+ " AND p2.id NOT IN ( SELECT c.id FROM Usuario u2 JOIN u2.conocimientos c "
																					+ " WHERE u2.id = ?2) ) "
									+ " AND t.esPoster = 0 )" ;	
								
				Query query = entityManager.createQuery(jpql);

				query.setParameter(1, evaluador.getEmpresa());
				query.setParameter(2, evaluador.getId());
				List<Trabajo> resultados = query.getResultList();
				entityManager.close();
				
				return resultados;
	}
	
}
