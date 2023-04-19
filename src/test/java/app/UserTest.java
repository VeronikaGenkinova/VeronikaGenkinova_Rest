package app;

import org.junit.jupiter.api.Test;
import app.config.UsersHttpRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserTest {

   private UsersHttpRequest usersHttpRequest = new UsersHttpRequest();

    @Test
    public void testGetSingleUser() {
        Response users = usersHttpRequest.getUsers("2");

    }
}
