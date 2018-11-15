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
import entidades.Usuario;
import servicios.EvaluacionDAO;
import servicios.UsuarioDAO;


//import entidades.Usuario;
//import servicios.UsuarioDAO;


@Path("/usuarios")
public class UsuarioRESTController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) //ojo las {}
	public List<Usuario> getAllUsuarios() {
		return UsuarioDAO.getInstance().findAll();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario getUsuarioById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Usuario usuario = UsuarioDAO.getInstance().findById(id);
		if(usuario!=null)
			return usuario;
		else
			throw new RecursoNoExiste(id);
	}
	
	@GET
	@Path("/{id}/trabajos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Trabajo> getTrabajosDeUnUsuarioById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Usuario usuario = UsuarioDAO.getInstance().findById(id);
		if(usuario!=null)
			return UsuarioDAO.getInstance().buscarTrabajoPorAutor(usuario);
		else
			throw new RecursoNoExiste(id);
	}
	
	@GET
	@Path("/{id}/evaluaciones")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Evaluacion> getEvaluacionesDeUnUsuarioById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Usuario usuario = UsuarioDAO.getInstance().findById(id);
		if(usuario!=null)
			return EvaluacionDAO.getInstance().buscarEvaluacionPorEvaluador(usuario);
		else
			throw new RecursoNoExiste(id);
	}
	
	@GET
	@Path("/{id}/temas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tema> getConocimientosDeUnUsuarioById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Usuario usuario = UsuarioDAO.getInstance().findById(id);
		if(usuario!=null)
			return UsuarioDAO.getInstance().conocimientosDeUnUsuario(usuario);
		else
			throw new RecursoNoExiste(id);
	}
		
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUsuario(Usuario usuario) {
		Usuario result= UsuarioDAO.getInstance().persist(usuario);
		if(result==null) {
			throw new RecursoDuplicado(usuario.getId());
		}else {
			return Response.status(201).entity(usuario).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUsuario(@PathParam("id") int id) {
//		throw new UnsupportedOperationException();
		boolean wasDeleted = UsuarioDAO.getInstance().delete(id);
		if(wasDeleted)
			return Response.status(200).build();
		else
			throw new RecursoNoExiste(id);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUsuario(@PathParam("id") int id, Usuario usuario) {
//		throw new UnsupportedOperationException();
		Usuario result= UsuarioDAO.getInstance().update(id, usuario);
		if(result==null) {
			throw new RecursoNoExiste(id);
		}else {
			return Response.status(200).entity(usuario).build();
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
