import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practikum.dto.User;
import ru.yandex.practikum.generator.UserGenerator;
import ru.yandex.practikum.user.UserClient;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.yandex.practikum.generator.UserGenerator.getUser;

@RunWith(Parameterized.class)
public class UserEditTest {
    private UserClient userClient;
    private User randomUser;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userClient.createUser(randomUser)
                .assertThat()
                .statusCode(SC_OK);
    }

    @Parameterized.Parameters
    public static Object[][] setParams() {
        return new Object[][]{
                {Field.NAME},
                {Field.EMAIL},
                {Field.PASSWORD},
        };
    }

    public UserEditTest(Field field) {
        randomUser = getUser();
        System.out.println(userClient.getAccessToken());
        switch (field) {
            case NAME:randomUser.setName(randomUser.getName()+"_edit");
                break;
            case EMAIL:randomUser.setEmail(UserGenerator.randomEmail());
                break;
            case PASSWORD:randomUser.setPassword(randomUser.getPassword()+"_edit");
                break;
        }
    }

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Изменение данных авторизованного пользователя")
    public void changeDataAuthorizedUserTest () {
        userClient.updateUser(randomUser, true)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Изменение данных неавторизованного пользователя")
    public void changeDataUnauthorizedUserTest () {
        userClient.updateUser(randomUser, false)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("message", equalTo("You should be authorised"));
    }
}

enum Field{NAME, EMAIL, PASSWORD}

