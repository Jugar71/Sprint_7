package tests;

import org.hamcrest.Matchers;
import org.junit.Test;
import logic.OrderLogic;

import static org.hamcrest.Matchers.empty;
import static org.apache.http.HttpStatus.*;

public class OrdersGetTest extends BaseTest {
    @Test
    public void getOrders(){ //получает список заказов
        OrderLogic obj = new OrderLogic();
        obj.getOrders().then().assertThat().body(Matchers.not(empty()))
                .and()
                .statusCode(SC_OK);
    }
}
