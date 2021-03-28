package com.github.wwkarev.sharktask.core.status

import com.github.wwkarev.sharktask.api.status.StatusManager as API_StatusManager
import com.github.wwkarev.sharktask.core.config.Config
import groovy.sql.Sql

final class StatusManager implements API_StatusManager, StatusAccessor, StatusCreator {
    private Sql sql
    private Config config

    private StatusManager(Sql sql, Config config) {
        this.sql = sql
        this.config = config
    }

    static StatusManager newInstance(Sql sql, Config config) {
        return new StatusManager(sql, config)
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
