package com.github.wwkarev.sharktask.core.tasktype

import com.github.wwkarev.sharktask.api.tasktype.TaskTypeManager as API_TaskTypeManager
import com.github.wwkarev.sharktask.core.config.Config
import groovy.sql.Sql

final class TaskTypeManager implements API_TaskTypeManager, TaskTypeAccessor, TaskTypeCreator {
    private Sql sql
    private Config config

    private TaskTypeManager(Sql sql, Config config) {
        this.sql = sql
        this.config = config
    }

    static TaskTypeManager newInstance(Sql sql, Config config) {
        return new TaskTypeManager(sql, config)
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
