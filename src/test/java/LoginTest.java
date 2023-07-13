import io.restassured.response.Response;
import org.junit.Test;
import java.io.File;

import static org.hamcrest.Matchers.*;

public class LoginTest extends BaseTest {

    @Test
    public void courierCorrectLogin() {
        File json1 = new File("src/test/resources/courierCreateCorrect.json");
        File json2 = new File("src/test/resources/courierLoginCorrect.json");
        Logic obj = new Logic();
        obj.createCourier(json1); //создаём
        Response response = obj.login(json2); //логинимся и записываем ответ
        obj.deleteCourier(json2); //удаляем
        response.then().assertThat().body(containsString("id"))
                .and()
                .statusCode(200);
    }

    @Test
    public void courierLoginWithEmptyLogin() {
        File json = new File("src/test/resources/courierLoginWithEmptyLogin.json");
        Logic obj = new Logic();
        obj.login(json).then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void courierLoginWithEmptyPassword() {
        File json = new File("src/test/resources/courierLoginWithEmptyPassword.json");
        Logic obj = new Logic();
        obj.login(json).then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void courierLoginWithWrongLogin() {
        File json1 = new File("src/test/resources/courierCreateCorrect.json");
        File json2 = new File("src/test/resources/courierLoginWithWrongLogin.json");
        File json3 = new File("src/test/resources/courierLoginCorrect.json");
        Logic obj = new Logic();
        obj.createCourier(json1); //создаём
        Response response = obj.login(json2); //логинимся и записываем ответ
        obj.deleteCourier(json3); //удаляем
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    public void courierLoginWithWrongPassword() {
        File json1 = new File("src/test/resources/courierCreateCorrect.json");
        File json2 = new File("src/test/resources/courierLoginWithWrongPassword.json");
        File json3 = new File("src/test/resources/courierLoginCorrect.json");
        Logic obj = new Logic();
        obj.createCourier(json1); //создаём
        Response response = obj.login(json2); //логинимся и записываем ответ
        obj.deleteCourier(json3); //удаляем
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
}
