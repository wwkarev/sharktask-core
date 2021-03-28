package com.github.wwkarev.sharktask.core.field

import com.github.wwkarev.sharktask.core.exception.NoSuchFieldTypeException

enum FieldType {
    DATE(0),
    NUMBER(1),
    TEXT(2),
    USER(3),
    JSON(4)

    private final int value
    static private final String DATE_STR = 'date'
    static private final String NUMBER_STR = 'number'
    static private final String TEXT_STR = 'text'
    static private final String USER_STR = 'user'
    static private final String JSON_STR = 'json'

    FieldType(int value) {
        this.value = value
    }

    String toString() {
        String str
        switch (value) {
            case 0:
                str = FieldType.DATE_STR
                break
            case 1:
                str = FieldType.NUMBER_STR
                break
            case 2:
                str = FieldType.TEXT_STR
                break
            case 3:
                str = FieldType.USER_STR
                break
            case 4:
                str = FieldType.JSON_STR
                break
        }
        return str
    }

    static FieldType fromString(String str) {
        FieldType fieldType
        switch (str) {
            case FieldType.DATE_STR:
                fieldType = FieldType.DATE
                break
            case FieldType.NUMBER_STR:
                fieldType = FieldType.NUMBER
                break
            case FieldType.TEXT_STR:
                fieldType = FieldType.TEXT
                break
            case FieldType.USER_STR:
                fieldType = FieldType.USER
                break
            case FieldType.JSON_STR:
                fieldType = FieldType.JSON
                break
        }
        if (fieldType == null) {
            throw new NoSuchFieldTypeException()
        }
        return fieldType
    }
}
