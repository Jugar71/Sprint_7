import org.apache.maven.surefire.shared.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseTest {
    private final String color;

    public OrderCreateTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] testCases() {
        return new Object[][] {
                { "[\"BLACK\"]}" },
                { "[\"GREY\"]}" },
                { "[\"BLACK\", \"GREY\"]}" },
                { "[\"\"]}" },
        };
    }

    @Test
    public void createOrder() throws IOException { //создаём заказ, удалить из базы его нельзя, можно только сменить статус, поэтому здесь за собой не подчищаем
        File file = new File("src/test/resources/order.txt");
        String order = FileUtils.readFileToString(file, StandardCharsets.UTF_8); //читаем из файла большую часть заказа
        order = order + color; //дописываем цвет
        Logic obj = new Logic();
        obj.createOrder(order).then().assertThat().body(containsString("track"))
                .and()
                .statusCode(201);
    }
}
