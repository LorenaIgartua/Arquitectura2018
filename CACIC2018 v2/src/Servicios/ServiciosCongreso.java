package Servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Entidades.Evaluacion;
import Entidades.Tema;
import Entidades.Trabajo;
import Entidades.Usuario;

public class ServiciosCongreso {
	
	//busca un trabajo por su titulo, y luego retorna lista de evaluadores que pueden asignarse
	public List<Usuario> listarCandidatosParaSerEvaluadores (String tituloTrabajo) {		
		Trabajo trabajo = TrabajoDAO.getInstance().buscarTrabajoPorNombre(tituloTrabajo);
		
		return listarCandidatosParaSerEvaluadores(trabajo);
	}
	
	//retorna lista de evaluadores que pueden asignarse a un determinado trabajo
	public List<Usuario> listarCandidatosParaSerEvaluadores (Trabajo trabajo) {		
		List<Usuario> candidatos = UsuarioDAO.getInstance().listarCandidatosAEvalucacionDistintaEmpresa(trabajo);
		
		List<Usuario> retorno = new ArrayList<Usuario>();
		
		if (trabajo.isEsPoster()) {
			for (Usuario usuario : candidatos)
				if (UsuarioDAO.getInstance().conoceUnoDeLosTemas(usuario, trabajo) ) 
							retorno.add(usuario);
		}
		else {
			for (Usuario usuario : candidatos) 
				if (UsuarioDAO.getInstance().conoceTodosLosTemas(usuario, trabajo)) 
							retorno.add(usuario);
		}
		return retorno;
	}
	
	//retorna lista de trabajos que pueden asignarse un evaluador particular
	public List<Trabajo> listarTrabajosParaAsignar (String nombreUsuario, String apellidoUsuario) {		
		Usuario evaluador = UsuarioDAO.getInstance().buscarUsuarioPorNombreYApellido(nombreUsuario, apellidoUsuario);		
		
		return listarTrabajosParaAsignar(evaluador);
	}
	
	public List<Trabajo> listarTrabajosParaAsignar (Usuario evaluador) {		
		List<Trabajo> candidatos = UsuarioDAO.getInstance().listarTrabajosDeAutoresConDistintaEmpresa(evaluador);
		
		List<Trabajo> retorno = new ArrayList<Trabajo>();
		
		for (Trabajo t : candidatos) {
			if (t.isEsPoster())
					if (UsuarioDAO.getInstance().conoceUnoDeLosTemas(evaluador, t) ) 
								retorno.add(t);
			else
					if (UsuarioDAO.getInstance().conoceTodosLosTemas(evaluador, t)) 
								retorno.add(t);
		}
		
		return retorno;
	}
	
	//retorna lista de trabajos de un autor y revisor en una determinada área de investigación
	public List<Trabajo> buscarTrabajosSegunAutorEvaluadorTema (Usuario autor, Usuario evaluador, Tema tema) {	
		EntityManager entityManager = EMF.createEntityManager();
		String jpql = "SELECT t FROM Evaluacion e JOIN e.trabajo t JOIN t.autores a JOIN t.palabrasClave p "
				+ "WHERE a.id = ?1 AND e.evaluador = ?2 AND p.id = ?3";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, autor.getId());
		query.setParameter(2, evaluador);
		query.setParameter(3, tema.getId());
		List<Trabajo> resultados = query.getResultList();
		entityManager.close();
		
		return resultados;
	}
	
	public void asignarEvaluador (Trabajo trabajo) {	
		List<Usuario> evaluadores = this.listarCandidatosParaSerEvaluadores(trabajo);
//		EntityManager entityManager = EMF.createEntityManager();
//		entityManager.getTransaction( ).begin( );
		
				while ((evaluadores.size() > 0) && (EvaluacionDAO.getInstance().cantidadEvaluacionesPorEvaluador(evaluadores.get(0)) < 3)
						&& (EvaluacionDAO.getInstance().cantidadEvaluadoresPorTrabajo(trabajo) < 3)) {
					Evaluacion nueva = new Evaluacion();
					nueva.setTrabajo(trabajo);
					nueva.setEvaluador(evaluadores.get(0));
					evaluadores.remove(0);
					
					EvaluacionDAO.getInstance().persist(nueva);
					}
//		return null;
//				entityManager.getTransaction( ).commit( );
//				entityManager.close();
	}

	
}
