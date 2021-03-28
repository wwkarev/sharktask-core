package com.github.wwkarev.sharktask.core.tasktype

import com.github.wwkarev.gorm.Select
import com.github.wwkarev.gorm.exceptions.RecordNotFoundException
import com.github.wwkarev.sharktask.api.tasktype.TaskTypeAccessor as API_TaskTypeAccessor
import com.github.wwkarev.sharktask.api.tasktype.TaskTypeNotFoundException
import com.github.wwkarev.sharktask.core.config.Config
import groovy.sql.Sql

trait TaskTypeAccessor implements API_TaskTypeAccessor {
    abstract Sql getSql()
    abstract Config getConfig()

    @Override
    TaskType getById(Long id) {
        try {
            return new TaskType(Select.get(getSql(), getConfig().getTaskTypeModel(), 'id', id))
        } catch(RecordNotFoundException e) {
            throw new TaskTypeNotFoundException()
        }
    }

    @Override
    TaskType getAtById(Long id) {
        try {
            return new TaskType(Select.get(getSql(), getConfig().getTaskTypeModel(), 'id', id))
        } catch(RecordNotFoundException e) {
            return null
        }
    }
}
