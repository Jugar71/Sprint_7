package logic;

import io.restassured.response.Response;
import io.qameta.allure.Step;
import serialization.OrderSerialization;

import static io.restassured.RestAssured.given;

public class OrderLogic {
    static final String ORDERS = "/api/v1/orders";

    @Step("Отправляет запрос GET на получение списка заказов на /api/v1/orders")
    public Response getOrders () {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .when()
                        .get(ORDERS);
        System.out.println(response.body().asString());
        return response;
    }

    @Step("Отправляет запрос POST о создании заказа на /api/v1/orders")
    public Response createOrder (OrderSerialization order) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(order)
                        .when()
                        .post(ORDERS);
        System.out.println(response.body().asString());
        return response;
    }
}
