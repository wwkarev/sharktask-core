package com.github.wwkarev.sharktask.core.field

import com.github.wwkarev.sharktask.api.field.Field as API_Field

final class Field implements API_Field {
    private Long id
    private String name
    private String valueType

    Field(Long id, String name, String valueType) {
        this.id = id
        this.name = name
        this.valueType = valueType
    }

    @Override
    Long getId() {
        return id
    }

    @Override
    String getName() {
        return name
    }

    String getValueType() {
        return valueType
    }

    static class ValueType {
        static final String DATE = 'date'
        static final String NUMBER = 'number'
        static final String TEXT = 'text'
    }
}
