package com.github.wwkarev.sharktask.core.models

import com.github.wwkarev.gorm.config.Config
import com.github.wwkarev.sharktask.core.tasktype.TaskType
import groovy.sql.Sql

class TaskTypeModelT extends TaskTypeModel {
    TaskTypeModelT(Sql sql, Long id, String name) {
        super(sql, id, name)
    }

    @Override
    String getTableName() {
        return 'task_type'
    }
}
