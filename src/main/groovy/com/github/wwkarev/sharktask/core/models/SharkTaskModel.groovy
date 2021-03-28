package com.github.wwkarev.sharktask.core.models

import com.github.wwkarev.gorm.Model
import groovy.sql.Sql

abstract class SharkTaskModel extends Model {
    SharkTaskModel(Sql sql) {
        super(sql)
    }

    abstract String getTableName()
}
