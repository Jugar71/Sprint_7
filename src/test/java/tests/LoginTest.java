package tests;

import org.junit.After;
import serialization.CourierCreateSerialization;
import logic.CourierLogic;
import serialization.LoginSerialization;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class LoginTest extends BaseTest {
    private boolean isCleaningNeeded = false;

    @Test
    public void courierCorrectLogin() {
        isCleaningNeeded = true;
        CourierCreateSerialization courier = new CourierCreateSerialization("109876543210987654321tset", "12345678", "test");
        LoginSerialization login = new LoginSerialization("109876543210987654321tset", "12345678");
        CourierLogic obj = new CourierLogic();
        obj.createCourier(courier); //создаём
        obj.login(login).then().assertThat().body(containsString("id"))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void courierLoginWithEmptyLogin() {
        LoginSerialization login = new LoginSerialization("", "12345678");
        CourierLogic obj = new CourierLogic();
        obj.login(login).then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void courierLoginWithEmptyPassword() {
        LoginSerialization login = new LoginSerialization("109876543210987654321tset", "");
        CourierLogic obj = new CourierLogic();
        obj.login(login).then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void courierLoginWithWrongLogin() {
        isCleaningNeeded = true;
        CourierCreateSerialization courier = new CourierCreateSerialization("109876543210987654321tset", "12345678", "test");
        LoginSerialization loginWrongLogin = new LoginSerialization("tset", "12345678");
        CourierLogic obj = new CourierLogic();
        obj.createCourier(courier); //создаём
        obj.login(loginWrongLogin).then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    public void courierLoginWithWrongPassword() {
        isCleaningNeeded = true;
        CourierCreateSerialization courier = new CourierCreateSerialization("109876543210987654321tset", "12345678", "test");
        LoginSerialization loginWrongPassword = new LoginSerialization("109876543210987654321tset", "1");
        CourierLogic obj = new CourierLogic();
        obj.createCourier(courier); //создаём
        obj.login(loginWrongPassword).then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
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
