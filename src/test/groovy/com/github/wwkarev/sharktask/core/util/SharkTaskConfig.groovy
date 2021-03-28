package com.github.wwkarev.sharktask.core.util

import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.models.AttachmentModel
import com.github.wwkarev.sharktask.core.models.AttachmentModelT
import com.github.wwkarev.sharktask.core.models.FieldModel
import com.github.wwkarev.sharktask.core.models.FieldModelT
import com.github.wwkarev.sharktask.core.models.FieldValueModelT
import com.github.wwkarev.sharktask.core.models.ProjectModelT
import com.github.wwkarev.sharktask.core.models.StatusModelT
import com.github.wwkarev.sharktask.core.models.TaskModelT
import com.github.wwkarev.sharktask.core.models.TaskTypeModelT
import com.github.wwkarev.sharktask.core.models.UserModelT

class SharkTaskConfig implements Config {
    @Override
    Class getProjectModel() {
        return ProjectModelT
    }

    @Override
    Class getTaskTypeModel() {
        return TaskTypeModelT
    }

    @Override
    Class getTaskModel() {
        return TaskModelT
    }

    @Override
    Class<? extends FieldModel> getFieldModel() {
        return FieldModelT
    }

    @Override
    Class getFieldValueModel() {
        return FieldValueModelT
    }

    @Override
    Class getStatusModel() {
        return StatusModelT
    }

    @Override
    Class getUserModel() {
        return UserModelT
    }

    @Override
    Class<? extends AttachmentModel> getAttachmentModel() {
        return AttachmentModelT
    }
}
