package com.github.wwkarev.sharktask.core.init

import com.github.wwkarev.gorm.Model
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.field.FieldType
import com.github.wwkarev.sharktask.core.models.FieldModel
import groovy.sql.Sql

class Initializer {
    private Sql sql
    private Config config

    Initializer(Sql sql, Config config) {
        this.sql = sql
        this.config = config
    }

    void init() {
        createField(config.getCreatorFieldId(), config.getCreatorFieldName(), FieldType.USER.toString())
        createField(config.getAssigneeFieldId(), config.getAssigneeFieldName(), FieldType.USER.toString())
        createField(config.getSummaryFieldId(), config.getSummaryFieldName(), FieldType.TEXT.toString())

        sql.execute("ALTER SEQUENCE ${Model.newInstance(config.getFieldModel()).getTableName()}_id_seq RESTART WITH 1000".toString())
    }

    private void createField(Long id, String name, String type) {
        FieldModel fieldModel = config.getFieldModel()
                .getDeclaredConstructor(Sql, Long, String, String)
                .newInstance(sql, id, name, type)
        fieldModel.insert()
    }
}
