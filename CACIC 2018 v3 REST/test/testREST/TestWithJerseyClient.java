package testREST;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class TestWithJerseyClient {

	 
	public final String BASE_URL="http://localhost:8080/CONGRESO/api";

	public Client client = Client.create();


	@Test
	public void testCrearPerros() {

		String url = BASE_URL + "/perros";

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Roque");
		jsonObject.put("raza", "Callejero");
		jsonObject.put("edad", 7);
		String jsonString = jsonObject.toString();
		
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type("application/json")
				   .post(ClientResponse.class, jsonString);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 201);
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Popi");
		jsonObject.put("raza", "Siberiano");
		jsonObject.put("edad", 4);
		jsonString = jsonObject.toString();

		webResource = client.resource(url);
		response = webResource.type("application/json")
				   .post(ClientResponse.class, jsonString);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 201);
		
		jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Manchita");
		jsonObject.put("raza", "Dalmate");
		jsonObject.put("edad", 5);
		jsonString = jsonObject.toString();

		webResource = client.resource(url);
		response = webResource.type("application/json")
				   .post(ClientResponse.class, jsonString);

		System.out.println("\nPOST "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 201);
	}

	@Test(dependsOnMethods= {"testCrearPerros"})
	public void testGetPerro() {

		String url = BASE_URL + "/perros/1";
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

		System.out.println("\nGET "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 200);
		
	}

	@Test(dependsOnMethods= {"testCrearPerros"})
	public void testListarPerros() {
		
		String url = BASE_URL + "/perros";
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

		System.out.println("\nGET "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 200);

	}
	
	@Test(dependsOnMethods= {"testCrearPerros"})
	public void testUpdatePerro() {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode jsonObject = mapper.createObjectNode();
		jsonObject.put("nombre", "Roque");
		jsonObject.put("raza", "Callejero");
		jsonObject.put("edad", 8);
		String jsonString = jsonObject.toString();

		String url = BASE_URL + "/perros/1";
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type("application/json").put(ClientResponse.class,jsonString);

		System.out.println("\nPUT "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 200);
		
	}
	
	@Test(dependsOnMethods= {"testCrearPerros"})
	public void testDeletePerro() {
		
		String url = BASE_URL + "/perros/2";
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.delete(ClientResponse.class);

		System.out.println("\nDELETE "+url);
		System.out.println("Response Code : " + response.getStatus());
		if(response.hasEntity())
			System.out.println("Response Content : " + response.getEntity(String.class));
		else
			System.out.println("Response Content : NO CONTENT");
		Assert.assertTrue(response.getStatus() == 204 || response.getStatus() == 404);
		
	}
	
	@Test(dependsOnMethods= {"testCrearPerros"})
	private void testFindPerrosByEdad() {
		
		String url = BASE_URL + "/perros/findPerrosByEdad?from=3&to=5";
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

		System.out.println("\nGET "+url);
		System.out.println("Response Code : " + response.getStatus());
		System.out.println("Response Content : " + response.getEntity(String.class));
		Assert.assertEquals(response.getStatus(), 200);

	}	
	
}
