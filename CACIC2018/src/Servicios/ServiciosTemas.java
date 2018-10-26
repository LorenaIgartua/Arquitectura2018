package Servicios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Entidades.Tema;
import Entidades.Usuario;

public class ServiciosTemas {

	
	//crea un tema, desde un nombre
	public static Tema altaTema (String nombre, EntityManager entitymanager) {		
		entitymanager.getTransaction().begin( );

		Tema nuevo = new Tema();
		nuevo.setNombre(nombre);
		
		entitymanager.persist( nuevo );
		entitymanager.getTransaction( ).commit( );
		return nuevo;
	}
	
	//crea un tema, desde un nombre, indicando si es especifico
	public static Tema altaTema (String nombre, boolean esEspecifico, EntityManager entitymanager) {		
		entitymanager.getTransaction( ).begin( );

		Tema nuevo = new Tema();
		nuevo.setNombre(nombre);
		nuevo.setEsEspecifico(esEspecifico);
		
		entitymanager.persist( nuevo );
		entitymanager.getTransaction( ).commit( );
		return nuevo;
	}
	
	//genera lista de todos los Temas.
	public static List<Tema> listarTemas (EntityManager entitymanager) {		
		String jpql = "SELECT t FROM Tema t";
		Query query = entitymanager.createQuery(jpql);
		List<Tema> resultados = query.getResultList();
		return resultados;
	}
	
	//devuelve un tema, a partir de un nombre
	public static Tema buscarTemaPorNombre (String nombre, EntityManager entitymanager) {		
		String jpql = "SELECT t FROM Tema t WHERE t.nombre = ?1";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, nombre);
		List<Tema> resultados = query.getResultList();
		return resultados.get(0);
	}
	
	//setea un tema como especifico (o al reves)
	public static void hacerEspecifico (String nombre, boolean espec, EntityManager entitymanager) {	
		Tema aux = buscarTemaPorNombre(nombre, entitymanager);
		aux.setEsEspecifico(espec);
		
		entitymanager.persist(aux);
		entitymanager.getTransaction( ).commit( );
	}
	
	//retorna lista de temas especificos (o generales)
	public static List<Tema> buscarTemasPorTipo (boolean tipo, EntityManager entitymanager) {		
		String jpql = "SELECT t FROM Tema t WHERE t.esEspecifico = ?1";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, tipo);
		List<Tema> resultados = query.getResultList();
		return resultados;
	}
	
	
}
