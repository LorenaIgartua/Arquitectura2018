package testREST;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import entidades.Empresa;
import entidades.Tema;
import servicios.EmpresaDAO;
import servicios.TemaDAO;

public class UsuarioRESTTest {

	public final String BASE_URL="http://localhost:8080/CONGRESO/api";

	public final HttpClient client = HttpClientBuilder.create().build();

	@Test
	public void testRESTInterface() throws ClientProtocolException, IOException {
		crearUsuarios();
		getUsuario();
		listarUsuarios();
		updateUsuario();
		deleteUsuario();
	}
	

	public void crearUsuarios() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/usuarios";
		
		ObjectMapper mapper = new ObjectMapper();

		String tema1URL= BASE_URL + "/temas/7";
		HttpGet request1 = new HttpGet(tema1URL);
		HttpResponse response1 = client.execute(request1);
		String resultContentTema1 = getResultContent(response1);
		
		String tema2Url= BASE_URL + "/temas/8";
		HttpGet request2 = new HttpGet(tema2Url);
		HttpResponse response2 = client.execute(request2);
		String resultContentTema2 = getResultContent(response2);
		
		String tema3Url= BASE_URL + "/temas/9";
		HttpGet request3 = new HttpGet(tema3Url);
		HttpResponse response3 = client.execute(request3);
		String resultContentTema3 = getResultContent(response3);
		
		String tema4Url= BASE_URL + "/temas/10";
		HttpGet request4 = new HttpGet(tema4Url);
		HttpResponse response4 = client.execute(request4);
		String resultContentTema4 = getResultContent(response4);
		
		String tema5Url= BASE_URL + "/temas/11";
		HttpGet request5 = new HttpGet(tema5Url);
		HttpResponse response5 = client.execute(request5);
		String resultContentTema5 = getResultContent(response5);
		
		String tema6Url= BASE_URL + "/temas/12";
		HttpGet request6 = new HttpGet(tema6Url);
		HttpResponse response6 = client.execute(request6);
		String resultContentTema6 = getResultContent(response6);
		
		String tema7Url= BASE_URL + "/temas/13";
		HttpGet request7 = new HttpGet(tema7Url);
		HttpResponse response7 = client.execute(request7);
		String resultContentTema7 = getResultContent(response7);
		
		String tema8Url= BASE_URL + "/temas/14";
		HttpGet request8 = new HttpGet(tema8Url);
		HttpResponse response8 = client.execute(request8);
		String resultContentTema8 = getResultContent(response8);
		
		String tema9Url= BASE_URL + "/temas/15";
		HttpGet request9 = new HttpGet(tema9Url);
		HttpResponse response9 = client.execute(request9);
		String resultContentTema9 = getResultContent(response9);
		
		String tema10Url= BASE_URL + "/temas/16";
		HttpGet request10 = new HttpGet(tema10Url);
		HttpResponse response10 = client.execute(request10);
		String resultContentTema10 = getResultContent(response10);
		
		String empresa1URL = BASE_URL + "/empresas/1";
		HttpGet request11 = new HttpGet(empresa1URL);
		HttpResponse response11 = client.execute(request11);
		String resultContentEmpresa1 = getResultContent(response11);
		
		String empresa2URL = BASE_URL + "/empresas/2";
		HttpGet request12 = new HttpGet(empresa2URL);
		HttpResponse response12 = client.execute(request12);
		String resultContentEmpresa2 = getResultContent(response12);
		
		String empresa3URL = BASE_URL + "/empresas/3";
		HttpGet request13 = new HttpGet(empresa3URL);
		HttpResponse response13 = client.execute(request13);
		String resultContentEmpresa3 = getResultContent(response13);
		
		String empresa4URL = BASE_URL + "/empresas/4";
		HttpGet request14 = new HttpGet(empresa4URL);
		HttpResponse response14 = client.execute(request14);
		String resultContentEmpresa4 = getResultContent(response14);
		
		String empresa5URL = BASE_URL + "/empresas/5";
		HttpGet request15 = new HttpGet(empresa5URL);
		HttpResponse response15 = client.execute(request15);
		String resultContentEmpresa5 = getResultContent(response15);
		
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Lorena");
		jsonObject.put("apellido", "Igartua");		
		ArrayNode conocimientos = jsonObject.putArray("conocimientos");
		conocimientos.addPOJO(resultContentTema1);
		conocimientos.addPOJO(resultContentTema2);
		jsonObject.putPOJO("conocimientos", conocimientos); 
		jsonObject.putPOJO("empresa", resultContentEmpresa5); 
		String jsonString = jsonObject.toString();

		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		String resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);

		jsonObject = mapper.createObjectNode();
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
		

		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Para BORRAR");
		jsonObject.put("apellido", "chacha");
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

	
	public void getUsuario() throws ClientProtocolException, IOException {
		String url = BASE_URL + "/usuarios/20";

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		
		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	
	public void listarUsuarios() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/usuarios";

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		
		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}
	
	public void updateUsuario() throws ClientProtocolException, IOException {
		
		String tema5Url= BASE_URL + "/temas/21";
		HttpGet request5 = new HttpGet(tema5Url);
		HttpResponse response5 = client.execute(request5);
		String resultContentTema5 = getResultContent(response5);
		
		String empresa1URL = BASE_URL + "/empresas/1";
		HttpGet request11 = new HttpGet(empresa1URL);
		HttpResponse response11 = client.execute(request11);
		String resultContentEmpresa1 = getResultContent(response11);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Ramoncito");
		jsonObject.put("apellido", "Ochoa");
		ArrayNode conocimientos = jsonObject.putArray("conocimientos");
		conocimientos.addPOJO(resultContentTema5);
		jsonObject.putPOJO("conocimientos", conocimientos); 
		jsonObject.putPOJO("empresa", resultContentEmpresa1); 
		String jsonString = jsonObject.toString();

		String url = BASE_URL + "/usuarios/22";
		HttpPut request = new HttpPut(url);
		request.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(request);

		System.out.println("\nPUT "+url);
		
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}
	
	public void deleteUsuario() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/usuarios/28";
		
		HttpDelete request = new HttpDelete(url);
		
		HttpResponse response = client.execute(request);
		
		System.out.println("\nDELETE "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}
	
}
