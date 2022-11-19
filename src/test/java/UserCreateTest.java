import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practikum.dto.User;
import ru.yandex.practikum.user.UserClient;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.practikum.generator.UserGenerator.*;

public class UserCreateTest {

    private UserClient userClient;
    private User randomUser;

    @Before
    public void setUp() {
        randomUser = getUser();
        userClient = new UserClient();
    }

    @After
    public void tearDown() {
        userClient.delete();
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверка на создание уникального пользователя")
    public void userShouldBeCreatedTest() {
        userClient.createUser(randomUser)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверка создания пользователя, который уже зарегистрирован")
    public void userShouldBeCreatedUserAlreadyExistsTest() {
            userClient.createUser(randomUser)
                    .assertThat()
                    .statusCode(SC_OK)
                    .and()
                    .body("success", equalTo(true));
            userClient.createUser(randomUser)
                    .assertThat()
                    .statusCode(SC_FORBIDDEN)
                    .and()
                    .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверка создания пользователя без обязательного параметра email")
    public void userShouldBeCreatedWithoutEmailTest() {
        randomUser = getUserWithoutEmail();
        userClient.createUser(randomUser)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверка создания пользователя без обязательного параметра password")
    public void userShouldBeCreatedWithoutPasswordTest() {
        randomUser = getUserWithoutPassword();
        userClient.createUser(randomUser)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверка создания пользователя без обязательного параметра name")
    public void userShouldBeCreatedWithoutNameTest() {
        randomUser = getUserWithoutName();
        userClient.createUser(randomUser)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
