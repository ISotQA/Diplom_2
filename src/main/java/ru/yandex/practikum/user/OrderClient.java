package ru.yandex.practikum.user;

import io.restassured.response.ValidatableResponse;
import ru.yandex.practikum.dto.Ingredient;
import ru.yandex.practikum.dto.ListOfIngredient;
import ru.yandex.practikum.dto.Order;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    private static final String ORDER = "orders";
    private static final String GET_ORDERS = "orders";
    private static final String INGREDIENTS = "ingredients";

    public static ListOfIngredient getIngredients() {
        return given()
                .spec(getDefaultRequestSpec())
                .when()
                .get(INGREDIENTS)
                .then()
                .extract().as(ListOfIngredient.class);
    }

    public ValidatableResponse createOrder(String userToken, Order order) {
        return given()
                .spec(getDefaultRequestSpec())
                .auth().oauth2(userToken)
                .body(order)
                .post(ORDER)
                .then();
    }

    public ValidatableResponse getOrderList(String userToken, Order order) {
        return given()
                .spec(getDefaultRequestSpec())
                .auth().oauth2(userToken)
                .body(order)
                .get(GET_ORDERS)
                .then();
    }
}
