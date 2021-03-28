package com.github.wwkarev.sharktask.core.models

import com.github.wwkarev.gorm.config.Config
import groovy.sql.Sql

abstract class UserModel extends SharkTaskModel {
    String key
    String firstName
    String lastName
    String fullName

    UserModel(Sql sql, String key, String firstName, String lastName, String fullName) {
        super(sql)
        this.key = key
        this.firstName = firstName
        this.lastName = lastName
        this.fullName = fullName
    }

    @Override
    Config config() {
        return new Config(tableName: getTableName())
    }
}
