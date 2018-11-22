package controllerREST;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RecursoNoExiste extends WebApplicationException {

	public RecursoNoExiste(int id) {
        super(Response.status(Response.Status.NOT_FOUND)
            .entity("El recurso con id "+id+" no fue encontrado").type(MediaType.TEXT_PLAIN).build());
    }
	
}
