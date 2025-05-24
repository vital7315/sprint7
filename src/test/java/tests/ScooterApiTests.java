package tests;

import clients.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.Courier;
import models.CourierCreds;
import models.CourierId;
import org.junit.After;
import org.junit.Test;

import static generators.CourierGenerator.randomCourier;
import static models.CourierCreds.credsFromCourier;
import static org.junit.Assert.*;

public class ScooterApiTests {

    private final CourierClient courierClient = new CourierClient();

    private Courier courier;
    private int courierId;

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    // 1. Тесты создания курьера
    @Test
    @DisplayName("Создание курьера и возвращение id созданного курьера")
    public void createCourierSuccess() {
        courier = randomCourier();

        Response createResponse = courierClient.create(courier);
        assertEquals(201, createResponse.statusCode());
        assertTrue(createResponse.path("ok"));

        Response loginResponse = courierClient.login(credsFromCourier(courier));
        courierId = loginResponse.as(CourierId.class).getId();
        assertEquals(200, loginResponse.statusCode());
    }

    @Test
    @DisplayName("Создание уже созданного курьера")
    public void createDuplicateCourierFails() {
        courier = randomCourier();
        courierClient.create(courier);

        Response duplicateResponse = courierClient.create(courier);
        assertEquals(409, duplicateResponse.statusCode());
        assertEquals("Этот логин уже используется", duplicateResponse.path("message"));

        // Очистка
        Response loginResponse = courierClient.login(credsFromCourier(courier));
        courierId = loginResponse.as(CourierId.class).getId();
    }

    @Test
    @DisplayName("Создание курьера без обязательных полей")
    public void createCourierWithoutRequiredFieldsFails() {
        // Без логина
        Courier noLogin = randomCourier().setLogin(null);
        Response noLoginResponse = courierClient.create(noLogin);
        assertEquals(400, noLoginResponse.statusCode());

        // Без пароля
        Courier noPassword = randomCourier().setPassword(null);
        Response noPasswordResponse = courierClient.create(noPassword);
        assertEquals(400, noPasswordResponse.statusCode());
    }

    // 2. Тесты авторизации курьера
    @Test
    @DisplayName("Авторизация курьера")
    public void loginCourierSuccess() {
        courier = randomCourier();
        courierClient.create(courier);

        Response loginResponse = courierClient.login(credsFromCourier(courier));
        courierId = loginResponse.as(CourierId.class).getId();

        assertEquals(200, loginResponse.statusCode());
        assertTrue(courierId > 0);
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным паролем")
    public void loginWithWrongCredentialsFails() {
        courier = randomCourier();
        courierClient.create(courier);

        // Неправильный пароль
        CourierCreds wrongPass = new CourierCreds(courier.getLogin(), "wrong");
        Response wrongPassResponse = courierClient.login(wrongPass);
        assertEquals(404, wrongPassResponse.statusCode());

        // Очистка
        Response loginResponse = courierClient.login(credsFromCourier(courier));
        courierId = loginResponse.as(CourierId.class).getId();
    }

    @Test
    @DisplayName("Авторизация курьера без заполнения обязательных полей")
    public void loginWithoutRequiredFieldsFails() {
        // Без логина
        Response noLoginResponse = courierClient.login(new CourierCreds(null, "pass"));
        assertEquals(400, noLoginResponse.statusCode());

        // Без пароля
        Response noPasswordResponse = courierClient.login(new CourierCreds("login", null));
        assertEquals(400, noPasswordResponse.statusCode());
    }

    // 4. Тест списка заказов
    @Test
    @DisplayName("Создание списка заказа")
    public void getOrdersListReturnsNonEmptyArray() {
        Response ordersResponse = courierClient.getOrdersList();
        assertEquals(200, ordersResponse.statusCode());
        assertNotEquals("[]", ordersResponse.path("orders").toString());
    }
}
