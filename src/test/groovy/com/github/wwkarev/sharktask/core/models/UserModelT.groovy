package com.github.wwkarev.sharktask.core.models

import com.github.wwkarev.gorm.config.Config
import groovy.sql.Sql

class UserModelT extends UserModel {
    UserModelT(Sql sql, String key, String firstName, String lastName, String fullName) {
        super(sql, key, firstName, lastName, fullName)
    }

    @Override
    String getTableName() {
        return 'sht_user'
    }
}
