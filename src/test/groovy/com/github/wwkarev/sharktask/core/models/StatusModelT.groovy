package com.github.wwkarev.sharktask.core.models

import groovy.sql.Sql

class StatusModelT extends StatusModel {
    StatusModelT(Sql sql, Long id, String name) {
        super(sql, id, name)
    }

    @Override
    String getTableName() {
        return 'status'
    }
}
