package ru.yandex.practikum.user;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.yandex.practikum.config.Config;

public class RestClient {

    public static RequestSpecification getDefaultRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Config.getBaseUri())
                .setContentType(ContentType.JSON)
                .build();
    }
}
