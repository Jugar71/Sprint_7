package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import logic.OrderLogic;
import serialization.OrderSerialization;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseTest {
    private final List<String>  color;

    public OrderCreateTest(List<String>  color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] testCases() {
        return new Object[][] {
                { List.of("BLACK") },
                { List.of("GREY") },
                { List.of("BLACK", "GREY") },
                { List.of() },
        };
    }

    @Test
    public void createOrder() { //создаём заказ, удалить из базы его нельзя, можно только сменить статус, поэтому здесь за собой не подчищаем
        OrderSerialization order = new OrderSerialization("Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                "5",
                "2020-06-06",
                "Saske, come back to Konoha",
                color);
        OrderLogic obj = new OrderLogic();
        obj.createOrder(order).then().assertThat().body(containsString("track"))
                .and()
                .statusCode(SC_CREATED);
    }
}
