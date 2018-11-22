package com.microAPI.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microAPI.entidades.Trabajo;
import com.microAPI.entidades.Usuario;
import com.microAPI.repositorios.UsuarioRepository;


@Service
public class UsuarioDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UsuarioRepository repoUsuario;
	
	public Usuario encontrarPorId(int id) {
		return repoUsuario.findOne(id);
	}
	
	public List<Usuario> findAll() {
		List<Usuario> retorno = new ArrayList<>();
		repoUsuario.findAll().forEach(retorno::add);		
			return retorno;
	}
	
	public List<Trabajo> buscarTrabajoPorAutor (Usuario autor) {
		int id = autor.getId();
		String jpql = "SELECT t FROM Trabajo t JOIN t.autores a WHERE a.id = ?1";
		javax.persistence.Query query = this.entityManager.createQuery(jpql);
		query.setParameter(1, id);
		List<Trabajo> resultados = query.getResultList();
		this.entityManager.close();
		
		return resultados;
	}
	
}
