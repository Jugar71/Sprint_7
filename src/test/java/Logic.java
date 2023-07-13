import io.restassured.response.Response;
import java.io.File;

import static io.restassured.RestAssured.given;

public class Logic {
    public Response createCourier (File json) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        System.out.println(response.body().asString());
        return response;
    }

    public Response login (File json) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier/login");
        System.out.println(response.body().asString());
        return response;
    }

    public void deleteCourier (File json1) {
        String[] split1 = login(json1).body().asString().split(":");
        String[] split2 = split1[1].split("}");
        String id = split2[0];

        String json2 = "{\"id\": " + id + "}";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json2)
                        .when()
                        .delete("/api/v1/courier/" + id);
        System.out.println(response.body().asString());
    }

    public Response getOrders () {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .when()
                        .get("/api/v1/orders");
        System.out.println(response.body().asString());
        return response;
    }

    public Response createOrder (String json) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/orders");
        System.out.println(response.body().asString());
        return response;
    }
}
