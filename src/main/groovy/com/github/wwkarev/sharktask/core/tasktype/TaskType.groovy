package com.github.wwkarev.sharktask.core.tasktype

import com.github.wwkarev.sharktask.api.tasktype.TaskType as API_TaskType

final class TaskType implements API_TaskType {
    private Long id
    private String name

    TaskType(Long id, String name) {
        this.id = id
        this.name = name
    }

    @Override
    Long getId() {
        return id
    }

    @Override
    String getName() {
        return name
    }
}
