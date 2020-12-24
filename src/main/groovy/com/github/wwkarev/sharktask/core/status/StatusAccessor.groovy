package com.github.wwkarev.sharktask.core.status

import com.github.wwkarev.sharktask.core.config.DBTableNames
import groovy.sql.Sql

import com.github.wwkarev.sharktask.api.status.StatusAccessor as API_StatusAccessor

class StatusAccessor implements API_StatusAccessor {
    private Sql sql
    private DBTableNames dbTableNames

    private StatusAccessor(Sql sql, DBTableNames dbTableNames) {
        this.sql = sql
        this.dbTableNames = dbTableNames
    }

    static StatusAccessor getInstance(Sql sql, DBTableNames dbTable) {
        return new StatusAccessor(sql, dbTable)
    }

    @Override
    Status getById(Long id) {
        def rowResult = sql.rows(
                "select * from ${dbTableNames.getStatusTable()} where id = ?".toString(),
                [id]
        )[0]
        return new Status(rowResult.id, rowResult.name)
    }
}
