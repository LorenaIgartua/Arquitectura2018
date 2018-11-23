package testREST;


import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import entidades.Empresa;
import servicios.EmpresaDAO;




public class TestCACIC {

	private final int CANT_EMPRESAS = 5;
	private final int CANTIDAD = 10;
	
	private  final String BASE_URL="http://localhost:8080/CONGRESO/api";
	private  final HttpClient client = HttpClientBuilder.create().build();
	
	ObjectMapper mapper = new ObjectMapper();
	
	int[] idsEmpresas = new int[CANT_EMPRESAS];
	int[] idsTemas = new int[CANTIDAD];
	int[] idsUsuarios = new int[CANTIDAD];
	int[] idsTrabajos = new int[CANTIDAD];
	 

	


	@Test
	public void testRESTCongreso() throws ClientProtocolException, IOException {
		
//	b. Implementar casos de test con un cliente HTTP para la siguiente funcionalidad:
		testCrearEmpresas();
		testCrearTemas();
		testCrearUsuarios();   // i. Crear usuarios.
		testCrearTrabajos();    // ii. Crear trabajos de investigación.
		testUpdateUsuario();    // iii. Modificación de datos de usuario.
		testAsignarTrabajoAEvaluadores();  // iv. Asignar un trabajo a un revisor, chequeando las restricciones.
		testGetTrabajosDeUnAutor();    // v. Retornar todas los trabajos enviados por un autor determinado.
		testGetTrabajosDeUnRevisorEnRangoFechas();  //vi. Retornar todas los trabajos revisados por un revisor determinado
														// dado un rango de fechas. 
	}


	public void testCrearEmpresas() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/empresas";
		
		//empresa 1

		ObjectNode jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Isistan");
		jsonObject.put("direccion", "Unicen - Tandil");
		jsonObject.put("telefono", "4375873");
		jsonObject.put("cuit", "4331990");
		String jsonString = jsonObject.toString();

		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		JsonNode jsonContent = this.mapper.readTree(resultContent);
		this.idsEmpresas[0] = jsonContent.get("id").asInt();
		
		//empresa 2
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Globant SA");
		jsonObject.put("direccion", "9 de Julio 762 - Tandil");
		jsonObject.put("telefono", "478873");
		jsonObject.put("cuit", "6757920");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsEmpresas[1] = jsonContent.get("id").asInt();

		//empresa 3
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Unitech SA");
		jsonObject.put("direccion", "Rodriguez 620 - Tandil");
		jsonObject.put("telefono", "4787387");
		jsonObject.put("cuit", "7645834");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsEmpresas[2] = jsonContent.get("id").asInt();
		
		//empresa 4
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Pladema");
		jsonObject.put("direccion", "Unicen - Tandil");
		jsonObject.put("telefono", "34543");
		jsonObject.put("cuit", "5454332");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsEmpresas[3] = jsonContent.get("id").asInt();
		
