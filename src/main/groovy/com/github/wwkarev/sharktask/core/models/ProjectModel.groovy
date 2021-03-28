package com.github.wwkarev.sharktask.core.models

import com.github.wwkarev.gorm.config.Config
import groovy.sql.Sql

abstract class ProjectModel extends SharkTaskModel {
    String key
    String name

    ProjectModel(Sql sql, Long id, String key, String name) {
        super(sql)
        this.id = id
        this.name = name
        this.key = key
    }

    @Override
    Config config() {
        return new Config(tableName: getTableName())
    }
}
