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
import entidades.Empresa;
import entidades.Perro;
import servicios.EmpresaDAO;
import servicios.PerroDAO;

//import entidades.Empresa;
//import servicios.EmpresaDAO;


@Path("/empresas")
public class EmpresaRESTController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) //ojo las {}
	public List<Empresa> getAllEmpresas() {
		return EmpresaDAO.getInstance().findAll();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Empresa getEmpresaById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Empresa empresa = EmpresaDAO.getInstance().findById(id);
		if(empresa!=null)
			return empresa;
		else
			throw new RecursoNoExiste(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEmpresa(Empresa empresa) {
		Empresa result= EmpresaDAO.getInstance().persist(empresa);
		if(result==null) {
			throw new RecursoDuplicado(empresa.getId());
		}else {
			return Response.status(201).entity(empresa).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteEmpresa(@PathParam("id") int id) {
//		throw new UnsupportedOperationException();
		boolean wasDeleted = EmpresaDAO.getInstance().delete(id);
		if(wasDeleted)
			return Response.status(200).build();
		else
			throw new RecursoNoExiste(id);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEmpresa(@PathParam("id") int id, Empresa empresa) {
//		throw new UnsupportedOperationException();
		Empresa result= EmpresaDAO.getInstance().update(id, empresa);
		if(result==null) {
			throw new RecursoNoExiste(id);
		}else {
			return Response.status(200).entity(empresa).build();
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
