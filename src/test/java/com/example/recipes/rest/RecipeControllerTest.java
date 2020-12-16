package com.example.recipes.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RecipeControllerTest {
    private static final String API_ROOT = "http://localhost:8080/api/recipes";

    @Value("${admin.name}")
    private String adminName;
    @Value("${admin.password}")
    private String adminPassword;

    @Test
    void findAll() {
        Response response = RestAssured.given().auth().basic(adminName, adminPassword).get(API_ROOT);

        List<String> jsonResponse = response.body().jsonPath().getList("$");

        assert(HttpStatus.OK.value() == response.getStatusCode() && jsonResponse.size() > 0);
    }

    @Test
    void findByTitle() {
        Response response = RestAssured.given().auth().basic(adminName, adminPassword).get(API_ROOT+"/title/Minestrone");

        String title = response.body().jsonPath().getString("title[0]");

        assert(HttpStatus.OK.value() == response.getStatusCode() && "Minestrone".equals(title));
    }

    @Test
    void findOne() {
        Response response = RestAssured.given().auth().basic(adminName, adminPassword).get(API_ROOT+"/title/Minestrone");

        String id = response.body().jsonPath().getString("id[0]");

        response = RestAssured.given().auth().basic(adminName, adminPassword).get(API_ROOT+"/"+id);

        String idTest = response.body().jsonPath().getString("id");

        assert(HttpStatus.OK.value() == response.getStatusCode() && id.equals(idTest));
    }

}
