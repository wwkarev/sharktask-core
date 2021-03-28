package com.github.wwkarev.sharktask.core.models

import groovy.sql.Sql

class TaskModelT extends TaskModel {
    TaskModelT(Sql sql, Long projectId, Long taskTypeId, Long statusId, Date created) {
        super(sql, projectId, taskTypeId, statusId, created)
    }

    @Override
    Class<? extends ProjectModel> getProjectModelClass() {
        return ProjectModelT
    }

    @Override
    Class<? extends TaskTypeModel> getTaskTypeModelClass() {
        return TaskTypeModelT
    }

    @Override
    Class<? extends StatusModel> getStatusModelClass() {
        return StatusModelT
    }

    @Override
    String getTableName() {
        return 'task'
    }
}
