package com.github.wwkarev.sharktask.core.tasktype

import com.github.wwkarev.sharktask.api.tasktype.TaskTypeCreator as API_TaskTypeCreator
import com.github.wwkarev.sharktask.core.config.DBTableNames
import groovy.sql.Sql

class TaskTypeCreator implements API_TaskTypeCreator {
    private Sql sql
    private DBTableNames dbTableNames
    private String name

    private TaskTypeCreator(Sql sql, DBTableNames dbTableNames, String name) {
        this.sql = sql
        this.dbTableNames = dbTableNames
        this.name = name
    }

    static TaskTypeCreator getInstance(Sql sql, DBTableNames dbTableNames, String name) {
        return new TaskTypeCreator(sql, dbTableNames, name)
    }

    @Override
    TaskType create() {
        Long id = sql.executeInsert(
                "insert into ${dbTableNames.getTaskTypeTable()} (name) VALUES(?)".toString(),
                [name]
        )[0][0]
        return new TaskType(id, name)
    }
}
