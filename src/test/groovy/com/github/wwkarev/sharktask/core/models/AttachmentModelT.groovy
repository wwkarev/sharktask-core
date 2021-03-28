package com.github.wwkarev.sharktask.core.models

import groovy.sql.Sql

class AttachmentModelT extends AttachmentModel {
    AttachmentModelT(Sql sql, String filePath, String name, Long taskId) {
        super(sql, filePath, name, taskId)
    }

    @Override
    Class<? extends TaskModel> getTaskModelClass() {
        return TaskModelT
    }

    @Override
    String getTableName() {
        return 'attachment'
    }
}
