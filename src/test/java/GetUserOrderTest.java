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

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.practikum.generator.UserGenerator.getUser;

public class GetUserOrderTest {
    private OrderClient orderClient;
    private UserClient userClient;
    private User randomUser;
    private Order order = new Order();


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
        orderClient.createOrder(userClient.getAccessToken(), order)
                .assertThat()
                .statusCode(SC_OK);
    }

    @DisplayName("Получение списка заказов")
    @Description("Проверка получения списка заказов авторизованного пользователя")
    @Test
    public void orderListAuthorizedUserMustBeObtainedTest() {
        Order order = new Order();
        orderClient.getOrderList(userClient.getAccessToken(), order)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @DisplayName("Получение списка заказов конкретного пользователя")
    @Description("Проверка получения списка заказов неавторизованного пользователя")
    @Test
    public void orderListUnauthorizedUserMustBeObtainedTest() {
        Order order = new Order();
        orderClient.getOrderList("", order)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("message", equalTo("You should be authorised"));
    }
}
