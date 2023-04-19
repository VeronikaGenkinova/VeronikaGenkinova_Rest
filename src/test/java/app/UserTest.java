package app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import app.config.UsersHttpRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class UserTest {
    private static final String BASE_URL = "https://reqres.in/api";
    private static UsersHttpRequest usersHttpRequest = new UsersHttpRequest();

    @Test
    public void testGetUser() {
        usersHttpRequest.getUsers("2")
                .then()
                .statusCode(200)
                .extract()
                .body();
    }

    @Test
    public void testGetSingleUser() {
        Response response = RestAssured.get(BASE_URL + "/users/2");
        Assertions.assertEquals(response.getStatusCode(), 200);

        String firstName = response.jsonPath().get("data.first_name");
        String lastName = response.jsonPath().get("data.last_name");
        String emailAddress = response.jsonPath().get("data.email");

        Assertions.assertEquals(firstName, "Janet");
        Assertions.assertEquals(lastName, "Weaver");
        Assertions.assertEquals(emailAddress, "janet.weaver@reqres.in");
    }

    @Test
    public void testSingleResourceNotFound() {
        Response response = RestAssured.get(BASE_URL + "/unknown/23");
        Assertions.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void testListResources() {
        Response response = RestAssured.get(BASE_URL + "/unknown");
        Assertions.assertEquals(response.getStatusCode(), 200);

        int totalPages = response.jsonPath().get("total_pages");
        int perPage = response.jsonPath().get("per_page");
        int total = response.jsonPath().get("total");
        int actualPage = response.jsonPath().get("page");

        Assertions.assertEquals(totalPages, 2);
        Assertions.assertEquals(perPage, 6);
        Assertions.assertEquals(total, 12);
        Assertions.assertEquals(actualPage, 1);
    }

    @Test
    public void testCreateUser() {
        String requestBody = "{\n" +
                "    \"name\": \"Eugene\",\n" +
                "    \"job\": \"Master\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(BASE_URL + "/users");
        Assertions.assertEquals(response.getStatusCode(), 201);

        String name = response.jsonPath().get("name");
        String job = response.jsonPath().get("job");

        Assertions.assertEquals(name, "Eugene");
        Assertions.assertEquals(job, "Master");
    }
}
