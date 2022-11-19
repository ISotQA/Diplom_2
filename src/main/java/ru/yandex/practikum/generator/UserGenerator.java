package ru.yandex.practikum.generator;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.practikum.dto.User;

public class UserGenerator {

    public static String randomEmail() {
        return RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
    }

    public static User getUser() {
        User user = new User();
        user.setName("name");
        user.setEmail(randomEmail());
        user.setPassword("password");
        return user;
    }

    public static User getUserWithoutEmail() {
        User user = new User();
        user.setName("Ira");
        user.setPassword("12345");
        return user;
    }

    public static User getUserWithoutName() {
        User user = new User();
        user.setEmail(randomEmail());
        user.setPassword("12345");
        user.setName("");
        return user;
    }

    public static User getUserWithoutPassword() {
        User user = new User();
        user.setEmail(randomEmail());
        user.setPassword("");
        user.setName("Ira");
        return user;
    }
}
