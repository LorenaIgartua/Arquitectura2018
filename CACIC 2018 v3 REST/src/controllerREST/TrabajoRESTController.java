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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entidades.Evaluacion;
import entidades.Tema;
import entidades.Trabajo;
import entidades.Usuario;
import servicios.EvaluacionDAO;
import servicios.ServiciosCongreso;
import servicios.TrabajoDAO;


//import entidades.Trabajo;
//import servicios.TrabajoDAO;


@Path("/trabajos")
public class TrabajoRESTController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) //ojo las {}
	public List<Trabajo> getAllTrabajos() {
		return TrabajoDAO.getInstance().findAll();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Trabajo getTrabajoById(@PathParam("id") String msg) {
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
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTrabajo(Trabajo trabajo) {
		Trabajo result= TrabajoDAO.getInstance().persist(trabajo);
//		ServiciosCongreso.getInstance().asignarEvaluador(trabajo);
		if(result==null) {
			throw new RecursoDuplicado(trabajo.getId());
		}else {
			return Response.status(201).entity(trabajo).build();
		}
	}
	
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTrabajo(@PathParam("id") int id) {
//		throw new UnsupportedOperationException();
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
	public Response updateTrabajo(@PathParam("id") int id, Trabajo trabajo) {
//		throw new UnsupportedOperationException();
		Trabajo result= TrabajoDAO.getInstance().update(id, trabajo);
		if(result==null) {
			throw new RecursoNoExiste(id);
		}else {
			return Response.status(200).entity(trabajo).build();
		}
	}
	
//	@GET
//	@Path("/findPerrosByEdad")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Perro> findPerrosByEdad(@QueryParam("from") int from,
//			@QueryParam("to") int to) {
//		throw new UnsupportedOperationException();
//	}

	public class RecursoDuplicado extends WebApplicationException {
	     public RecursoDuplicado(int id) {
	         super(Response.status(Response.Status.CONFLICT)
	             .entity("El recurso con ID "+id+" ya existe").type(MediaType.TEXT_PLAIN).build());
	     }
	}
	
	public class RecursoNoExiste extends WebApplicationException {
	     public RecursoNoExiste(int id) {
	         super(Response.status(Response.Status.NOT_FOUND)
	             .entity("El recurso con id "+id+" no fue encontrado").type(MediaType.TEXT_PLAIN).build());
	     }
	}
}
