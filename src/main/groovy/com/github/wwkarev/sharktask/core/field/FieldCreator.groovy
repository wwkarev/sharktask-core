package com.github.wwkarev.sharktask.core.field

import com.github.wwkarev.sharktask.api.field.FieldCreator as API_FieldCreator
import com.github.wwkarev.sharktask.core.config.DBTableNames
import groovy.sql.Sql

class FieldCreator implements API_FieldCreator {
    private Sql sql
    private DBTableNames dbTableNames
    private String name
    private String valueType

    private FieldCreator(Sql sql, DBTableNames dbTableNames, String name, String valueType) {
        this.sql = sql
        this.dbTableNames = dbTableNames
        this.name = name
        this.valueType = valueType
    }

    static FieldCreator getInstance(Sql sql, DBTableNames dBTableNames, String name, String valueType) {
        return new FieldCreator(sql, dBTableNames, name, valueType)
    }

    @Override
    Field create() {
        Long id = sql.executeInsert(
                "insert into ${dbTableNames.getFieldTable()} (name, value_type) VALUES(?, ?)".toString(),
                [name, valueType]
        )[0][0]
        return new Field(id, name, valueType)
    }
}
