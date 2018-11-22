package controllerREST;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entidades.Evaluacion;
import entidades.Tema;
import entidades.Trabajo;
import entidades.Usuario;
import servicios.EvaluacionDAO;
import servicios.TemaDAO;
import servicios.TrabajoDAO;
import servicios.UsuarioDAO;


@Path("/trabajos")
public class TrabajoRESTController implements controllerREST <Trabajo, Integer>{
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Trabajo> getAll() {
		return TrabajoDAO.getInstance().findAll();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Trabajo getById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Trabajo trabajo = TrabajoDAO.getInstance().findById(id);
		if(trabajo!=null)
			return trabajo;
		else
			throw new RecursoNoExiste(id);
	}
	
	@GET
	@Path("/{id}/autores")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getAutoresDeUnTrabajoById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Trabajo trabajo = TrabajoDAO.getInstance().findById(id);
		if(trabajo!=null)
			return TrabajoDAO.getInstance().autoresDeUnTrabajo(trabajo);
		else
			throw new RecursoNoExiste(id);
	}
	
	@GET
	@Path("/{id}/evaluadoresParaAsignar")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getEvaluadoresParaAsignarDeUnTrabajoById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Trabajo trabajo = TrabajoDAO.getInstance().findById(id);
		if(trabajo!=null)
			return TrabajoDAO.getInstance().listarCandidatosParaSerEvaluadores(trabajo);
		else
			throw new RecursoNoExiste(id);
	}
	
	@GET
	@Path("/{id}/temas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tema> getTemasDeUnTrabajoById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Trabajo trabajo = TrabajoDAO.getInstance().findById(id);
		if(trabajo!=null)
			return TrabajoDAO.getInstance().temasDeUnTrabajo(trabajo);
		else
			throw new RecursoNoExiste(id);
	}
	
	@GET
	@Path("/{id}/evaluaciones")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Evaluacion> getEvaluacionesDeUnTrabajoById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Trabajo trabajo = TrabajoDAO.getInstance().findById(id);
		if(trabajo!=null)
			return EvaluacionDAO.getInstance().buscarEvaluacionesPorTrabajo(trabajo);
		else
			throw new RecursoNoExiste(id);
	}
	
	@GET
	@Path("/listaSegunAutorEvaluadorTema")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Trabajo> getTrabajoPorAutorEvaluadorTema(@QueryParam("autor") String a, 
			@QueryParam("evaluador") String e, @QueryParam("tema") String t) {
		int idAutor = Integer.valueOf(a);
		Usuario autor = UsuarioDAO.getInstance().findById(idAutor);
		
		int idEvaluador = Integer.valueOf(e);
		Usuario evaluador = UsuarioDAO.getInstance().findById(idEvaluador);
		
		int idTema = Integer.valueOf(t);
		Tema tema = TemaDAO.getInstance().findById(idTema);
		
		if(autor!=null && evaluador != null & tema != null)
			return TrabajoDAO.getInstance().buscarTrabajosSegunAutorEvaluadorTema(autor, evaluador, tema);
		else
			throw new RecursoNoExiste(idAutor);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Trabajo trabajo) {
		Trabajo result= TrabajoDAO.getInstance().persist(trabajo);
		if(result==null) {
			throw new RecursoDuplicado(trabajo.getId());
		}else {
			return Response.status(201).entity(trabajo).build();
		}
	}
	
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") Integer id) {
		boolean wasDeleted = TrabajoDAO.getInstance().delete(id);
		if(wasDeleted)
			return Response.status(200).build();
		else
			throw new RecursoNoExiste(id);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Integer id, Trabajo trabajo) {
		Trabajo result= TrabajoDAO.getInstance().update(id, trabajo);
		if(result==null) {
			throw new RecursoNoExiste(id);
		}else {
			return Response.status(200).entity(trabajo).build();
		}
	}
	
}
