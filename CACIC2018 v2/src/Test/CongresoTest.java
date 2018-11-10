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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

import Entidades.Empresa;
import Entidades.Evaluacion;
import Entidades.Tema;
import Entidades.Trabajo;
import Entidades.Usuario;
import Servicios.ServiciosCongreso;
import Servicios.EMF;
import Servicios.EmpresaDAO;
import Servicios.EvaluacionDAO;
import Servicios.TemaDAO;
import Servicios.TrabajoDAO;
import Servicios.UsuarioDAO;


public class CongresoTest {
	
	private static final int CANTIDAD = 10;

	@Test
	public void crearCongreso() {
		
		
		//creo Empresas
		Empresa globant = new Empresa("Globant SA");
		Empresa isistan = new Empresa("ISISTAN - UNICEN");
		Empresa pladema = new Empresa("PLADEMA - UNICEN");
		Empresa beereal = new Empresa("BEE REAL SA");
		Empresa unitech = new Empresa("Unitech SA");
		
		EmpresaDAO.getInstance().persist(globant);
		EmpresaDAO.getInstance().persist(isistan);
		EmpresaDAO.getInstance().persist(pladema);
		EmpresaDAO.getInstance().persist(beereal);
		EmpresaDAO.getInstance().persist(unitech);
		
//				List<Empresa> consulta12 = EmpresaDAO.getInstance().findAll();
//				for (Empresa e :  consulta12) {
//					System.out.println(e.getNombre());
//				}
		
////			System.out.println(EmpresaDAO.getInstance().findById(globant.getId()).toString());
		
//				assertEquals( globant, EmpresaDAO.getInstance().findById(globant.getId()) );
		
		assertTrue(EmpresaDAO.getInstance().findById(globant.getId()).getNombre().equals("Globant SA"));
		
//		assertTrue(EmpresaDAO.getInstance().delete(unitech.getId()));
		assertFalse(EmpresaDAO.getInstance().delete(unitech.getCUIT()));
	
	
		//creo Temas(conocimientos)
		Tema tema1 = new Tema ("phyton",true);
		Tema tema2 = new Tema ("javascript", false);
		Tema tema3 = new Tema ("html", false);
		Tema tema4 = new Tema ("Hivernete", true);
		Tema tema5 = new Tema ("Postgrest", false);
		Tema tema6 = new Tema ("PDO", false);
		Tema tema7 = new Tema ("google", false);
		Tema tema8 = new Tema ("JPA", false);
		Tema tema9 = new Tema ("Educacion a distancia", false);
		Tema tema10 = new Tema ("futuro", false);
		
		TemaDAO.getInstance().persist(tema1);
		TemaDAO.getInstance().persist(tema2);
		TemaDAO.getInstance().persist(tema3);
		TemaDAO.getInstance().persist(tema4);
		TemaDAO.getInstance().persist(tema5);
		TemaDAO.getInstance().persist(tema6);
		TemaDAO.getInstance().persist(tema7);
		TemaDAO.getInstance().persist(tema8);
		TemaDAO.getInstance().persist(tema9);
		TemaDAO.getInstance().persist(tema10);
		
		assertFalse(TemaDAO.getInstance().findById(tema2.getId()).getNombre().equals("phyton"));
		
//				List<Tema> consulta11 = TemaDAO.getInstance().findAll();
//				for (Tema t :  consulta11) {
//					System.out.println(t.toString());
//				}
		
		tema3.setEsEspecifico(true);
		TemaDAO.getInstance().update(tema3.getId(),tema3);

		
		//creo Usuarios 
		System.out.println("b. Crear usuarios (10 usuarios)");
		
		Usuario lorena = new Usuario("Lorena", "Igartua");
		Usuario mujica = new Usuario("Martin", "Mujica");
		Usuario carito = new Usuario("Carolina", "Igartua");
		Usuario roque = new Usuario("Roque", "Risiga");
		Usuario ramon = new Usuario("Ramon", "Torres");
		Usuario pedro = new Usuario("Pedro", "Valdez");
		Usuario carina = new Usuario("Carina", "Lopez");
		Usuario gaspar = new Usuario("Gaspar", "Salerno");
		Usuario adela = new Usuario("Adela", "Garcia");
		Usuario marce = new Usuario("Marcelo", "Gonzalez");
		
		//actualizo datos de los usuarios (conocimientos y lugar de trabajo)		
		lorena.agregarConocimiento(tema1);
		lorena.agregarConocimiento( tema2);
		lorena.agregarConocimiento(tema3);
		carito.agregarConocimiento(tema1);
		carito.agregarConocimiento(tema2);
		carito.agregarConocimiento(tema3);
		carito.agregarConocimiento(tema10);
		mujica.agregarConocimiento(tema2);
		mujica.agregarConocimiento(tema3);
		mujica.agregarConocimiento(tema4);
		roque.agregarConocimiento(tema4);
		roque.agregarConocimiento(tema8);
		roque.agregarConocimiento(tema6);
		adela.agregarConocimiento(tema4);
		adela.agregarConocimiento(tema5);
		adela.agregarConocimiento(tema6);
		ramon.agregarConocimiento(tema4);
		ramon.agregarConocimiento(tema5);
		ramon.agregarConocimiento(tema6);
		ramon.agregarConocimiento(tema7);
		gaspar.agregarConocimiento(tema6);
		gaspar.agregarConocimiento(tema10);
		gaspar.agregarConocimiento(tema8);
		marce.agregarConocimiento(tema7);
		marce.agregarConocimiento(tema10);
		marce.agregarConocimiento(tema9);
		carina.agregarConocimiento(tema7);
		carina.agregarConocimiento(tema10);
		carina.agregarConocimiento(tema9);
		pedro.agregarConocimiento(tema6);
		
		lorena.setEmpresa(beereal);
		carito.setEmpresa(isistan);
		mujica.setEmpresa(globant);
		roque.setEmpresa(beereal);
		marce.setEmpresa(beereal);
		adela.setEmpresa(unitech);
		pedro.setEmpresa(pladema);
		ramon.setEmpresa(pladema);
		gaspar.setEmpresa(beereal);
		carina.setEmpresa(isistan);
		
		UsuarioDAO.getInstance().persist(lorena);
		UsuarioDAO.getInstance().persist(mujica);
		UsuarioDAO.getInstance().persist(carito);
		UsuarioDAO.getInstance().persist(roque);
		UsuarioDAO.getInstance().persist(ramon);
		UsuarioDAO.getInstance().persist(pedro);
		UsuarioDAO.getInstance().persist(carina);
		UsuarioDAO.getInstance().persist(gaspar);
		UsuarioDAO.getInstance().persist(adela);
		UsuarioDAO.getInstance().persist(marce);
		
		
		System.out.println(UsuarioDAO.getInstance().findById(lorena.getId()).toString());
		assertTrue(UsuarioDAO.getInstance().findAll().size() == CANTIDAD);
	
		
//		UsuarioDAO.getInstance().update(lorena.getId(), lorena);
//		UsuarioDAO.getInstance().update(carito.getId(), carito);
//		UsuarioDAO.getInstance().update(mujica.getId(), mujica);
//		UsuarioDAO.getInstance().update(roque.getId(), roque);
//		UsuarioDAO.getInstance().update(marce.getId(), marce);
//		UsuarioDAO.getInstance().update(adela.getId(), adela);
//		UsuarioDAO.getInstance().update(ramon.getId(), ramon);
//		UsuarioDAO.getInstance().update(pedro.getId(), pedro);
//		UsuarioDAO.getInstance().update(gaspar.getId(), gaspar);
//		UsuarioDAO.getInstance().update(carina.getId(), carina);
		
		List<Usuario> consulta5 = UsuarioDAO.getInstance().findAll();
		for (Usuario u :  consulta5) {
			System.out.println(u.toString());
		}
		
		assertEquals(CANTIDAD, consulta5.size());
	
		
	// c. Crear trabajos de investigación (10 trabajos de investigación).
		Trabajo trabajo1 = new Trabajo("base de datos");
		Trabajo trabajo2 = new Trabajo("sistema operativo");
		Trabajo trabajo3 = new Trabajo("programas para web");
		Trabajo trabajo4 = new Trabajo("inteligencia artificial");
		Trabajo trabajo5 = new Trabajo("documentacion y validacion");
		Trabajo trabajo6 = new Trabajo("java y html");
		Trabajo trabajo7 = new Trabajo("ultracasa");
		Trabajo trabajo8 = new Trabajo("mongosql vs sql");
		Trabajo trabajo9 = new Trabajo("tecnologia y salud");
		Trabajo trabajo10 = new Trabajo("orientando a educadores");
		
		trabajo1.agregarPalabraClave(tema1);
		trabajo1.agregarPalabraClave(tema10);
		trabajo1.agregarPalabraClave(tema5);
		trabajo2.agregarPalabraClave(tema1);
		trabajo2.agregarPalabraClave(tema2);
		trabajo3.agregarPalabraClave(tema1);
		trabajo3.agregarPalabraClave(tema10);
		trabajo4.agregarPalabraClave(tema5);
		trabajo5.agregarPalabraClave(tema1);
		trabajo9.agregarPalabraClave(tema8);
		trabajo3.agregarPalabraClave(tema7);
		trabajo4.agregarPalabraClave(tema6);
		trabajo5.agregarPalabraClave(tema10);
		trabajo8.agregarPalabraClave(tema8);
		trabajo8.agregarPalabraClave(tema10);
		trabajo9.agregarPalabraClave(tema5);
		trabajo3.agregarPalabraClave(tema10);
		trabajo4.agregarPalabraClave(tema9);
		trabajo7.agregarPalabraClave(tema8);
		trabajo10.agregarPalabraClave(tema1);
		trabajo10.agregarPalabraClave(tema10);
		trabajo10.agregarPalabraClave(tema5);
		trabajo6.agregarPalabraClave(tema1);
		trabajo6.agregarPalabraClave(tema8);
		
		trabajo1.agregarAutor(lorena);
		trabajo1.agregarAutor(carito);
		trabajo1.agregarAutor(mujica);
		trabajo2.agregarAutor(mujica);
		trabajo2.agregarAutor(adela);
		trabajo3.agregarAutor(carina);
		trabajo3.agregarAutor(ramon);
		trabajo4.agregarAutor(gaspar);
		trabajo5.agregarAutor(carito);
		trabajo6.agregarAutor(marce);
		trabajo6.agregarAutor(lorena);
		trabajo8.agregarAutor(mujica);
		trabajo9.agregarAutor(roque);
		trabajo7.agregarAutor(lorena);
		trabajo10.agregarAutor(adela);
		trabajo10.agregarAutor(marce);
		trabajo10.agregarAutor(ramon);
		
		TrabajoDAO.getInstance().persist(trabajo1);
		TrabajoDAO.getInstance().persist(trabajo2);
		TrabajoDAO.getInstance().persist(trabajo3);
		TrabajoDAO.getInstance().persist(trabajo4);
		TrabajoDAO.getInstance().persist(trabajo5);
		TrabajoDAO.getInstance().persist(trabajo6);
		TrabajoDAO.getInstance().persist(trabajo7);
		TrabajoDAO.getInstance().persist(trabajo8);
		TrabajoDAO.getInstance().persist(trabajo9);
		TrabajoDAO.getInstance().persist(trabajo10);
		
		trabajo1.setEsPoster(true);
		trabajo3.setEsPoster(true);
		trabajo5.setEsPoster(true);
		trabajo8.setEsPoster(true);
		trabajo10.setEsPoster(true);
		
		TrabajoDAO.getInstance().update(trabajo1.getId(),trabajo1);
		TrabajoDAO.getInstance().update(trabajo3.getId(), trabajo3);
		TrabajoDAO.getInstance().update(trabajo5.getId(), trabajo5);
		TrabajoDAO.getInstance().update(trabajo8.getId(), trabajo8);
		TrabajoDAO.getInstance().update(trabajo10.getId(), trabajo10);
		
		List<Trabajo> consulta4 = TrabajoDAO.getInstance().findAll();
		for(Trabajo t : consulta4) {
			System.out.println(t.toString());
		}
		
		assertEquals(CANTIDAD, consulta4.size());
		
		ServiciosCongreso cacic = new ServiciosCongreso();
		cacic.asignarEvaluador(trabajo1);
		cacic.asignarEvaluador(trabajo2);
		cacic.asignarEvaluador(trabajo3);
		cacic.asignarEvaluador(trabajo4);
		cacic.asignarEvaluador(trabajo5);
		cacic.asignarEvaluador(trabajo6);
		cacic.asignarEvaluador(trabajo7);
		cacic.asignarEvaluador(trabajo8);
		cacic.asignarEvaluador(trabajo9);
		cacic.asignarEvaluador(trabajo10);

		
		
		List<Evaluacion> consulta20 = EvaluacionDAO.getInstance().findAll();
		for (Evaluacion e :  consulta20) {
			System.out.println(e.toString());
		}

	
		System.out.println("d. Consultar autores, revisores y trabajos de investigación. En particular se debe probar:");
		
		System.out.println("i. Consulta de todos los datos de un autor/revisor.");   //ver!!!!!
//		Usuario auxUsuario = UsuarioDAO.getInstance().buscarUsuarioPorNombreYApellido("Lorena", "Igartua");
//		System.out.println(auxUsuario.toString());
		UsuarioDAO.getInstance().buscarDatosUsuario(lorena);
//		assertEquals(auxUsuario, lorena);
		
		System.out.println(" ");
		System.out.println("ii. Dado un revisor, retornar todos sus trabajos asignados.");
		
		List<Trabajo> consulta12 = EvaluacionDAO.getInstance().buscarTrabajosDeUnEvaluador(carito);
		for (Trabajo t :  consulta12) {
			System.out.println(t.getTitulo());
		}
		
		System.out.println(" ");
		System.out.println("iii. Dado un revisor y un rango de fechas, retornar todas sus revisiones.");
		LocalDate fin = LocalDate.now().plusDays(1);
		LocalDate inicio = fin.minusDays(10);
		System.out.println("Desde: " + inicio + " Hasta: " + fin );
		List<Evaluacion> consulta25 = EvaluacionDAO.getInstance().buscarEvaluacionPorRangoFechas(inicio, fin, marce);
		for (Evaluacion e :  consulta25) {
			System.out.println(e.toString());
		}
		
		System.out.println(" ");
		System.out.println("iv. Dado un autor, retornar todos los trabajos de investigación enviados.");
		List<Trabajo> consultaAutorTrabajo = UsuarioDAO.getInstance().buscarTrabajoPorAutor(lorena);
		for (Trabajo t : consultaAutorTrabajo) {
			System.out.println(t.getTitulo());
		}
		
		System.out.println(" ");
		System.out.println("f. Consultar trabajos de investigación y sus propiedades.");  //ver completar mas datos
		System.out.println("Todos los trabajos presentados son: ");
		List<Trabajo> consulta8 = TrabajoDAO.getInstance().findAll();
		for (Trabajo t : consulta8) {
			System.out.println(t.toString());
		}
		System.out.println(" ");
		System.out.println("Todos los datos de un trabajo en particular son: ");
		TrabajoDAO.getInstance().buscarDatosTrabajo(trabajo1);
		
		System.out.println(" ");
		System.out.println("g. Seleccionar trabajos de investigación de un autor y revisor en una\r\n" + 
				"//	determinada área de investigación utilizando consultas JPQL.");
		List<Trabajo> consulta7 = cacic.buscarTrabajosSegunAutorEvaluadorTema(mujica, carito, tema1);
		for (Trabajo t : consulta7) {
			System.out.println(t.getTitulo());
		}
		
		System.out.println(" ");	
		
//		entitymanager.close();
//		
//	}
	
//	@AfterClass
//	public static void cerrarEntity () {
//		emf.close();
//	}
		
	}
}
