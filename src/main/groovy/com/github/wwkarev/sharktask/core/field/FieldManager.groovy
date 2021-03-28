package com.github.wwkarev.sharktask.core.field

import com.github.wwkarev.sharktask.api.field.FieldManager as API_FieldManager
import com.github.wwkarev.sharktask.core.config.Config
import groovy.sql.Sql

final class FieldManager implements API_FieldManager, FieldAccessor, FieldCreator {
    private Sql sql
    private Config config

    private FieldManager(Sql sql, Config config) {
        this.sql = sql
        this.config = config
    }

    static FieldManager newInstance(Sql sql, Config config) {
        return new FieldManager(sql, config)
    }

    @Override
    Sql getSql() {
        return sql
    }

    @Override
    Config getConfig() {
        return config
    }
}
