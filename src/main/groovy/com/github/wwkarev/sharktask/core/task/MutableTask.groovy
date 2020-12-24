package com.github.wwkarev.sharktask.core.task

import com.github.wwkarev.sharktask.api.eventdispatchoption.EventDispatchOption
import com.github.wwkarev.sharktask.api.project.Project as API_Project
import com.github.wwkarev.sharktask.api.status.Status as API_Status
import com.github.wwkarev.sharktask.api.task.MutableTask as API_MutableTask
import com.github.wwkarev.sharktask.api.tasktype.TaskType as API_TaskType
import com.github.wwkarev.sharktask.api.user.User as API_User
import com.github.wwkarev.sharktask.core.config.DBTableNames
import com.github.wwkarev.sharktask.core.exception.MethodNotImplementedException
import com.github.wwkarev.sharktask.core.field.Field
import com.github.wwkarev.sharktask.core.field.FieldAccessor
import groovy.sql.GroovyRowResult
import groovy.sql.Sql

class MutableTask extends Task implements API_MutableTask {
    MutableTask(Sql sql, DBTableNames dbTableNames, Long id, API_Project project, API_TaskType taskType, API_Status status, String summary, API_User creator, API_User assignee, Date createdDate) {
        super(sql, dbTableNames, id, project, taskType, status, summary, creator, assignee, createdDate)
    }

    @Override
    void updateValue(Long fieldId, value, API_User user, EventDispatchOption eventDispatchOption) {
        value = transformValue(value)
        String valueColumnName = getValueColumnName(fieldId)
        GroovyRowResult fieldValueRowResult =  sql.rows((
                "select * from ${dbTableNames.getFieldValueTable()} where task_id = $id and field_id = $fieldId"
        ).toString())[0]
        if (fieldValueRowResult) {
            sql.execute((
                    "update ${dbTableNames.getFieldValueTable()} set $valueColumnName = ? " +
                            "where id = ?").toString()
                    , [value, fieldValueRowResult.id])
        } else {
            sql.execute((
                    "insert into ${dbTableNames.getFieldValueTable()} " +
                            "(task_id, field_id, $valueColumnName) VALUES($id, $fieldId, ?)").toString()
                    , [value])
        }
    }

    @Override
    void addInwardLinkedTask(API_MutableTask sourceTask, Long linkTypeId, API_User user, EventDispatchOption eventDispatchOption) {
        throw new MethodNotImplementedException()
    }

    @Override
    void addOutwardLinkedTask(API_MutableTask destinationTask, Long linkTypeId, API_User user, EventDispatchOption eventDispatchOption) {
        throw new MethodNotImplementedException()
    }

    @Override
    void removeAllOutwardLinks(Long linkTypeId, API_User user, EventDispatchOption eventDispatchOption) {
        throw new MethodNotImplementedException()
    }

    @Override
    void transit(Long transtionId, API_User user) {
        throw new MethodNotImplementedException()
    }

    @Override
    void addAttachment(String filePath, String attachmentName, API_User user, EventDispatchOption eventDispatchOption) {
        throw new MethodNotImplementedException()
    }

    @Override
    void addComment(String body, API_User user, EventDispatchOption eventDispatchOption) {
        throw new MethodNotImplementedException()
    }

    @Override
    void delete(API_User user) {
        throw new MethodNotImplementedException()
    }

    private String getValueColumnName(Long fieldId) {
        String valueType = FieldAccessor.getInstance(sql, dbTableNames).getById(fieldId).getValueType()
        String valueColumnName = null
        switch (valueType) {
            case Field.ValueType.TEXT:
                valueColumnName = 'text_value'
                break
            case Field.ValueType.DATE:
                valueColumnName = 'date_value'
                break
            case Field.ValueType.NUMBER:
                valueColumnName = 'number_value'
                break
        }
        if (!valueColumnName) {
            throw new IllegalArgumentException("Value type not supported: $valueType")
        }
        return valueColumnName
    }

    private def transformValue(value) {
        if (value instanceof Date) {
            value = ((Date)value).toTimestamp()
        }
        return value
    }
}
