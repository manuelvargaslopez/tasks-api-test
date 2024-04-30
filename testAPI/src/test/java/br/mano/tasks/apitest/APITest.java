package br.mano.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setUP() {
		
		RestAssured.baseURI= "http://localhost:8001/tasks-backend";
	}
	
	
	@Test
	public void deveRetornarTarefas() {
	
		RestAssured.given()	
		.when()
			.get("/todo")
		.then()
			.statusCode(200)		
		;		
	}


	@Test
	public void deveAdicionarTarefaComSucesso() {
	
		RestAssured.given()	
		.body("{\"task\": \"pelo body\",\"dueDate\":\"2040-12-10\"}")
		.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(201)			
		;		
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
	
		RestAssured.given()	
			.body("{\"task\": \"pelo body 400\",\"dueDate\":\"2010-12-10\"}")
		.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)	
			.body("message", CoreMatchers.is ("Due date must not be in past"))			
		;	
		
	}
	
	
}

//{"task": "pelo body","dueDate":"10/10/2040"}
