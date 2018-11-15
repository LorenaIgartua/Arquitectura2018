package servicios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entidades.Perro;

public class PerroDAO implements DAO<Perro,Integer>{
	
	private static PerroDAO daoPerro;
	
	private PerroDAO(){}

	public static PerroDAO getInstance() {
		if(daoPerro==null)
			daoPerro=new PerroDAO();
		return daoPerro;
	}

	@Override
	public Perro findById(Integer id) {
		
		EntityManager entityManager=EMF.createEntityManager();
		Perro perro=entityManager.find(Perro.class, id);
		entityManager.close();
		return perro;
	
	}

	@Override
	public Perro persist(Perro perro) {
		EntityManager entityManager=EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(perro);
		entityManager.getTransaction().commit();
		entityManager.close();
		return perro;
	}

	@Override
	public List<Perro> findAll() {
		//throw new UnsupportedOperationException();
		EntityManager entityManager = EMF.createEntityManager();
		TypedQuery<Perro> query = entityManager.createQuery("SELECT p FROM Perro p", Perro.class);
		List<Perro> results = query.getResultList();
		entityManager.close();
		return results;
	}

	@Override
	public boolean delete(Integer id) {
//		throw new UnsupportedOperationException();
		EntityManager entityManager = EMF.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("DELETE FROM Perro p WHERE p.id = :id");
		query.setParameter("id", id);
		int deletedCount = query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(deletedCount>0)
			return true;
		else
			return false;
	}

	@Override
	public Perro update(Integer id, Perro entity) {
//		throw new UnsupportedOperationException();
		EntityManager entityManager = EMF.createEntityManager();
		Perro entityAux = entityManager.find(Perro.class, id);
		if (entityAux == null) {
			entityManager.close();
			return null;
		} else {
			entityManager.getTransaction().begin();
			entityAux.setEdad(entity.getEdad());
			entityAux.setNombre(entity.getNombre());
			entityAux.setRaza(entity.getRaza());
			entityManager.getTransaction().commit();
			entityManager.close();
			return entityAux;
		}
	}

}
