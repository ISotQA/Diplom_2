package ru.yandex.practikum.generator;

import ru.yandex.practikum.dto.User;

public class LoginGenerator {
    public static User getLogin(User user) {
        User login = new User();
        login.setEmail(user.getEmail());
        login.setName(user.getName());
        return login;
    }
}
