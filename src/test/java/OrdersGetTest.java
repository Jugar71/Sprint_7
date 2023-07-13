import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.empty;

public class OrdersGetTest extends BaseTest {
    @Test
    public void getOrders(){ //получает список заказов
        Logic obj = new Logic();
        obj.getOrders().then().assertThat().body(Matchers.not(empty()))
                .and()
                .statusCode(200);
    }
}
