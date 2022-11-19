import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practikum.dto.User;
import ru.yandex.practikum.user.UserClient;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.yandex.practikum.generator.UserGenerator.getUser;

public class UserLoginTest {

    private UserClient userClient;
    private User randomUser;

    @Before
    public void setUp() {
        userClient = new UserClient();
        randomUser = getUser();
        userClient.createUser(randomUser)
                .assertThat()
                .statusCode(SC_OK);
    }

    @After
    public void tearDown() {
        userClient.delete();
    }

    @Test
    @DisplayName("Авторизация пользователя")
    @Description("Проверка авторизации пользователя под существующим пользователем")
    public void userMustBeAuthorizedUserExistsTest() {
         userClient.loginUser(randomUser)
                .assertThat()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Авторизация пользователя")
    @Description("Проверка авторизации пользователя с неверным логином")
    public void userMustBeAuthorizedIncorrectLoginTest() {
        randomUser.setEmail("12345");
        userClient.loginUser(randomUser)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация пользователя")
    @Description("Проверка авторизации пользователя с неверным паролем")
    public void userMustBeAuthorizedIncorrectPasswordTest() {
        randomUser.setPassword("");
        userClient.loginUser(randomUser)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }
}