		//empresa 5
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "BeeReal SRL");
		jsonObject.put("direccion", "Chacabuco 433 - Tandil");
		jsonObject.put("telefono", "345132");
		jsonObject.put("cuit", "6648723");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsEmpresas[4] = jsonContent.get("id").asInt();
		
		// luego de crear 5 empresas, controlo que todas se persistieron
		HttpGet requestGet = new HttpGet(url);
		response = client.execute(requestGet);
		String resultContentEmpresa = getResultContent(response);
	
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		
		jsonContent = this.mapper.readTree(resultContentEmpresa);
		Assert.assertEquals(jsonContent.findValues("id").size(), CANT_EMPRESAS);
		
	}

	public void testCrearTemas() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/temas";

		//tema 1		

		ObjectNode jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "phyton");
		jsonObject.put("esEspecifico", "true");
		String jsonString = jsonObject.toString();

		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		JsonNode jsonContent = this.mapper.readTree(resultContent);
		this.idsTemas[0] = jsonContent.get("id").asInt();

		//tema 2	
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "javascript");
		jsonObject.put("esEspecifico", "false");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTemas[1] = jsonContent.get("id").asInt();
		
		//tema 3
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "html");
		jsonObject.put("esEspecifico", "true");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTemas[2] = jsonContent.get("id").asInt();
		
		//tema 4
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Hivernete");
		jsonObject.put("esEspecifico", "true");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		this.idsTemas[3] = jsonContent.get("id").asInt();
		
		//tema 5
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Postgrest");
		jsonObject.put("esEspecifico", "false");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTemas[4] = jsonContent.get("id").asInt();
		
		//tema 6
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "PDO");
		jsonObject.put("esEspecifico", "false");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTemas[5] = jsonContent.get("id").asInt();
		
		//tema 7
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "google");
		jsonObject.put("esEspecifico", "true");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTemas[6] = jsonContent.get("id").asInt();
		
		//tema 8
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "JPA");
		jsonObject.put("esEspecifico", "false");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTemas[7] = jsonContent.get("id").asInt();
		
		//tema 9
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Educacion a distancia");
		jsonObject.put("esEspecifico", "false");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTemas[8] = jsonContent.get("id").asInt();
		
		//tema 10
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "futuro");
		jsonObject.put("esEspecifico", "true");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTemas[9] = jsonContent.get("id").asInt();
		
		// luego de crear 10 temas, controlo que todas se persistieron
		HttpGet requestGet = new HttpGet(url);
		response = client.execute(requestGet);
		String resultContentEmpresa = getResultContent(response);
	
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		
		jsonContent = this.mapper.readTree(resultContentEmpresa);
		Assert.assertEquals(jsonContent.findValues("id").size(), CANTIDAD);
	}
	

	public void testCrearUsuarios() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/usuarios";

		String tema1URL= BASE_URL + "/temas/"+this.idsTemas[0];
		HttpGet request1 = new HttpGet(tema1URL);
		HttpResponse response1 = client.execute(request1);
		String resultContentTema1 = getResultContent(response1);
		
		String tema2Url= BASE_URL + "/temas/"+this.idsTemas[1];
		HttpGet request2 = new HttpGet(tema2Url);
		HttpResponse response2 = client.execute(request2);
		String resultContentTema2 = getResultContent(response2);
		
		String tema3Url= BASE_URL + "/temas/"+this.idsTemas[2];
		HttpGet request3 = new HttpGet(tema3Url);
		HttpResponse response3 = client.execute(request3);
		String resultContentTema3 = getResultContent(response3);
		
		String tema4Url= BASE_URL + "/temas/"+this.idsTemas[3];
		HttpGet request4 = new HttpGet(tema4Url);
		HttpResponse response4 = client.execute(request4);
		String resultContentTema4 = getResultContent(response4);
		
		String tema5Url= BASE_URL + "/temas/"+this.idsTemas[4];
		HttpGet request5 = new HttpGet(tema5Url);
		HttpResponse response5 = client.execute(request5);
		String resultContentTema5 = getResultContent(response5);
		
		String tema6Url= BASE_URL + "/temas/"+this.idsTemas[5];
		HttpGet request6 = new HttpGet(tema6Url);
		HttpResponse response6 = client.execute(request6);
		String resultContentTema6 = getResultContent(response6);
		
		String tema7Url= BASE_URL + "/temas/"+this.idsTemas[6];
		HttpGet request7 = new HttpGet(tema7Url);
		HttpResponse response7 = client.execute(request7);
		String resultContentTema7 = getResultContent(response7);
		
		String tema8Url= BASE_URL + "/temas/"+this.idsTemas[7];
		HttpGet request8 = new HttpGet(tema8Url);
		HttpResponse response8 = client.execute(request8);
		String resultContentTema8 = getResultContent(response8);
		
		String tema9Url= BASE_URL + "/temas/"+this.idsTemas[8];
		HttpGet request9 = new HttpGet(tema9Url);
		HttpResponse response9 = client.execute(request9);
		String resultContentTema9 = getResultContent(response9);
		
		String tema10Url= BASE_URL + "/temas/"+this.idsTemas[9];
		HttpGet request10 = new HttpGet(tema10Url);
		HttpResponse response10 = client.execute(request10);
		String resultContentTema10 = getResultContent(response10);
		
		
		String empresa1URL = BASE_URL + "/empresas/"+this.idsEmpresas[0];
		HttpGet request11 = new HttpGet(empresa1URL);
		HttpResponse response11 = client.execute(request11);
		String resultContentEmpresa1 = getResultContent(response11);
		
		String empresa2URL = BASE_URL + "/empresas/"+this.idsEmpresas[1];
		HttpGet request12 = new HttpGet(empresa2URL);
		HttpResponse response12 = client.execute(request12);
		String resultContentEmpresa2 = getResultContent(response12);
		
		String empresa3URL = BASE_URL + "/empresas/"+this.idsEmpresas[2];
		HttpGet request13 = new HttpGet(empresa3URL);
		HttpResponse response13 = client.execute(request13);
		String resultContentEmpresa3 = getResultContent(response13);
		
		String empresa4URL = BASE_URL + "/empresas/"+this.idsEmpresas[3];
		HttpGet request14 = new HttpGet(empresa4URL);
		HttpResponse response14 = client.execute(request14);
		String resultContentEmpresa4 = getResultContent(response14);
		
		String empresa5URL = BASE_URL + "/empresas/"+this.idsEmpresas[4];
		HttpGet request15 = new HttpGet(empresa5URL);
		HttpResponse response15 = client.execute(request15);
		String resultContentEmpresa5 = getResultContent(response15);
		
		//usuario 1
		ObjectNode jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Lorena");
		jsonObject.put("apellido", "Igartua");		
		ArrayNode conocimientos = jsonObject.putArray("conocimientos");
		conocimientos.addPOJO(resultContentTema1);
		conocimientos.addPOJO(resultContentTema9);
		jsonObject.putPOJO("conocimientos", conocimientos); 
		jsonObject.putPOJO("empresa", resultContentEmpresa2); 
		String jsonString = jsonObject.toString();

		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		JsonNode jsonContent = this.mapper.readTree(resultContent);
		this.idsUsuarios[0] = jsonContent.get("id").asInt();

		//usuario 2
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Martin");
		jsonObject.put("apellido", "Mujica");
		conocimientos = jsonObject.putArray("conocimientos");
		conocimientos.addPOJO(resultContentTema3);
		conocimientos.addPOJO(resultContentTema4);
		jsonObject.putPOJO("conocimientos", conocimientos); 
		jsonObject.putPOJO("empresa", resultContentEmpresa5); 
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsUsuarios[1] = jsonContent.get("id").asInt();
		
		//usuario 3
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Roque");
		jsonObject.put("apellido", "Risiga");
		conocimientos = jsonObject.putArray("conocimientos");
		conocimientos.addPOJO(resultContentTema10);
		conocimientos.addPOJO(resultContentTema2);
		jsonObject.putPOJO("conocimientos", conocimientos); 
		jsonObject.putPOJO("empresa", resultContentEmpresa1); 
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsUsuarios[2] = jsonContent.get("id").asInt();
		
		//usuario 4
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Carito");
		jsonObject.put("apellido", "Igartua");
		conocimientos = jsonObject.putArray("conocimientos");
		conocimientos.addPOJO(resultContentTema5);
		conocimientos.addPOJO(resultContentTema6);
		jsonObject.putPOJO("conocimientos", conocimientos); 
		jsonObject.putPOJO("empresa", resultContentEmpresa3); 
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsUsuarios[3] = jsonContent.get("id").asInt();
		
		//usuario 5
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Adela");
		jsonObject.put("apellido", "Perez");
		conocimientos = jsonObject.putArray("conocimientos");
		conocimientos.addPOJO(resultContentTema7);
		conocimientos.addPOJO(resultContentTema1);
		conocimientos.addPOJO(resultContentTema8);
		jsonObject.putPOJO("conocimientos", conocimientos); 
		jsonObject.putPOJO("empresa", resultContentEmpresa5); 
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsUsuarios[4] = jsonContent.get("id").asInt();
		
		//usuario 6
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Ramon");
		jsonObject.put("apellido", "Gonzalez");
		conocimientos = jsonObject.putArray("conocimientos");
		conocimientos.addPOJO(resultContentTema4);
		conocimientos.addPOJO(resultContentTema1);
		conocimientos.addPOJO(resultContentTema8);
		jsonObject.putPOJO("conocimientos", conocimientos); 
		jsonObject.putPOJO("empresa", resultContentEmpresa1); 
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsUsuarios[5] = jsonContent.get("id").asInt();
		
		//usuario 7
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Marcelo");
		jsonObject.put("apellido", "Rodriguez");
		conocimientos = jsonObject.putArray("conocimientos");
		conocimientos.addPOJO(resultContentTema4);
		conocimientos.addPOJO(resultContentTema6);
		conocimientos.addPOJO(resultContentTema10);
		jsonObject.putPOJO("conocimientos", conocimientos); 
		jsonObject.putPOJO("empresa", resultContentEmpresa3); 
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsUsuarios[6] = jsonContent.get("id").asInt();
		
		//usuario 8
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Ramiro");
		jsonObject.put("apellido", "Estevez");
		conocimientos = jsonObject.putArray("conocimientos");
		conocimientos.addPOJO(resultContentTema2);
		conocimientos.addPOJO(resultContentTema4);
		conocimientos.addPOJO(resultContentTema10);
		jsonObject.putPOJO("conocimientos", conocimientos); 
		jsonObject.putPOJO("empresa", resultContentEmpresa4); 
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsUsuarios[7] = jsonContent.get("id").asInt();
		
		//usuario 9
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Gaspar");
		jsonObject.put("apellido", "Torres");
		conocimientos = jsonObject.putArray("conocimientos");
		conocimientos.addPOJO(resultContentTema7);
		conocimientos.addPOJO(resultContentTema10);
		conocimientos.addPOJO(resultContentTema4);
		jsonObject.putPOJO("conocimientos", conocimientos); 
		jsonObject.putPOJO("empresa", resultContentEmpresa1); 
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsUsuarios[8] = jsonContent.get("id").asInt();
		
		//usuario 10
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Macarena");
		jsonObject.put("apellido", "Ferreiro");
		conocimientos = jsonObject.putArray("conocimientos");
		conocimientos.addPOJO(resultContentTema5);
		conocimientos.addPOJO(resultContentTema6);
		conocimientos.addPOJO(resultContentTema7);
		jsonObject.putPOJO("conocimientos", conocimientos); 
		jsonObject.putPOJO("empresa", resultContentEmpresa5); 
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);	
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsUsuarios[9] = jsonContent.get("id").asInt();
		
		// luego de crear 10 usuarios, controlo que todas se persistieron
		HttpGet requestGet = new HttpGet(url);
		response = client.execute(requestGet);
		String resultContentUsuarios = getResultContent(response);
		System.out.println("ACA");
	System.out.println(resultContentUsuarios);
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		
		jsonContent = this.mapper.readTree(resultContentUsuarios);
		
		Assert.assertEquals(jsonContent.findValues("apellido").size(), CANTIDAD);
	}
	

	public void testCrearTrabajos() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/trabajos";

		String tema1URL= BASE_URL + "/temas/"+this.idsTemas[0];
		HttpGet request1 = new HttpGet(tema1URL);
		HttpResponse response1 = client.execute(request1);
		String resultContentTema1 = getResultContent(response1);
		
		String tema2Url= BASE_URL + "/temas/"+this.idsTemas[1];
		HttpGet request2 = new HttpGet(tema2Url);
		HttpResponse response2 = client.execute(request2);
		String resultContentTema2 = getResultContent(response2);
		
		String tema3Url= BASE_URL + "/temas/"+this.idsTemas[2];
		HttpGet request3 = new HttpGet(tema3Url);
		HttpResponse response3 = client.execute(request3);
		String resultContentTema3 = getResultContent(response3);
		
		String tema4Url= BASE_URL + "/temas/"+this.idsTemas[3];
		HttpGet request4 = new HttpGet(tema4Url);
		HttpResponse response4 = client.execute(request4);
		String resultContentTema4 = getResultContent(response4);
		
		String tema5Url= BASE_URL + "/temas/"+this.idsTemas[4];
		HttpGet request5 = new HttpGet(tema5Url);
		HttpResponse response5 = client.execute(request5);
		String resultContentTema5 = getResultContent(response5);
		
		String tema6Url= BASE_URL + "/temas/"+this.idsTemas[5];
		HttpGet request6 = new HttpGet(tema6Url);
		HttpResponse response6 = client.execute(request6);
		String resultContentTema6 = getResultContent(response6);
		
		String tema7Url= BASE_URL + "/temas/"+this.idsTemas[6];
		HttpGet request7 = new HttpGet(tema7Url);
		HttpResponse response7 = client.execute(request7);
		String resultContentTema7 = getResultContent(response7);
		
		String tema8Url= BASE_URL + "/temas/"+this.idsTemas[7];
		HttpGet request8 = new HttpGet(tema8Url);
		HttpResponse response8 = client.execute(request8);
		String resultContentTema8 = getResultContent(response8);
		
		String tema9Url= BASE_URL + "/temas/"+this.idsTemas[8];
		HttpGet request9 = new HttpGet(tema9Url);
		HttpResponse response9 = client.execute(request9);
		String resultContentTema9 = getResultContent(response9);
		
		String tema10Url= BASE_URL + "/temas/"+this.idsTemas[9];
		HttpGet request10 = new HttpGet(tema10Url);
		HttpResponse response10 = client.execute(request10);
		String resultContentTema10 = getResultContent(response10);
		
		String usuario1Url= BASE_URL + "/usuarios/"+this.idsUsuarios[0];
		HttpGet request11 = new HttpGet(usuario1Url);
		HttpResponse response11 = client.execute(request11);
		String resultContentUsuario1 = getResultContent(response11);
		
		String usuario2Url= BASE_URL + "/usuarios/"+this.idsUsuarios[1];
		HttpGet request12 = new HttpGet(usuario2Url);
		HttpResponse response12 = client.execute(request12);
		String resultContentUsuario2 = getResultContent(response12);
		
		String usuario3Url= BASE_URL + "/usuarios/"+this.idsUsuarios[2];
		HttpGet request13 = new HttpGet(usuario3Url);
		HttpResponse response13 = client.execute(request13);
		String resultContentUsuario3 = getResultContent(response13);
		
		String usuario4Url= BASE_URL + "/usuarios/"+this.idsUsuarios[3];
		HttpGet request14 = new HttpGet(usuario4Url);
		HttpResponse response14 = client.execute(request14);
		String resultContentUsuario4 = getResultContent(response14);
		
		String usuario5Url= BASE_URL + "/usuarios/"+this.idsUsuarios[4];
		HttpGet request15 = new HttpGet(usuario5Url);
		HttpResponse response15 = client.execute(request15);
		String resultContentUsuario5 = getResultContent(response15);
		
		String usuario6Url= BASE_URL + "/usuarios/"+this.idsUsuarios[5];
		HttpGet request16 = new HttpGet(usuario6Url);
		HttpResponse response16 = client.execute(request16);
		String resultContentUsuario6 = getResultContent(response16);
		
		String usuario7Url= BASE_URL + "/usuarios/"+this.idsUsuarios[6];
		HttpGet request17 = new HttpGet(usuario7Url);
		HttpResponse response17 = client.execute(request17);
		String resultContentUsuario7 = getResultContent(response17);
		
		String usuario8Url= BASE_URL + "/usuarios/"+this.idsUsuarios[7];
		HttpGet request18 = new HttpGet(usuario8Url);
		HttpResponse response18 = client.execute(request18);
		String resultContentUsuario8 = getResultContent(response18);
		
		String usuario9Url= BASE_URL + "/usuarios/"+this.idsUsuarios[8];
		HttpGet request19 = new HttpGet(usuario9Url);
		HttpResponse response19 = client.execute(request19);
		String resultContentUsuario9 = getResultContent(response19);
		
		String usuario10Url= BASE_URL + "/usuarios/"+this.idsUsuarios[9];
		HttpGet request20 = new HttpGet(usuario10Url);
		HttpResponse response20 = client.execute(request20);
		String resultContentUsuario10 = getResultContent(response20);
		
		//trabajo 1
		ObjectNode jsonObject = this.mapper.createObjectNode();
		jsonObject.put("titulo", "base de datos");
		ArrayNode palabrasClave = jsonObject.putArray("palabrasClave");
		palabrasClave.addPOJO(resultContentTema1);
		palabrasClave.addPOJO(resultContentTema2);
		jsonObject.putPOJO("palabrasClave", palabrasClave); 
		ArrayNode autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContentUsuario10);
		jsonObject.putPOJO("autores", autores); 
		jsonObject.put("esPoster", "true");
		String jsonString = jsonObject.toString();

		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		JsonNode jsonContent = this.mapper.readTree(resultContent);
		this.idsTrabajos[0] = jsonContent.get("id").asInt();

		//trabajo 2
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("titulo", "sistema operativo");
		palabrasClave = jsonObject.putArray("palabrasClave");
		palabrasClave.addPOJO(resultContentTema10);
		palabrasClave.addPOJO(resultContentTema2);
		palabrasClave.addPOJO(resultContentTema4);
		jsonObject.putPOJO("palabrasClave", palabrasClave); 
		autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContentUsuario9);
		autores.addPOJO(resultContentUsuario2);
		jsonObject.putPOJO("autores", autores); 
		jsonObject.put("esPoster", "true");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTrabajos[1] = jsonContent.get("id").asInt();

		//trabajo 3
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("titulo", "programas para web");
		palabrasClave = jsonObject.putArray("palabrasClave");
		palabrasClave.addPOJO(resultContentTema3);
		palabrasClave.addPOJO(resultContentTema5);
		palabrasClave.addPOJO(resultContentTema4);
		jsonObject.putPOJO("palabrasClave", palabrasClave); 
		autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContentUsuario3);
		autores.addPOJO(resultContentUsuario1);
		jsonObject.putPOJO("autores", autores); 
		jsonObject.put("esPoster", "true");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTrabajos[2] = jsonContent.get("id").asInt();

		//trabajo 4
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("titulo", "inteligencia artificial");
		palabrasClave = jsonObject.putArray("palabrasClave");
		palabrasClave.addPOJO(resultContentTema7);
		palabrasClave.addPOJO(resultContentTema5);
		palabrasClave.addPOJO(resultContentTema9);
		jsonObject.putPOJO("palabrasClave", palabrasClave); 
		autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContentUsuario10);
		autores.addPOJO(resultContentUsuario4);
		jsonObject.putPOJO("autores", autores); 
		jsonObject.put("esPoster", "true");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTrabajos[3] = jsonContent.get("id").asInt();

		//trabajo 5
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("titulo", "documentacion y validacion");
		palabrasClave = jsonObject.putArray("palabrasClave");
		palabrasClave.addPOJO(resultContentTema7);
		palabrasClave.addPOJO(resultContentTema4);
		jsonObject.putPOJO("palabrasClave", palabrasClave); 
		autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContentUsuario2);
		jsonObject.putPOJO("autores", autores); 
		jsonObject.put("esPoster", "true");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTrabajos[4] = jsonContent.get("id").asInt();

		//trabajo 6
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("titulo", "java y html");
		palabrasClave = jsonObject.putArray("palabrasClave");
		palabrasClave.addPOJO(resultContentTema6);
		palabrasClave.addPOJO(resultContentTema7);
		palabrasClave.addPOJO(resultContentTema8);
		jsonObject.putPOJO("palabrasClave", palabrasClave); 
		autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContentUsuario4);
		autores.addPOJO(resultContentUsuario7);
		jsonObject.putPOJO("autores", autores); 
		jsonObject.put("esPoster", "true");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTrabajos[5] = jsonContent.get("id").asInt();

		//trabajo 7
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("titulo", "ultracasa");
		palabrasClave = jsonObject.putArray("palabrasClave");
		palabrasClave.addPOJO(resultContentTema1);
		palabrasClave.addPOJO(resultContentTema10);
		palabrasClave.addPOJO(resultContentTema8);
		jsonObject.putPOJO("palabrasClave", palabrasClave); 
		autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContentUsuario5);
		autores.addPOJO(resultContentUsuario7);
		jsonObject.putPOJO("autores", autores); 
		jsonObject.put("esPoster", "true");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTrabajos[6] = jsonContent.get("id").asInt();

		//trabajo 8
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("titulo", "mongosql vs sql");
		palabrasClave = jsonObject.putArray("palabrasClave");
		palabrasClave.addPOJO(resultContentTema2);
		palabrasClave.addPOJO(resultContentTema5);
		palabrasClave.addPOJO(resultContentTema8);
		jsonObject.putPOJO("palabrasClave", palabrasClave); 
		autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContentUsuario5);
		autores.addPOJO(resultContentUsuario8);
		autores.addPOJO(resultContentUsuario7);
		jsonObject.putPOJO("autores", autores); 
		jsonObject.put("esPoster", "false");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTrabajos[7] = jsonContent.get("id").asInt();

		//trabajo 9
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("titulo", "tecnologia y salud");
		palabrasClave = jsonObject.putArray("palabrasClave");
		palabrasClave.addPOJO(resultContentTema1);
		palabrasClave.addPOJO(resultContentTema7);
		palabrasClave.addPOJO(resultContentTema3);
		jsonObject.putPOJO("palabrasClave", palabrasClave); 
		autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContentUsuario8);
		autores.addPOJO(resultContentUsuario1);
		autores.addPOJO(resultContentUsuario4);
		jsonObject.putPOJO("autores", autores); 
		jsonObject.put("esPoster", "false");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTrabajos[8] = jsonContent.get("id").asInt();

		//trabajo 10
		jsonObject = this.mapper.createObjectNode();
		jsonObject.put("titulo", "orientando a educadores");
		palabrasClave = jsonObject.putArray("palabrasClave");
		palabrasClave.addPOJO(resultContentTema3);
		palabrasClave.addPOJO(resultContentTema5);
		palabrasClave.addPOJO(resultContentTema6);
		jsonObject.putPOJO("palabrasClave", palabrasClave); 
		autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContentUsuario6);
		autores.addPOJO(resultContentUsuario2);
		autores.addPOJO(resultContentUsuario4);
		jsonObject.putPOJO("autores", autores); 
		jsonObject.put("esPoster", "false");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonContent = this.mapper.readTree(resultContent);
		this.idsTrabajos[9] = jsonContent.get("id").asInt();
		
		// luego de crear 10 trabajos, controlo que todas se persistieron
		HttpGet requestGet = new HttpGet(url);
		response = client.execute(requestGet);
		String resultContentEmpresa = getResultContent(response);
	
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		
		jsonContent = this.mapper.readTree(resultContentEmpresa);
		Assert.assertEquals(jsonContent.findValues("esPoster").size(), CANTIDAD);
		
	}
	
	

	public void testUpdateUsuario() throws ClientProtocolException, IOException {
		
		String tema5Url= BASE_URL + "/temas/"+this.idsTemas[5];
		HttpGet request5 = new HttpGet(tema5Url);
		HttpResponse response5 = client.execute(request5);
		String resultContentTema5 = getResultContent(response5);
		
		String empresa1URL = BASE_URL + "/empresas/"+this.idsEmpresas[0];
		HttpGet request11 = new HttpGet(empresa1URL);
		HttpResponse response11 = client.execute(request11);
		String resultContentEmpresa1 = getResultContent(response11);
		
		ObjectNode jsonObject = this.mapper.createObjectNode();
		jsonObject.put("nombre", "Ramoncito");
		jsonObject.put("apellido", "Ochoa");
		ArrayNode conocimientos = jsonObject.putArray("conocimientos");
		conocimientos.addPOJO(resultContentTema5);
		jsonObject.putPOJO("conocimientos", conocimientos); 
		jsonObject.putPOJO("empresa", resultContentEmpresa1); 
		String jsonString = jsonObject.toString();

		String url = BASE_URL + "/usuarios/"+this.idsUsuarios[6];
		HttpPut request = new HttpPut(url);
		request.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(request);
		
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		
		System.out.println("\nPUT "+url);
		
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);
	}
	
	
	public void testAsignarTrabajoAEvaluadores() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/evaluaciones/asignarEvaluador/"+this.idsTrabajos[0];

		HttpPost post = new HttpPost(url);
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		// luego de crear una evaluacion, controlo que se persistio
		url = BASE_URL + "/evaluaciones";
		HttpGet requestGet = new HttpGet(url);
		response = client.execute(requestGet);
	
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		
		String resultContentEvaluacion = getResultContent(response);
		JsonNode jsonContent = this.mapper.readTree(resultContentEvaluacion);
		Assert.assertEquals(jsonContent.size(), 1);
	}
	
	public void testGetTrabajosDeUnAutor() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/usuarios/"+this.idsUsuarios[9]+"/trabajos";
		
		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		
		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);
	
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		
	}
	
	public void testGetTrabajosDeUnRevisorEnRangoFechas() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/evaluaciones/trabajosRevisadosPorEvalEnRango/"+this.idsUsuarios[0]+"?fromD=10&fromM=11&fromY=2018&toD=30&toM=11&toY=2018";
		
		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		
		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);
	
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		
	}

	
	private String getResultContent(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		if(entity!=null) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} else {
			return "";
		}
	}
	
}
