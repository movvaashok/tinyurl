package com.neueda.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.neueda.dao.UrlDAO;
import com.neueda.tinyurl.TinyurlApplication;
import com.neueda.utility.TinyUrlGenerator;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SpringBootTest
@ContextConfiguration(classes = { TinyUrlGenerator.class, UrlDAO.class, UrlController.class })
@TestInstance(Lifecycle.PER_CLASS)
class UrlControllerTest {
	@Autowired
	public UrlController urlController;

	@BeforeAll
	public void main() {
		TinyurlApplication.main(new String[] {});
	}

	@SuppressWarnings("unchecked")
	@Test
	void testGetTinyUrl() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("originalUrl", "http://www.google.com");
		RestAssured.baseURI = "http://localhost:8082/tinyurl";
		RequestSpecification request = RestAssured.given().contentType(ContentType.JSON);
		request.body(requestParams.toJSONString());
		Response response = request.post("/createTinyUrl");
		System.out.println(response.asString());
		String tinyUrl = response.jsonPath().get("tinyUrl");
		assertTrue(response.getStatusCode() == 200);
		Assert.assertEquals(tinyUrl, "www.neueda.com/253d14");
	}

	@SuppressWarnings("unchecked")
	@Test
	void testGetTinyUrlFailCase() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("url", "http://www.google.com");
		RestAssured.baseURI = "http://localhost:8082/tinyurl";
		RequestSpecification request = RestAssured.given().contentType(ContentType.JSON);
		request.body(requestParams.toJSONString());
		Response response = request.post("/createTinyUrl");
		System.out.println(response.asString());
		String reason = response.jsonPath().get("reason");
		assertTrue(response.getStatusCode() == 400);
		Assert.assertEquals(reason, "Original url not found");
	}

	@Test
	void testGetLargeUrl() {
		given().when().get("http://localhost:8082/tinyurl/getOriginalUrl?tiny=www.neueda.com/253d14").then()
				.assertThat().body("OriginalURL", equalTo("http://www.google.com"));
	}

	@Test
	void testGetLargeUrlFailCase() {
		given().when().get("http://localhost:8082/tinyurl/getOriginalUrl?tiny=www.neueda.com/1234").then().assertThat()
				.statusCode(400);
	}

	@Test
	void testSaveCreatedMappings() {
		given().when().get("http://localhost:8082/tinyurl/save").then().assertThat().statusCode(200);
	}
}
