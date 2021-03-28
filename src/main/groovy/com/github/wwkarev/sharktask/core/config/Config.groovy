package com.github.wwkarev.sharktask.core.config

import com.github.wwkarev.sharktask.core.models.AttachmentModel
import com.github.wwkarev.sharktask.core.models.FieldModel
import com.github.wwkarev.sharktask.core.models.FieldValueModel
import com.github.wwkarev.sharktask.core.models.ProjectModel
import com.github.wwkarev.sharktask.core.models.StatusModel
import com.github.wwkarev.sharktask.core.models.TaskModel
import com.github.wwkarev.sharktask.core.models.TaskTypeModel
import com.github.wwkarev.sharktask.core.models.UserModel

trait Config {
    abstract Class<? extends ProjectModel> getProjectModel()
    abstract Class<? extends TaskTypeModel> getTaskTypeModel()
    abstract Class<? extends TaskModel> getTaskModel()
    abstract Class<? extends FieldModel> getFieldModel()
    abstract Class<? extends FieldValueModel> getFieldValueModel()
    abstract Class<? extends StatusModel> getStatusModel()
    abstract Class<? extends UserModel> getUserModel()
    abstract Class<? extends AttachmentModel> getAttachmentModel()


    String getStatusFieldName() {
        return 'Status'
    }

    Long getCreatorFieldId() {
        return 1
    }

    String getCreatorFieldName() {
        return 'Creator'
    }

    Long getAssigneeFieldId() {
        return 2
    }

    String getAssigneeFieldName() {
        return 'Assignee'
    }

    Long getSummaryFieldId() {
        return 3
    }

    String getSummaryFieldName() {
        return 'Summary'
    }
}
