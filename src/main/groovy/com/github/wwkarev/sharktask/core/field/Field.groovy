package com.github.wwkarev.sharktask.core.field

import com.github.wwkarev.sharktask.api.field.Field as API_Field
import com.github.wwkarev.sharktask.core.models.FieldModel

final class Field implements API_Field {
    private FieldModel fieldModel

    Field(FieldModel fieldModel) {
        this.fieldModel = fieldModel
    }

    @Override
    Long getId() {
        return fieldModel.id
    }

    @Override
    String getName() {
        return fieldModel.name
    }

    FieldType getType() {
        return FieldType.fromString(fieldModel.getType())
    }
}
