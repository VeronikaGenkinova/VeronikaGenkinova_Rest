package app.config;

import io.restassured.response.Response;
import io.restassured.RestAssured;

public class UsersHttpRequest extends BaseHttpRequest {
    private static final String userUrl = "api/users";

    public Response getUsers(String id) {
        return getRequestSpecification()
                .when()
                .get(userUrl + "/" + id);
    }
}