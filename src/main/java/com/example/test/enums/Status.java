package com.example.test.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ACTIVE("Активный"),
    BLOCKED("Блокированный"),
    DELETED("Удаленный")
    ;
    String DESCRIPTION;

}
