package com.github.wwkarev.sharktask.core.models

import groovy.sql.Sql

class FieldValueModelT extends FieldValueModel {
    FieldValueModelT(Sql sql, Long taskId, Long fieldId, String textValue, Double numberValue, Date dateValue) {
        super(sql, taskId, fieldId, textValue, numberValue, dateValue)
    }

    @Override
    Class<? extends TaskModel> getTaskModelClass() {
        return TaskModelT
    }

    @Override
    Class<? extends FieldModel> getFieldModelClass() {
        return FieldModelT
    }

    @Override
    String getTableName() {
        return 'field_value'
    }
}
