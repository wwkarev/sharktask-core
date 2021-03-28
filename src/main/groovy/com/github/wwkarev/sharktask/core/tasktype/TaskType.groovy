package com.github.wwkarev.sharktask.core.tasktype

import com.github.wwkarev.sharktask.api.tasktype.TaskType as API_TaskType
import com.github.wwkarev.sharktask.core.models.TaskTypeModel

final class TaskType implements API_TaskType {
    private TaskTypeModel taskTypeModel

    TaskType(TaskTypeModel taskTypeModel) {
        this.taskTypeModel = taskTypeModel
    }

    @Override
    Long getId() {
        return taskTypeModel.id
    }

    @Override
    String getName() {
        return taskTypeModel.name
    }
}
