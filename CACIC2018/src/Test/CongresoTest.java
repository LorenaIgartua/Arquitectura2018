package Test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;
//import org.testng.AssertJUnit;
//import org.testng.AssertJUnit;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import Entidades.Empresa;
import Entidades.Evaluacion;
import Entidades.Tema;
import Entidades.Trabajo;
import Entidades.Usuario;
import Servicios.ServiciosCongreso;
import Servicios.ServiciosEmpresas;
import Servicios.ServiciosEmpresas;
import Servicios.ServiciosEvaluaciones;
import Servicios.ServiciosTemas;
import Servicios.ServiciosTrabajos;
import Servicios.ServiciosUsuarios;


public class CongresoTest {
	
	private static EntityManagerFactory emf;
	
	@BeforeClass
	public static void crearEntity () {
		emf = Persistence.createEntityManagerFactory("CACIC2018");
	}
	
	@Test
	public void crearCongreso() {
		
		EntityManager entitymanager = emf.createEntityManager();
		

		// b. Crear usuarios (10 usuarios).
		Usuario lorena = ServiciosUsuarios.altaUsuario("Lorena", "Igartua", entitymanager);
		Usuario mujica = ServiciosUsuarios.altaUsuario("Martin", "Mujica", entitymanager);
		Usuario carito = ServiciosUsuarios.altaUsuario("Carolina", "Igartua", entitymanager);
		Usuario roque = ServiciosUsuarios.altaUsuario("Roque", "Risiga", entitymanager);
		Usuario ramon = ServiciosUsuarios.altaUsuario("Ramon", "Torres", entitymanager);
		Usuario pedro = ServiciosUsuarios.altaUsuario("Pedro", "Valdez", entitymanager);
		Usuario carina = ServiciosUsuarios.altaUsuario("Carina", "Lopez", entitymanager);
		Usuario gaspar = ServiciosUsuarios.altaUsuario("Gaspar", "Salerno", entitymanager);
		Usuario adela = ServiciosUsuarios.altaUsuario("Adela", "Garcia", entitymanager);
		Usuario marce = ServiciosUsuarios.altaUsuario("Marcelo", "Gonzalez", entitymanager);
		
		// creo Empresas
		Empresa globant = ServiciosEmpresas.altaEmpresa("Globant SA", entitymanager);
		Empresa isistan = ServiciosEmpresas.altaEmpresa("ISISTAN - UNICEN", entitymanager);
		Empresa pladema = ServiciosEmpresas.altaEmpresa("PLADEMA - UNICEN", entitymanager);
		Empresa beereal = ServiciosEmpresas.altaEmpresa("BEE REAL SA", entitymanager);
		Empresa unitech = ServiciosEmpresas.altaEmpresa("Unitech SA", entitymanager);
		
		//creo temas o topicos
		Tema tema1 = ServiciosTemas.altaTema("phyton",true, entitymanager);
		Tema tema2 = ServiciosTemas.altaTema("javascript", false, entitymanager);
		Tema tema3 = ServiciosTemas.altaTema("html", false, entitymanager);
		Tema tema4 = ServiciosTemas.altaTema("Hivernete", true, entitymanager);
		Tema tema5 = ServiciosTemas.altaTema("Postgrest", false, entitymanager);
		Tema tema6 = ServiciosTemas.altaTema("PDO", false, entitymanager);
		Tema tema7 = ServiciosTemas.altaTema("google", false, entitymanager);
		Tema tema8 = ServiciosTemas.altaTema("JPA", false, entitymanager);
		Tema tema9 = ServiciosTemas.altaTema("Educacion a distancia", false, entitymanager);
		Tema tema10 = ServiciosTemas.altaTema("futuro", false, entitymanager);
		
		
		ServiciosUsuarios.agregarConocimiento(lorena, tema1, entitymanager);
		ServiciosUsuarios.agregarConocimiento(lorena, tema2, entitymanager);
		ServiciosUsuarios.agregarConocimiento(lorena, tema3, entitymanager);
		ServiciosUsuarios.agregarConocimiento(carito, tema1, entitymanager);
		ServiciosUsuarios.agregarConocimiento(carito, tema2, entitymanager);
		ServiciosUsuarios.agregarConocimiento(carito, tema3, entitymanager);
		ServiciosUsuarios.agregarConocimiento(carito, tema10, entitymanager);
		ServiciosUsuarios.agregarConocimiento(mujica, tema2, entitymanager);
		ServiciosUsuarios.agregarConocimiento(mujica, tema3, entitymanager);
		ServiciosUsuarios.agregarConocimiento(mujica, tema4, entitymanager);
		ServiciosUsuarios.agregarConocimiento(roque, tema4, entitymanager);
		ServiciosUsuarios.agregarConocimiento(roque, tema8, entitymanager);
		ServiciosUsuarios.agregarConocimiento(roque, tema6, entitymanager);
		ServiciosUsuarios.agregarConocimiento(adela, tema4, entitymanager);
		ServiciosUsuarios.agregarConocimiento(adela, tema5, entitymanager);
		ServiciosUsuarios.agregarConocimiento(adela, tema6, entitymanager);
		ServiciosUsuarios.agregarConocimiento(ramon, tema4, entitymanager);
		ServiciosUsuarios.agregarConocimiento(ramon, tema5, entitymanager);
		ServiciosUsuarios.agregarConocimiento(ramon, tema6, entitymanager);
		ServiciosUsuarios.agregarConocimiento(ramon, tema7, entitymanager);
		ServiciosUsuarios.agregarConocimiento(gaspar, tema6, entitymanager);
		ServiciosUsuarios.agregarConocimiento(gaspar, tema10, entitymanager);
		ServiciosUsuarios.agregarConocimiento(gaspar, tema8, entitymanager);
		ServiciosUsuarios.agregarConocimiento(marce, tema7, entitymanager);
		ServiciosUsuarios.agregarConocimiento(marce, tema10, entitymanager);
		ServiciosUsuarios.agregarConocimiento(marce, tema9, entitymanager);
		ServiciosUsuarios.agregarConocimiento(carina, tema7, entitymanager);
		ServiciosUsuarios.agregarConocimiento(carina, tema10, entitymanager);
		ServiciosUsuarios.agregarConocimiento(carina, tema9, entitymanager);
		
		ServiciosUsuarios.agregarLugarDeTrabajo(lorena, beereal, entitymanager);
		ServiciosUsuarios.agregarLugarDeTrabajo(carito, isistan, entitymanager);
		ServiciosUsuarios.agregarLugarDeTrabajo(mujica, globant, entitymanager);
		ServiciosUsuarios.agregarLugarDeTrabajo(roque, beereal, entitymanager);
		ServiciosUsuarios.agregarLugarDeTrabajo(marce, beereal, entitymanager);
		ServiciosUsuarios.agregarLugarDeTrabajo(adela, beereal, entitymanager);
		ServiciosUsuarios.agregarLugarDeTrabajo(pedro, pladema, entitymanager);
		ServiciosUsuarios.agregarLugarDeTrabajo(ramon, pladema, entitymanager);
		ServiciosUsuarios.agregarLugarDeTrabajo(gaspar, beereal, entitymanager);
		ServiciosUsuarios.agregarLugarDeTrabajo(carina, isistan, entitymanager);
		
		
		// c. Crear trabajos de investigación (10 trabajos de investigación).
		Trabajo trabajo1 = ServiciosTrabajos.altaTrabajo("base de datos", entitymanager);
		Trabajo trabajo2 = ServiciosTrabajos.altaTrabajo("sistema operativo", entitymanager);
		Trabajo trabajo3 = ServiciosTrabajos.altaTrabajo("programas para web", entitymanager);
		Trabajo trabajo4 = ServiciosTrabajos.altaTrabajo("inteligencia artificial", entitymanager);
		Trabajo trabajo5 = ServiciosTrabajos.altaTrabajo("documentacion y validacion", entitymanager);
		Trabajo trabajo6 = ServiciosTrabajos.altaTrabajo("java y html", entitymanager);
		Trabajo trabajo7 = ServiciosTrabajos.altaTrabajo("ultracasa", entitymanager);
		Trabajo trabajo8 = ServiciosTrabajos.altaTrabajo("mongosql vs sql", entitymanager);
		Trabajo trabajo9 = ServiciosTrabajos.altaTrabajo("tecnologia y salud", entitymanager);
		Trabajo trabajo10 = ServiciosTrabajos.altaTrabajo("orientando a educadores", entitymanager);
		
		
		ServiciosTrabajos.agregarPalabraClave(trabajo1, tema1, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo1, tema10, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo1, tema5, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo2, tema1, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo2, tema2, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo3, tema1, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo3, tema10, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo4, tema5, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo5, tema1, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo9, tema8, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo3, tema7, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo4, tema6, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo5, tema10, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo8, tema8, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo8, tema10, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo9, tema5, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo3, tema10, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo4, tema9, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo7, tema8, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo10, tema1, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo10, tema10, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo10, tema5, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo6, tema1, entitymanager);
		ServiciosTrabajos.agregarPalabraClave(trabajo6, tema8, entitymanager);
		
		ServiciosTrabajos.agregarAutor(trabajo1, lorena, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo1, carito, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo1, mujica, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo2, mujica, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo2, adela, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo3, carina, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo3, ramon, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo4, gaspar, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo5, carito, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo6, marce, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo6, lorena, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo8, mujica, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo9, roque, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo7, lorena, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo10, adela, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo10, marce, entitymanager);
		ServiciosTrabajos.agregarAutor(trabajo10, ramon, entitymanager);
		
		ServiciosTrabajos.setEsPoster(trabajo1, true, entitymanager);
		ServiciosTrabajos.setEsPoster(trabajo3, true, entitymanager);
		ServiciosTrabajos.setEsPoster(trabajo5, true, entitymanager);
		ServiciosTrabajos.setEsPoster(trabajo8, true, entitymanager);
		ServiciosTrabajos.setEsPoster(trabajo10, true, entitymanager);
		
		
		
		
		Trabajo auxTrabajo = ServiciosTrabajos.buscarTrabajoPorNombre("base de datos", entitymanager);
		assertEquals(auxTrabajo, trabajo1);
		
		List<Usuario> consulta4 = ServiciosUsuarios.listarUsuarios(entitymanager);
		assertEquals(10, consulta4.size());
		
		List<Trabajo> consulta14 = ServiciosTrabajos.listarTrabajos(entitymanager);
		assertEquals(10, consulta14.size());
		
//		asigno evaluadores a cada trabajo recibido
		ServiciosEvaluaciones.altaEvaluacion(trabajo1, entitymanager);
		ServiciosEvaluaciones.altaEvaluacion(trabajo2, entitymanager);
		ServiciosEvaluaciones.altaEvaluacion(trabajo3, entitymanager);
		ServiciosEvaluaciones.altaEvaluacion(trabajo4, entitymanager);
		ServiciosEvaluaciones.altaEvaluacion(trabajo5, entitymanager);
		ServiciosEvaluaciones.altaEvaluacion(trabajo6, entitymanager);
		ServiciosEvaluaciones.altaEvaluacion(trabajo7, entitymanager);
		ServiciosEvaluaciones.altaEvaluacion(trabajo8, entitymanager);
		ServiciosEvaluaciones.altaEvaluacion(trabajo9, entitymanager);
		ServiciosEvaluaciones.altaEvaluacion(trabajo10, entitymanager);
		
	
		System.out.println("d. Consultar autores, revisores y trabajos de investigación. En particular se debe probar:");
		
		System.out.println("i. Consulta de todos los datos de un autor/revisor.");
		Usuario auxUsuario = ServiciosUsuarios.buscarUsuarioPorNombreYApellido("Lorena", "Igartua", entitymanager);
		System.out.println(auxUsuario.toString());
		assertEquals(auxUsuario, lorena);
		
		System.out.println(" ");
		System.out.println("ii. Dado un revisor, retornar todos sus trabajos asignados.");
		
		List<Trabajo> consulta12 = ServiciosEvaluaciones.buscarTrabajosDeUnEvaluador(carito, entitymanager);
		for (Trabajo t :  consulta12) {
			System.out.println(t.getTitulo());
		}
		
		System.out.println(" ");
		System.out.println("iii. Dado un revisor y un rango de fechas, retornar todas sus revisiones.");
		LocalDate fin = LocalDate.now().plusDays(1);
		LocalDate inicio = fin.minusDays(10);
		System.out.println("Desde: " + inicio + " Hasta: " + fin );
		List<Evaluacion> consulta5 = ServiciosEvaluaciones.buscarEvaluacionPorRangoFechas(inicio, fin, marce, entitymanager);
		for (Evaluacion e :  consulta5) {
			System.out.println(e.toString());
		}
		
		System.out.println(" ");
		System.out.println("iv. Dado un autor, retornar todos los trabajos de investigación enviados.");
		List<Trabajo> consultaAutorTrabajo = ServiciosUsuarios.buscarTrabajoPorAutor(lorena, entitymanager);
		for (Trabajo t : consultaAutorTrabajo) {
			System.out.println(t.getTitulo());
		}
		
		System.out.println(" ");
		System.out.println("f. Consultar trabajos de investigación y sus propiedades.");
		List<Trabajo> consulta8 = ServiciosTrabajos.listarTrabajos(entitymanager);
		for (Trabajo t : consulta8) {
			System.out.println(t.toString());
		}
		
		System.out.println(" ");
		System.out.println("g. Seleccionar trabajos de investigación de un autor y revisor en una\r\n" + 
				"//	determinada área de investigación utilizando consultas JPQL.");
		List<Trabajo> consulta7 = ServiciosCongreso.buscarTrabajosSegunAutorEvaluadorTema(mujica, carito, tema1, entitymanager);
		for (Trabajo t : consulta7) {
			System.out.println(t.getTitulo());
		}
		
		System.out.println(" ");	
		
		entitymanager.close();
		
	}
	
	@AfterClass
	public static void cerrarEntity () {
		emf.close();
	}
		

}
