package com.github.wwkarev.sharktask.core.user

import com.github.wwkarev.sharktask.api.user.UserAccessor as API_UserAccessor
import com.github.wwkarev.sharktask.core.config.DBTableNames
import groovy.sql.Sql

class UserAccessor implements API_UserAccessor {
    private Sql sql
    private DBTableNames dbTableNames

    private UserAccessor(Sql sql, DBTableNames dbTableNames) {
        this.sql = sql
        this.dbTableNames = dbTableNames
    }

    static UserAccessor getInstance(Sql sql, DBTableNames dbTable) {
        return new UserAccessor(sql, dbTable)
    }

    @Override
    User getById(Long id) {
        def rowResult = sql.rows(
                "select * from ${dbTableNames.getUserTable()} where id = ?".toString(),
                [id]
        )[0]
        return new User(rowResult.id, rowResult.key, rowResult.first_name, rowResult.last_name, rowResult.full_name)
    }

    @Override
    User getByKey(String key) {
        def rowResult = sql.rows(
                "select * from ${dbTableNames.getUserTable()} where key = ?".toString(),
                [key]
        )[0]
        return new User(rowResult.id, rowResult.key, rowResult.first_name, rowResult.last_name, rowResult.full_name)
    }
}
