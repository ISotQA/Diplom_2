package ru.yandex.practikum.dto;

import lombok.Data;
import java.util.List;

@Data
public class ListOfIngredient {
    private Boolean success;
    private List<Ingredient> data;
}
