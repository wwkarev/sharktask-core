package com.github.wwkarev.sharktask.core.user

import com.github.wwkarev.sharktask.api.user.UserCreator as API_UserCreator
import com.github.wwkarev.sharktask.core.config.DBTableNames
import groovy.sql.Sql

class UserCreator implements API_UserCreator {
    private Sql sql
    private DBTableNames dbTableNames
    private String key
    private String firstName
    private String lastName
    private String fullName

    private UserCreator(Sql sql, DBTableNames dbTableNames, String key, String firstName, String lastName, String fullName) {
        this.sql = sql
        this.dbTableNames = dbTableNames
        this.key = key
        this.firstName = firstName
        this.lastName = lastName
        this.fullName = fullName
    }

    static UserCreator getInstance(Sql sql, DBTableNames dbTableNames, String key, String firstName, String lastName, String fullName) {
        return new UserCreator(sql, dbTableNames, key, firstName, lastName, fullName)
    }

    @Override
    User create() {
        Long id = sql.executeInsert(
                "insert into ${dbTableNames.getUserTable()} (key, first_name, last_name, full_name) VALUES(?, ?, ?, ?)".toString(),
                [key, firstName, lastName, fullName]
        )[0][0]
        return new User(id, key, firstName, lastName, fullName)
    }
}
