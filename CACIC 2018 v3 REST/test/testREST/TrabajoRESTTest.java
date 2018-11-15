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

public class TrabajoRESTTest {

	public final String BASE_URL="http://localhost:8080/CONGRESO/api";

	public final HttpClient client = HttpClientBuilder.create().build();

	@Test
	public void testRESTInterface() throws ClientProtocolException, IOException {
		crearTrabajos();
		getTrabajo();
		listarTrabajos();
		updateTrabajo();
		deleteTrabajo();
	}
	

	public void crearTrabajos() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/trabajos";
		
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
		
		String tema9Url= BASE_URL + "/temas/17";
		HttpGet request9 = new HttpGet(tema9Url);
		HttpResponse response9 = client.execute(request9);
		String resultContentTema9 = getResultContent(response9);
		
		String tema10Url= BASE_URL + "/temas/16";
		HttpGet request10 = new HttpGet(tema10Url);
		HttpResponse response10 = client.execute(request10);
		String resultContentTema10 = getResultContent(response10);
		
		String usuario1Url= BASE_URL + "/usuarios/18";
		HttpGet request11 = new HttpGet(usuario1Url);
		HttpResponse response11 = client.execute(request11);
		String resultContentUsuario1 = getResultContent(response11);
		
		String usuario2Url= BASE_URL + "/usuarios/19";
		HttpGet request12 = new HttpGet(usuario2Url);
		HttpResponse response12 = client.execute(request12);
		String resultContentUsuario2 = getResultContent(response12);
		
		String usuario3Url= BASE_URL + "/usuarios/20";
		HttpGet request13 = new HttpGet(usuario3Url);
		HttpResponse response13 = client.execute(request13);
		String resultContentUsuario3 = getResultContent(response13);
		
		String usuario4Url= BASE_URL + "/usuarios/21";
		HttpGet request14 = new HttpGet(usuario4Url);
		HttpResponse response14 = client.execute(request14);
		String resultContentUsuario4 = getResultContent(response14);
		
		String usuario5Url= BASE_URL + "/usuarios/22";
		HttpGet request15 = new HttpGet(usuario5Url);
		HttpResponse response15 = client.execute(request15);
		String resultContentUsuario5 = getResultContent(response15);
		
		String usuario6Url= BASE_URL + "/usuarios/23";
		HttpGet request16 = new HttpGet(usuario6Url);
		HttpResponse response16 = client.execute(request16);
		String resultContentUsuario6 = getResultContent(response16);
		
		String usuario7Url= BASE_URL + "/usuarios/23";
		HttpGet request17 = new HttpGet(usuario7Url);
		HttpResponse response17 = client.execute(request17);
		String resultContentUsuario7 = getResultContent(response17);
		
		String usuario8Url= BASE_URL + "/usuarios/22";
		HttpGet request18 = new HttpGet(usuario8Url);
		HttpResponse response18 = client.execute(request18);
		String resultContentUsuario8 = getResultContent(response18);
		
		String usuario9Url= BASE_URL + "/usuarios/22";
		HttpGet request19 = new HttpGet(usuario9Url);
		HttpResponse response19 = client.execute(request19);
		String resultContentUsuario9 = getResultContent(response19);
		
		String usuario10Url= BASE_URL + "/usuarios/22";
		HttpGet request20 = new HttpGet(usuario10Url);
		HttpResponse response20 = client.execute(request20);
		String resultContentUsuario10 = getResultContent(response20);
		
		ObjectNode jsonObject = mapper.createObjectNode();
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

		jsonObject = mapper.createObjectNode();
		jsonObject.put("titulo", "sistema operativo");
		palabrasClave = jsonObject.putArray("palabrasClave");
		palabrasClave.addPOJO(resultContentTema10);
		palabrasClave.addPOJO(resultContentTema2);
		palabrasClave.addPOJO(resultContentTema4);
		jsonObject.putPOJO("palabrasClave", palabrasClave); 
		autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContentUsuario10);
		autores.addPOJO(resultContentUsuario2);
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
		

		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("titulo", "documentacion y validacion");
		palabrasClave = jsonObject.putArray("palabrasClave");
		palabrasClave.addPOJO(resultContentTema7);
		palabrasClave.addPOJO(resultContentTema4);
		jsonObject.putPOJO("palabrasClave", palabrasClave); 
		autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContentUsuario2);
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
		
		jsonObject = mapper.createObjectNode();
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
		jsonObject.put("esPoster", "false");
		jsonString = jsonObject.toString();

		post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		response = client.execute(post);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		resultContent = getResultContent(response);
		System.out.println("Response Content : " + resultContent);
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("titulo", "para BORRAR");
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

	
	public void getTrabajo() throws ClientProtocolException, IOException {
		String url = BASE_URL + "/trabajos/33";

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		
		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	
	public void listarTrabajos() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/trabajos";

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		
		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}
	
	public void updateTrabajo() throws ClientProtocolException, IOException {
		
		String tema3Url= BASE_URL + "/temas/9";
		HttpGet request3 = new HttpGet(tema3Url);
		HttpResponse response3 = client.execute(request3);
		String resultContentTema3 = getResultContent(response3);
		
		String usuario2Url= BASE_URL + "/usuarios/19";
		HttpGet request12 = new HttpGet(usuario2Url);
		HttpResponse response12 = client.execute(request12);
		String resultContentUsuario2 = getResultContent(response12);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("titulo", "inteligencia artificial volumen 2");
		ArrayNode palabrasClave = jsonObject.putArray("palabrasClave");
		palabrasClave.addPOJO(resultContentTema3);
		jsonObject.putPOJO("palabrasClave", palabrasClave); 
		ArrayNode autores = jsonObject.putArray("autores");
		autores.addPOJO(resultContentUsuario2);
		jsonObject.putPOJO("autores", autores); 
		jsonObject.put("esPoster", "true");
		String jsonString = jsonObject.toString();

		String url = BASE_URL + "/trabajos/34";
		HttpPut request = new HttpPut(url);
		request.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(request);

		System.out.println("\nPUT "+url);
		
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}
	
	public void deleteTrabajo() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/trabajos/39";
		
		HttpDelete request = new HttpDelete(url);
		
		HttpResponse response = client.execute(request);
		
		System.out.println("\nDELETE "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}
	
}
