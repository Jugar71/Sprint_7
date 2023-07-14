package logic;

import io.restassured.response.Response;
import io.qameta.allure.Step;
import serialization.CourierCreateSerialization;
import serialization.LoginSerialization;

import static io.restassured.RestAssured.given;

public class CourierLogic {
    static final String COURIER = "/api/v1/courier";
    static final String LOGIN = "/api/v1/courier/login";

    @Step("Отправляет запрос POST о создании курьера на /api/v1/courier")
    public Response createCourier (CourierCreateSerialization courier) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post(COURIER);
        System.out.println(response.body().asString());
        return response;
    }

    @Step("Отправляет запрос POST на авторизацию на /api/v1/courier/login")
    public Response login (LoginSerialization login) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post(LOGIN);
        System.out.println(response.body().asString());
        return response;
    }

    @Step("Отправляет запрос DELETE на удаление курьера на /api/v1/courier")
    public void deleteCourier (LoginSerialization login) {
        String[] split1 = login(login).body().asString().split(":");
        String[] split2 = split1[1].split("}");
        String id = split2[0];

        String json2 = "{\"id\": " + id + "}";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json2)
                        .when()
                        .delete(COURIER + "/" + id);
        System.out.println(response.body().asString() + " !!!УДАЛЕНО!!!");
    }
}
