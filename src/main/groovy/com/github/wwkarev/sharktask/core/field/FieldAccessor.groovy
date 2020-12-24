package com.github.wwkarev.sharktask.core.field


import com.github.wwkarev.sharktask.api.field.FieldAccessor as API_FieldAccessor
import com.github.wwkarev.sharktask.core.config.DBTableNames
import groovy.sql.Sql

class FieldAccessor implements API_FieldAccessor {
    private Sql sql
    private DBTableNames dbTableNames

    private FieldAccessor(Sql sql, DBTableNames dbTableNames) {
        this.sql = sql
        this.dbTableNames = dbTableNames
    }

    static FieldAccessor getInstance(Sql sql, DBTableNames dbTableNames) {
        return new FieldAccessor(sql, dbTableNames)
    }

    @Override
    Field getById(Long id) {
        def rowResult = sql.rows(
                "select * from ${dbTableNames.getFieldTable()} where id = ?".toString(),
                [id]
        )[0]
        return new Field(rowResult.id, rowResult.name, rowResult.value_type)
    }
}
