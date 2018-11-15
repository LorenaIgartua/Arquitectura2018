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
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EmpresaRESTTest {

	public final String BASE_URL="http://localhost:8080/CONGRESO/api";

	public final HttpClient client = HttpClientBuilder.create().build();

	@Test
	public void testRESTInterface() throws ClientProtocolException, IOException {
		crearEmpresas();
		getEmpresa();
		System.out.println("aca");
		listarEmpresas();
		updateEmpresa();
		deleteEmpresa();
	}
	

	public void crearEmpresas() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/empresas";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
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

		jsonObject = mapper.createObjectNode();
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
		

		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
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
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Yell");
		jsonObject.put("direccion", "colombia 702 - Tandil");
		jsonObject.put("telefono", "4437737");
		jsonObject.put("cuit", "3376512");
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

	
	public void getEmpresa() throws ClientProtocolException, IOException {
		String url = BASE_URL + "/empresas/4";

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		
		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}

	
	public void listarEmpresas() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/empresas";

		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);
		
		System.out.println("\nGET "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}
	
	public void updateEmpresa() throws ClientProtocolException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "BeeReal SRL y CIA");
		jsonObject.put("direccion", "Chacabuco 433 - Tandil");
		jsonObject.put("telefono", "345132");
		jsonObject.put("cuit", "68764723");
		String jsonString = jsonObject.toString();

		String url = BASE_URL + "/empresas/5";
		HttpPut request = new HttpPut(url);
		request.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
		HttpResponse response = client.execute(request);

		System.out.println("\nPUT "+url);
		
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}
	
	public void deleteEmpresa() throws ClientProtocolException, IOException {

		String url = BASE_URL + "/empresas/6";
		
		HttpDelete request = new HttpDelete(url);
		
		HttpResponse response = client.execute(request);
		
		System.out.println("\nDELETE "+url);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		String resultContent = getResultContent(response);

		System.out.println("Response Content : " + resultContent);

	}
	
}
