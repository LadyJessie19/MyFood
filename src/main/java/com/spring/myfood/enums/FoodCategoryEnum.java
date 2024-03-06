package com.spring.myfood.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FoodCategoryEnum {
    PIZZA("Pizza"),
    HAMBURGER("Hamburger"),
    SANDWICH("Sandwich"),
    PASTA("Pasta"),
    SALAD("Salad"),
    SUSHI("Sushi"),
    BBQ("BBQ"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    VEGAN("Vegan"),
    FRUIT("Fruit"),
    BEVERAGE("Beverage");

    private final String category;
}
