package controllerREST;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import entidades.Empresa;
import entidades.Evaluacion;
import entidades.Trabajo;
import entidades.Usuario;
import servicios.EvaluacionDAO;
import servicios.TrabajoDAO;
import servicios.UsuarioDAO;



@Path("/evaluaciones")
public class EvaluacionRESTController implements controllerREST <Evaluacion, Integer> {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
	public List<Evaluacion> getAll() {
		return EvaluacionDAO.getInstance().findAll();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Evaluacion getById(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Evaluacion evaluacion = EvaluacionDAO.getInstance().findById(id);
		if(evaluacion!=null)
			return evaluacion;
		else
			throw new RecursoNoExiste(id);
	}
	
	@GET
	@Path("/trabajosRevisadosPor/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Trabajo> trabajosRevisadosPorEvaluador(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Usuario usuario = UsuarioDAO.getInstance().findById(id);
		if(usuario!=null)
			return EvaluacionDAO.getInstance().buscarTrabajosDeUnEvaluador(usuario);
		else
			throw new RecursoNoExiste(id);
	}
	
	@GET
	@Path("/trabajosRevisadosPorEvalEnRango/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Trabajo> trabajosRevisadosPorEvaluadorEnRangoFechas(@PathParam("id") String msg, 
			@QueryParam("fromD") String fromDia, @QueryParam("fromM") String fromMes, @QueryParam("fromY") String fromAño,  
			@QueryParam("toD") String toDia, @QueryParam("toM") String toMes, @QueryParam("toY") String toAño ){
//			@PathParam("from") LocalDate from, @PathParam("to") LocalDate to) {
		int id = Integer.valueOf(msg);
		Usuario usuario = UsuarioDAO.getInstance().findById(id);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String inicio = fromDia +"-"+fromMes+"-"+fromAño;
		String fin = toDia +"-"+toMes+"-"+toAño;;
		LocalDate dateInicio = LocalDate.parse(inicio, formatter);
		LocalDate dateFin = LocalDate.parse(fin, formatter);
		
		if(usuario!=null)
			return EvaluacionDAO.getInstance().buscarEvaluacionPorRangoFechas(dateInicio, dateFin, usuario);
		else
			throw new RecursoNoExiste(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Evaluacion evaluacion) {
		Evaluacion result= EvaluacionDAO.getInstance().persist(evaluacion);
		if(result==null) {
			throw new RecursoDuplicado(evaluacion.getId());
		}else {
			return Response.status(201).entity(evaluacion).build();
		}
	}
	
	@POST
	@Path("/asignarEvaluador/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response asignarEvaluadorATrabajo(@PathParam("id") String msg) {
		int id = Integer.valueOf(msg);
		Trabajo trabajo = TrabajoDAO.getInstance().findById(id);
		if(trabajo == null) {
			throw new RecursoNoExiste(id);
		} else {
			List<Evaluacion> aux = EvaluacionDAO.getInstance().asignarEvaluador(trabajo);
			return Response.status(201).entity(aux).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") Integer id) {
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
	public Response update(@PathParam("id") Integer id, Evaluacion evaluacion) {
		Evaluacion result= EvaluacionDAO.getInstance().update(id, evaluacion);
		if(result==null) {
			throw new RecursoNoExiste(id);
		}else {
			return Response.status(200).entity(evaluacion).build();
		}
	}

}
