package servicios;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Empresa;

//import entidades.Empresa;
//import entidades.Tema;

public class EmpresaDAO implements DAO <Empresa, Integer> {
	
	private static EmpresaDAO daoEmpresa;
	
	private EmpresaDAO(){}

	public static EmpresaDAO getInstance() {
		if(daoEmpresa==null)
			daoEmpresa = new EmpresaDAO();
		return daoEmpresa;
	}

//	//crea una empresa, desde un nombre
//	public static Empresa altaEmpresa (String nombre, EntityManager entitymanager) {		
//		entitymanager.getTransaction( ).begin( );
//
//		Empresa nueva = new Empresa();
//		nueva.setNombre(nombre);
//		
//		entitymanager.persist(nueva);
//		entitymanager.getTransaction().commit( );
//		return nueva;
//	}
	
//	public static Empresa altaEmpresa (Empresa empresa) {		
////		Object nuevo = (Object) empresa;
//		persist(empresa);
//		entitymanager.getTransaction( ).begin( );
//
//		Empresa nueva = new Empresa();
//		nueva.setNombre(nombre);
//		
//		entitymanager.persist(nueva);
//		entitymanager.getTransaction().commit( );
//		return nueva;
//	}
	
	//genera lista de todos las Empresas.
//	public static List<Empresa> listarEmpresas (EntityManager entitymanager) {		
//		String jpql = "SELECT e FROM Empresa e";
//		Query query = entitymanager.createQuery(jpql);
//		List<Empresa> resultados = query.getResultList();
//		return resultados;
//	}
	
	//devuelve una empresa, a partir de un nombre
	public static Empresa buscarEmpresaPorNombre (String nombre) {	
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT e FROM Empresa e WHERE t.nombre = ?1";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, nombre);
		List<Empresa> resultados = query.getResultList();
		entityManager.close();
		return resultados.get(0);
	}

	public List findAll() {
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT e FROM Empresa e";
		Query query = entityManager.createQuery(jpql);
		List<Empresa> resultados = query.getResultList();
		entityManager.close();
		return resultados;
	}
	

	public Empresa persist(Empresa empresa) {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("CACIC2018");
//		EntityManager entityManager = emf.createEntityManager();
		
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(empresa);
		entityManager.getTransaction().commit();
		entityManager.close();
		return empresa;
	}

	public Empresa update(Integer id, Empresa actual) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction().begin();
		Empresa empresa = entityManager.find(Empresa.class, id);
		empresa.setNombre(actual.getNombre());
		empresa.setCUIT(actual.getCUIT());
		empresa.setDireccion(actual.getDireccion());
		empresa.setTelefono(actual.getTelefono());
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
		return empresa;	
	}

	public Empresa findById(int id) {
		EntityManager entityManager = EMF.createEntityManager();
		Empresa empresa = entityManager.find(Empresa.class, id);
		
		entityManager.close();
		return empresa;		
	}

	public boolean delete(Integer id) {
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction( ).begin( );
		Empresa empresa = entityManager.find(Empresa.class, id );
		if (empresa != null) {
			entityManager.remove(empresa);
			entityManager.getTransaction( ).commit( );
			entityManager.close();
			return true;
		}
		else {
			entityManager.close();
			return false;
		}
	}

	@Override
	public Empresa findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
