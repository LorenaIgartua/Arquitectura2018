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
import servicios.EvaluacionDAO;

//import entidades.Empresa;
//import servicios.EmpresaDAO;


@Path("/evaluaciones")
public class EvaluacionRESTController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) //ojo las {}
	public List<Evaluacion> getAllEvaluaciones() {
		return EvaluacionDAO.getInstance().findAll();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Evaluacion getEvaluacionById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Evaluacion evaluacion = EvaluacionDAO.getInstance().findById(id);
		if(evaluacion!=null)
			return evaluacion;
		else
			throw new RecursoNoExiste(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEvaluacion(Evaluacion evaluacion) {
		Evaluacion result= EvaluacionDAO.getInstance().persist(evaluacion);
		if(result==null) {
			throw new RecursoDuplicado(evaluacion.getId());
		}else {
			return Response.status(201).entity(evaluacion).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteEvaluacion(@PathParam("id") int id) {
//		throw new UnsupportedOperationException();
		boolean wasDeleted = EvaluacionDAO.getInstance().delete(id);
		if(wasDeleted)
			return Response.status(200).build();
		else
			throw new RecursoNoExiste(id);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEvaluacion(@PathParam("id") int id, Evaluacion evaluacion) {
//		throw new UnsupportedOperationException();
		Evaluacion result= EvaluacionDAO.getInstance().update(id, evaluacion);
		if(result==null) {
			throw new RecursoNoExiste(id);
		}else {
			return Response.status(200).entity(evaluacion).build();
		}
	}

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
