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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import entidades.Empresa;
import entidades.Evaluacion;
import servicios.EmpresaDAO;



@Path("/empresas")
public class EmpresaRESTController implements controllerREST <Empresa, Integer> {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public List<Empresa> getAll() {
		return EmpresaDAO.getInstance().findAll();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Empresa getById(@PathParam("id") String msg) {
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
	public Response create(Empresa empresa) {
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
	public Response delete(@PathParam("id") Integer id) {
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
	public Response update(@PathParam("id") Integer id, Empresa empresa) {
		Empresa result= EmpresaDAO.getInstance().update(id, empresa);
		if(result==null) {
			throw new RecursoNoExiste(id);
		}else {
			return Response.status(200).entity(empresa).build();
		}
	}

}
