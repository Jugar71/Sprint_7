package tests;

import serialization.CourierCreateSerialization;
import logic.CourierLogic;
import serialization.LoginSerialization;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class CourierCreateTest extends BaseTest {
    private boolean isCleaningNeeded = false;

    @Test
    public void createCourier(){
        isCleaningNeeded = true;
        CourierCreateSerialization courier = new CourierCreateSerialization("109876543210987654321tset", "12345678", "test");
        CourierLogic obj = new CourierLogic();
        obj.createCourier(courier).then().assertThat().body("ok", is(true))
                .and()
                .statusCode(SC_CREATED);
    }

    @Test
    public void createCourierCopy(){
        isCleaningNeeded = true;
        CourierCreateSerialization courier = new CourierCreateSerialization("109876543210987654321tset", "12345678", "test");
        CourierLogic obj = new CourierLogic();
        obj.createCourier(courier); //создаём
        obj.createCourier(courier).then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT);

    }

    @Test
    public void createCourierWithEmptyLogin(){
        CourierCreateSerialization courier = new CourierCreateSerialization("", "5678", "naruto");
        CourierLogic obj = new CourierLogic();
        obj.createCourier(courier).then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);

    }

    @Test
    public void createCourierWithEmptyPassword(){
        CourierCreateSerialization courier = new CourierCreateSerialization("hokage", "", "naruto");
        CourierLogic obj = new CourierLogic();
        obj.createCourier(courier).then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);

    }

    @After
    public void deleteCourier(){
        if (isCleaningNeeded){
            LoginSerialization login = new LoginSerialization("109876543210987654321tset", "12345678");
            CourierLogic obj = new CourierLogic();
            obj.deleteCourier(login);
            isCleaningNeeded = false;
        }
    }
}
