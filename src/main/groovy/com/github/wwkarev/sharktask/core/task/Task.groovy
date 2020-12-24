package com.github.wwkarev.sharktask.core.task

import com.github.wwkarev.sharktask.api.attachment.Attachment as API_Attachment
import com.github.wwkarev.sharktask.api.comment.Comment as API_Comment
import com.github.wwkarev.sharktask.api.project.Project
import com.github.wwkarev.sharktask.api.project.Project as API_Project
import com.github.wwkarev.sharktask.api.status.Status
import com.github.wwkarev.sharktask.api.status.Status as API_Status
import com.github.wwkarev.sharktask.api.task.Task as API_Task
import com.github.wwkarev.sharktask.api.tasktype.TaskType
import com.github.wwkarev.sharktask.api.tasktype.TaskType as API_TaskType
import com.github.wwkarev.sharktask.api.user.User
import com.github.wwkarev.sharktask.api.user.User as API_User
import com.github.wwkarev.sharktask.core.config.DBTableNames
import com.github.wwkarev.sharktask.core.exception.MethodNotImplementedException
import com.github.wwkarev.sharktask.core.field.Field
import com.github.wwkarev.sharktask.core.field.FieldAccessor
import groovy.sql.GroovyRowResult
import groovy.sql.Sql

class Task implements API_Task {
    protected Sql sql
    protected DBTableNames dbTableNames
    protected Long id
    protected String key
    protected API_Project project
    protected API_TaskType taskType
    protected API_Status status
    protected String summary
    protected API_User creator
    protected API_User assignee
    protected Date createdDate

    Task(Sql sql, DBTableNames dbTableNames, Long id, Project project, TaskType taskType, Status status, String summary, User creator, User assignee, Date createdDate) {
        this.sql = sql
        this.dbTableNames = dbTableNames
        this.id = id
        this.key = "${project.getKey()}-$id"
        this.project = project
        this.taskType = taskType
        this.status = status
        this.summary = summary
        this.creator = creator
        this.assignee = assignee
        this.createdDate = createdDate
    }

    @Override
    Long getId() {
        return id
    }

    @Override
    String getKey() {
        return key
    }

    @Override
    API_Project getProject() {
        return project
    }

    @Override
    API_TaskType getTaskType() {
        return taskType
    }

    @Override
    API_Status getStatus() {
        return status
    }

    @Override
    String getSummary() {
        return summary
    }

    @Override
    API_User getCreator() {
        return creator
    }

    @Override
    API_User getAssignee() {
        return assignee
    }

    @Override
    Date getCreatedDate() {
        return createdDate
    }

    @Override
    Object getFieldValue(Long fieldId) {
        String valueType = FieldAccessor.getInstance(sql, dbTableNames).getById(fieldId).getValueType()
        GroovyRowResult rowResult = sql.rows((
                "select * from ${dbTableNames.getFieldValueTable()} where task_id = $id and field_id = $fieldId"
        ).toString())[0]
        def value
        switch (valueType) {
            case Field.ValueType.TEXT:
                value = rowResult?.text_value
                break
            case Field.ValueType.DATE:
                value = rowResult?.date_value
                break
            case Field.ValueType.NUMBER:
                value = rowResult?.number_value
                break
        }
        return value
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
        throw new MethodNotImplementedException()
    }

    @Override
    List<API_Comment> getComments() {
        throw new MethodNotImplementedException()
    }
}
