package tests;

import clients.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class OrderParametrizedTest {

    private final CourierClient courierClient = new CourierClient();
    private final Order testOrder;

    public OrderParametrizedTest(Order testOrder) {
        this.testOrder = testOrder;
    }

    @Parameterized.Parameters
    public static Object[][] getColorCombinations() {
        return new Object[][]{
                {Order.blackOrder()},       // Заказ с BLACK
                {Order.greyOrder()},        // Заказ с GREY
                {Order.blackAndGreyOrder()}, // Заказ с BLACK + GREY
                {Order.noColorOrder()}      // Заказ без цвета
        };
    }

    @Test
    @DisplayName("Создание заказа с разными цветами самоката") // имя теста
    public void createOrderWithDifferentColorOptions() {
        Response orderResponse = courierClient.createOrder(testOrder);
        assertEquals(201, orderResponse.statusCode());
        assertNotNull("Трек-номер заказа не должен быть null", orderResponse.path("track"));
    }
}