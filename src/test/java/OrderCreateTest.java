import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practikum.dto.Ingredient;
import ru.yandex.practikum.dto.Order;
import ru.yandex.practikum.dto.User;
import ru.yandex.practikum.user.OrderClient;
import ru.yandex.practikum.user.UserClient;

import java.util.List;

import static java.util.Arrays.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.yandex.practikum.generator.UserGenerator.getUser;

public class OrderCreateTest {

    private OrderClient orderClient;
    private Order order = new Order();
    private UserClient userClient;
    private User randomUser;


    @Before
    public void setUp() {
        userClient = new UserClient();
        randomUser = getUser();
        userClient.createUser(randomUser)
                .assertThat()
                .statusCode(SC_OK);
        orderClient = new OrderClient();
        List<Ingredient> ingredients = OrderClient.getIngredients().getData();
        order.setIngredients(ingredients);
    }


    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с авторизацией и ингредиентами")
    public void orderShouldBeCreatedWithAuthorizationTest() {
        orderClient.createOrder(userClient.getAccessToken(), order)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа без авторизации")
    public void orderShouldBeCreatedWithoutAuthorizationTest() {
        orderClient.createOrder("", order)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа без ингредиентов")
    public void orderShouldBeCreatedWithoutIngredientsTest() {
        order.getIngredients().clear();
        orderClient.createOrder(userClient.getAccessToken(), order)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с неверным хешем ингредиентов")
    public void orderShouldBeCreatedWithInvalidHashTest() {
        Ingredient temporary = order.getIngredients().get(0);
        temporary.set_id("8");
        order.getIngredients().clear();
        order.setIngredients(asList(temporary));
        orderClient.createOrder(userClient.getAccessToken(), order)
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
