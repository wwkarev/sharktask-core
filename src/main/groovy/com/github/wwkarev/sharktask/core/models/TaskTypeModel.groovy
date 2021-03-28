package com.github.wwkarev.sharktask.core.models

import com.github.wwkarev.gorm.config.Config
import groovy.sql.Sql

abstract class TaskTypeModel extends SharkTaskModel {
    String name

    TaskTypeModel(Sql sql, Long id, String name) {
        super(sql)
        this.id = id
        this.name = name
    }

    @Override
    Config config() {
        return new Config(tableName: getTableName())
    }
}
