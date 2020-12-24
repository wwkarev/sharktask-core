package com.github.wwkarev.sharktask.core.tasktype

import com.github.wwkarev.sharktask.api.tasktype.TaskTypeAccessor as API_TaskTypeAccessor
import com.github.wwkarev.sharktask.core.config.DBTableNames
import groovy.sql.Sql

class TaskTypeAccessor implements API_TaskTypeAccessor {
    private Sql sql
    private DBTableNames dbTableNames

    private TaskTypeAccessor(Sql sql, DBTableNames dbTableNames) {
        this.sql = sql
        this.dbTableNames = dbTableNames
    }

    static TaskTypeAccessor getInstance(Sql sql, DBTableNames dbTable) {
        return new TaskTypeAccessor(sql, dbTable)
    }

    @Override
    TaskType getById(Long id) {
        def rowResult = sql.rows(
                "select * from ${dbTableNames.getTaskTypeTable()} where id = ?".toString(),
                [id]
        )[0]
        return new TaskType(rowResult.id, rowResult.name)
    }
}
