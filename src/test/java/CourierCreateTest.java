import io.restassured.response.Response;
import org.junit.*;
import java.io.File;

import static org.hamcrest.Matchers.*;

public class CourierCreateTest extends BaseTest{

    @Test
    public void createCourier(){
        File json1 = new File("src/test/resources/courierCreateCorrect.json");
        File json2 = new File("src/test/resources/courierLoginCorrect.json");
        Logic obj = new Logic();
        Response response = obj.createCourier(json1); //создаём и записываем ответ
        obj.deleteCourier(json2); //удаляем
        response.then().assertThat().body("ok", is(true))
                .and()
                .statusCode(201);
    }

    @Test
    public void createCourierCopy(){
        File json1 = new File("src/test/resources/courierCreateCorrect.json");
        File json2 = new File("src/test/resources/courierLoginCorrect.json");
        Logic obj = new Logic();
        obj.createCourier(json1); //создаём
        Response response = obj.createCourier(json1); //создаём повторно и записываем ответ
        obj.deleteCourier(json2); //удаляем
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);

    }

    @Test
    public void createCourierWithEmptyLogin(){
        File json = new File("src/test/resources/courierCreateWithEmptyLogin.json");
        Logic obj = new Logic();
        obj.createCourier(json).then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);

    }

    @Test
    public void createCourierWithEmptyPassword(){
        File json = new File("src/test/resources/courierCreateWithEmptyPassword.json");
        Logic obj = new Logic();
        obj.createCourier(json).then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);

    }
}
