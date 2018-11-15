package testREST;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({EmpresaRESTTest.class, TemaRESTTest.class,
	UsuarioRESTTest.class, TrabajoRESTTest.class, EvaluacionRESTTest.class})
public class TestSuite {

}
