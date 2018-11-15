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

import controllerREST.PerroRESTController.RecursoDuplicado;
import controllerREST.PerroRESTController.RecursoNoExiste;
import entidades.Tema;
import servicios.TemaDAO;

//import entidades.Empresa;
//import servicios.EmpresaDAO;


@Path("/temas")
public class TemaRESTController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) //ojo las {}
	public List<Tema> getAllEmpresas() {
		return TemaDAO.getInstance().findAll();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Tema getTemaById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Tema tema = TemaDAO.getInstance().findById(id);
		if(tema!=null)
			return tema;
		else
			throw new RecursoNoExiste(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTema(Tema tema) {
		Tema result= TemaDAO.getInstance().persist(tema);
		if(result==null) {
			throw new RecursoDuplicado(tema.getId());
		}else {
			return Response.status(201).entity(tema).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTema(@PathParam("id") int id) {
//		throw new UnsupportedOperationException();
		boolean wasDeleted = TemaDAO.getInstance().delete(id);
		if(wasDeleted)
			return Response.status(200).build();
		else
			throw new RecursoNoExiste(id);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTema(@PathParam("id") int id, Tema tema) {
////		throw new UnsupportedOperationException();
		Tema result= TemaDAO.getInstance().update(id, tema);
		if(result==null) {
			throw new RecursoNoExiste(id);
		}else {
			return Response.status(200).entity(tema).build();
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
