package ru.yandex.practikum.dto;

import lombok.Data;

@Data
public class Owner {
    private String name;
    private String email;
    private String createdAt;
    private String updatedAt;
}
