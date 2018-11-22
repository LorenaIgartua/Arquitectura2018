package servicios;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Empresa;
import entidades.Usuario;

public class EmpresaDAO implements DAO <Empresa, Integer> {
	
	private static EmpresaDAO daoEmpresa;
	
	private EmpresaDAO(){}

	public static EmpresaDAO getInstance() {
		if(daoEmpresa==null)
			daoEmpresa = new EmpresaDAO();
		return daoEmpresa;
	}
	
	//devuelve una empresa, a partir de un nombre
	public Empresa buscarEmpresaPorNombre (String nombre) {	
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
		EntityManager entityManager = EMF.createEntityManager();
		Empresa empresa = entityManager.find(Empresa.class, id);
		
		entityManager.close();
		return empresa;	
	}
	
}
