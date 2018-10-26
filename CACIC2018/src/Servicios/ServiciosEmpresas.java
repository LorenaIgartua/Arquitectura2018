package Servicios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Entidades.Empresa;
import Entidades.Tema;

public class ServiciosEmpresas {

	//crea una empresa, desde un nombre
	public static Empresa altaEmpresa (String nombre, EntityManager entitymanager) {		
		entitymanager.getTransaction( ).begin( );

		Empresa nueva = new Empresa();
		nueva.setNombre(nombre);
		
		entitymanager.persist(nueva);
		entitymanager.getTransaction().commit( );
		return nueva;
	}
	
	//genera lista de todos las Empresas.
	public static List<Empresa> listarEmpresas (EntityManager entitymanager) {		
		String jpql = "SELECT e FROM Empresa e";
		Query query = entitymanager.createQuery(jpql);
		List<Empresa> resultados = query.getResultList();
		return resultados;
	}
	
	//devuelve una empresa, a partir de un nombre
	public static Empresa buscarEmpresaPorNombre (String nombre, EntityManager entitymanager) {		
		String jpql = "SELECT e FROM Empresa e WHERE t.nombre = ?1";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, nombre);
		List<Empresa> resultados = query.getResultList();
		return resultados.get(0);
	}
	
}
