package com.microAPI;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;


import com.microAPI.entidades.Trabajo;

public class MicroApiApplicationTests {
	
	@Test
	public void testGetTrabajosDeUnAutor() throws Exception {

		String url = "http://localhost:8080/usuarios/25/trabajos";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	        RestTemplate restTemplate = new RestTemplate();
	        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
	        ResponseEntity<Trabajo[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Trabajo[].class, 1);
	        assertEquals( responseEntity.getStatusCodeValue(), 200);
		
	}
	
	
//	@Test
//	public void contextLoads() {
//	
//	}

}
