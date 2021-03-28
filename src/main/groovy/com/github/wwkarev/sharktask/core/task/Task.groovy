package com.github.wwkarev.sharktask.core.task

import com.github.wwkarev.gorm.Q
import com.github.wwkarev.gorm.Select
import com.github.wwkarev.gorm.exceptions.RecordNotFoundException
import com.github.wwkarev.sharktask.api.attachment.Attachment as API_Attachment
import com.github.wwkarev.sharktask.api.comment.Comment as API_Comment
import com.github.wwkarev.sharktask.api.task.Task as API_Task
import com.github.wwkarev.sharktask.api.user.User as API_User
import com.github.wwkarev.sharktask.core.attachment.Attachment
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.exception.MethodNotImplementedException
import com.github.wwkarev.sharktask.core.field.FieldType
import com.github.wwkarev.sharktask.core.models.FieldValueModel
import com.github.wwkarev.sharktask.core.models.TaskModel
import com.github.wwkarev.sharktask.core.project.Project
import com.github.wwkarev.sharktask.core.status.Status
import com.github.wwkarev.sharktask.core.tasktype.TaskType
import com.github.wwkarev.sharktask.core.user.UserManager
import groovy.json.JsonSlurper
import groovy.sql.Sql

abstract class Task implements API_Task {
    protected Sql sql
    protected Config config
    protected TaskModel taskModel

    Task(Sql sql, Config config, TaskModel taskModel) {
        this.sql = sql
        this.config = config
        this.taskModel = taskModel
    }

    @Override
    Long getId() {
        return taskModel.id
    }

    @Override
    String getKey() {
        return taskModel.getProjectModel().key + "-" + taskModel.id
    }

    @Override
    Project getProject() {
        return new Project(taskModel.getProjectModel())
    }

    @Override
    TaskType getTaskType() {
        return new TaskType(taskModel.getTaskTypeModel())
    }

    @Override
    Status getStatus() {
        return new Status(taskModel.getStatusModel())
    }

    @Override
    String getSummary() {
        return _getFieldValue(config.getSummaryFieldId())
    }

    @Override
    API_User getCreator() {
        return getFieldValue(config.getCreatorFieldId())
    }

    @Override
    API_User getAssignee() {
        return getFieldValue(config.getAssigneeFieldId())
    }

    @Override
    Date getCreatedDate() {
        return taskModel.created
    }

    @Override
    Object getFieldValue(Long fieldId) {
        try {
            return _getFieldValue(fieldId)
        } catch (RecordNotFoundException e) {
            return null
        }
    }

    @Override
    List<Long> getInwardLinkedTaskId(Long linkTypeId) {
        throw new MethodNotImplementedException()
    }

    @Override
    List<Long> getOutwardLinkedTaskId(Long linkTypeId) {
        throw new MethodNotImplementedException()
    }

    @Override
    List<API_Attachment> getAttachments() {
        return Select.filter(sql, config.getAttachmentModel(), 'taskId', id).collect{new Attachment(it)}
    }

    @Override
    List<API_Comment> getComments() {
        throw new MethodNotImplementedException()
    }

    private Object _getFieldValue(Long fieldId) {
        FieldValueModel fieldValueModel = Select.get(
                sql, config.getFieldValueModel(), new Q([taskId: taskModel.id, fieldId: fieldId])
        )
        def value
            switch (FieldType.fromString(fieldValueModel.getFieldModel().type)) {
                case FieldType.TEXT:
                    value = fieldValueModel.textValue
                    break
                case FieldType.NUMBER:
                    value = fieldValueModel.numberValue
                    break
                case FieldType.DATE:
                    value = fieldValueModel.dateValue
                    break
                case FieldType.USER:
                    String userKey = fieldValueModel.textValue
                    value = userKey ? UserManager.newInstance(sql, config).getByKey(userKey) : null
                    break
                case FieldType.JSON:
                    value = new JsonSlurper().parseText(fieldValueModel.textValue)
                    break
        }
        return value
    }
}
