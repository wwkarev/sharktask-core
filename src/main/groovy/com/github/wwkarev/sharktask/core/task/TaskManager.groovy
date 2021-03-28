package com.github.wwkarev.sharktask.core.task

import com.github.wwkarev.sharktask.api.task.TaskManager as API_TaskManager
import com.github.wwkarev.sharktask.core.config.Config
import groovy.sql.Sql

final class TaskManager implements API_TaskManager, TaskAccessor, TaskCreator {
    private Sql sql
    private Config config

    private TaskManager(Sql sql, Config config) {
        this.sql = sql
        this.config = config
    }

    static TaskManager newInstance(Sql sql, Config config) {
        return new TaskManager(sql, config)
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
