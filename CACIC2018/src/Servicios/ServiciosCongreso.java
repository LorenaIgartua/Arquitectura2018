package Servicios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import Entidades.Tema;
import Entidades.Trabajo;
import Entidades.Usuario;

public class ServiciosCongreso {
	
	//busca un trabajo por su titulo, y luego retorna lista de evaluadores que pueden asignarse
	public static List<Usuario> listarCandidatosParaSerEvaluadores (String tituloTrabajo, EntityManager entitymanager) {		
		Trabajo trabajo = ServiciosTrabajos.buscarTrabajoPorNombre(tituloTrabajo, entitymanager);
		
		return listarCandidatosParaSerEvaluadores(trabajo, entitymanager);
	}
	
	//retorna lista de evaluadores que pueden asignarse a un determinado trabajo
	public static List<Usuario> listarCandidatosParaSerEvaluadores (Trabajo trabajo, EntityManager entitymanager) {		
		List<Usuario> candidatos = ServiciosUsuarios.listarCandidatosAEvalucacionDistintaEmpresa(trabajo,entitymanager);
		
		List<Usuario> retorno = new ArrayList<Usuario>();
		
		if (trabajo.isEsPoster()) {
			for (Usuario usuario : candidatos)
				if (ServiciosUsuarios.conoceUnoDeLosTemas(usuario, trabajo, entitymanager) ) 
							retorno.add(usuario);
		}
		else {
			for (Usuario usuario : candidatos) 
				if (ServiciosUsuarios.conoceTodosLosTemas(usuario, trabajo, entitymanager)) 
							retorno.add(usuario);
		}
		return retorno;
	}
	
	//retorna lista de trabajos que pueden asignarse un evaluador particular
	public static List<Trabajo> listarTrabajosParaAsignar (String nombreUsuario, String apellidoUsuario, EntityManager entitymanager) {		
		Usuario evaluador = ServiciosUsuarios.buscarUsuarioPorNombreYApellido(nombreUsuario, apellidoUsuario, entitymanager);		
		
		return listarTrabajosParaAsignar(evaluador, entitymanager);
	}
	
	public static List<Trabajo> listarTrabajosParaAsignar (Usuario evaluador, EntityManager entitymanager) {		
		List<Trabajo> candidatos = ServiciosUsuarios.listarTrabajosDeAutoresConDistintaEmpresa(evaluador,entitymanager);
		
		List<Trabajo> retorno = new ArrayList<Trabajo>();
		
		for (Trabajo t : candidatos) {
			if (t.isEsPoster())
					if (ServiciosUsuarios.conoceUnoDeLosTemas(evaluador, t, entitymanager) ) 
								retorno.add(t);
			else
					if (ServiciosUsuarios.conoceTodosLosTemas(evaluador, t, entitymanager)) 
								retorno.add(t);
		}
		
		return retorno;
	}
	
	//retorna lista de trabajos de un autor y revisor en una determinada área de investigación
	public static List<Trabajo> buscarTrabajosSegunAutorEvaluadorTema (Usuario autor, Usuario evaluador, Tema tema, EntityManager entitymanager) {	
		String jpql = "SELECT t FROM Evaluacion e JOIN e.trabajo t JOIN t.autores a JOIN t.palabrasClave p "
				+ "WHERE a.id = ?1 AND e.evaluador = ?2 AND p.id = ?3";
		Query query = entitymanager.createQuery(jpql);
		query.setParameter(1, autor.getId());
		query.setParameter(2, evaluador);
		query.setParameter(3, tema.getId());
		List<Trabajo> resultados = query.getResultList();
		return resultados;
	}

	
}
