package com.github.wwkarev.sharktask.core.models

import com.github.wwkarev.gorm.config.Config
import groovy.sql.Sql

abstract class FieldModel extends SharkTaskModel {
    String name
    String type

    FieldModel(Sql sql, Long id, String name, String type) {
        super(sql)
        this.id = id
        this.name = name
        this.type = type
    }

    @Override
    Config config() {
        return new Config(tableName: getTableName())
    }
}
