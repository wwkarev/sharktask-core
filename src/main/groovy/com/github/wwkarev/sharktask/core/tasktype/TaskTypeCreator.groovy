package com.github.wwkarev.sharktask.core.tasktype

import com.github.wwkarev.sharktask.api.params.Params
import com.github.wwkarev.sharktask.api.tasktype.TaskTypeCreator as API_TaskTypeCreator
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.models.TaskTypeModel
import groovy.sql.Sql

trait TaskTypeCreator implements API_TaskTypeCreator {
    abstract Sql getSql()
    abstract Config getConfig()

    @Override
    TaskType create(Long id, String name, Params params = null) {
        return _create(id, name, params)
    }

    @Override
    TaskType create(String name, Params params = null) {
        return _create(null, name, params)
    }

    TaskType _create(Long id, String name, Params params) {
        TaskTypeModel taskTypeModel = getConfig().getTaskTypeModel()
                .getDeclaredConstructor(Sql, Long, String)
                .newInstance(getSql(), id, name)
                .insert()
        return new TaskType(taskTypeModel)
    }
}
