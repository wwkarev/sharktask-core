package com.github.wwkarev.sharktask.core.task

import com.github.wwkarev.gorm.Select
import com.github.wwkarev.gorm.exceptions.RecordNotFoundException
import com.github.wwkarev.sharktask.api.task.TaskAccessor as API_TaskAccessor
import com.github.wwkarev.sharktask.api.task.TaskNotFoundException
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.exception.MethodNotImplementedException
import groovy.sql.Sql

trait TaskAccessor implements API_TaskAccessor {
    abstract Sql getSql()
    abstract Config getConfig()

    @Override
    MutableTask getById(Long id) {
        try {
            return new MutableTask(getSql(), getConfig(), Select.get(getSql(), getConfig().getTaskModel(), 'id', id))
        } catch(RecordNotFoundException e) {
            throw new TaskNotFoundException()
        }
    }

    @Override
    MutableTask getByKey(String key) {
        try {
            throw new MethodNotImplementedException()
        } catch(RecordNotFoundException e) {
            throw new TaskNotFoundException()
        }
    }

    @Override
    MutableTask getAtById(Long id) {
        try {
            return new MutableTask(getSql(), getConfig(), Select.get(getSql(), getConfig().getTaskModel(), 'id', id))
        } catch(RecordNotFoundException e) {
            return null
        }
    }

    @Override
    MutableTask getAtByKey(String key) {
        try {
            throw new MethodNotImplementedException()
        } catch(RecordNotFoundException e) {
            return null
        }
    }
}
