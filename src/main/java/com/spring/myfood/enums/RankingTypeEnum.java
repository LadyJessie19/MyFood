package com.spring.myfood.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RankingTypeEnum {
    FOOD("Food"),
    CATEGORY("Category");

    private final String type;
}
