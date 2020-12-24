package com.github.wwkarev.sharktask.core.status

import com.github.wwkarev.sharktask.api.status.StatusCreator as API_StatusCreator
import com.github.wwkarev.sharktask.core.config.DBTableNames
import groovy.sql.Sql

class StatusCreator implements API_StatusCreator {
    private Sql sql
    private DBTableNames dbTableNames
    private String name

    private StatusCreator(Sql sql, DBTableNames dbTableNames, String name) {
        this.sql = sql
        this.dbTableNames = dbTableNames
        this.name = name
    }

    static StatusCreator getInstance(Sql sql, DBTableNames dbTableNames, String name) {
        return new StatusCreator(sql, dbTableNames, name)
    }

    @Override
    Status create() {
        Long id = sql.executeInsert(
                "insert into ${dbTableNames.getStatusTable()} (name) VALUES(?)".toString(),
                [name]
        )[0][0]
        return new Status(id, name)
    }
}
