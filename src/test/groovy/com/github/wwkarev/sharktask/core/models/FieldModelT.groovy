package com.github.wwkarev.sharktask.core.models

import groovy.sql.Sql

class FieldModelT extends FieldModel {
    FieldModelT(Sql sql, Long id, String name, String type) {
        super(sql, id, name, type)
    }
    FieldModelT(Sql sql, String name, String type) {
        this(sql, null, name, type)
    }

    @Override
    String getTableName() {
        return 'field'
    }
}
