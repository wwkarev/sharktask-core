package com.github.wwkarev.sharktask.core.task

import com.github.wwkarev.gorm.Q
import com.github.wwkarev.gorm.Select
import com.github.wwkarev.gorm.exceptions.RecordNotFoundException
import com.github.wwkarev.sharktask.api.eventdispatchoption.EventDispatchOption
import com.github.wwkarev.sharktask.api.task.MutableTask as API_MutableTask
import com.github.wwkarev.sharktask.api.user.User as API_User
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.exception.MethodNotImplementedException
import com.github.wwkarev.sharktask.core.field.FieldType
import com.github.wwkarev.sharktask.core.models.AttachmentModel
import com.github.wwkarev.sharktask.core.models.FieldModel
import com.github.wwkarev.sharktask.core.models.FieldValueModel
import com.github.wwkarev.sharktask.core.models.TaskModel
import com.github.wwkarev.sharktask.core.user.User
import groovy.json.JsonBuilder
import groovy.sql.Sql

final class MutableTask extends Task implements API_MutableTask {
    MutableTask(Sql sql, Config models, TaskModel taskModel) {
        super(sql, models, taskModel)
    }

    @Override
    void updateFieldValue(Long fieldId, value, API_User user, EventDispatchOption eventDispatchOption = EventDispatchOption.DO_NOT_DISPATCH) {
        FieldModel fieldModel = Select.get(sql, config.getFieldModel(), 'id', fieldId)
        try {
            _updateFieldValue(fieldModel, value, user, eventDispatchOption)
        } catch(RecordNotFoundException e) {
            insertFieldValue(fieldModel, value, user, eventDispatchOption)
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
        File file = new File(filePath)
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException(filePath)
        }

        AttachmentModel fieldValueModel = config.getAttachmentModel()
                .getDeclaredConstructor(Sql, String, String, Long)
                .newInstance(sql, filePath, attachmentName, id)
        .insert()
    }

    @Override
    void removeAttachment(Long id, API_User user, EventDispatchOption eventDispatchOption) {
        Select.get(sql, config.getAttachmentModel(), 'id', id).delete()
    }

    @Override
    void addComment(String body, API_User user, EventDispatchOption eventDispatchOption) {
        throw new MethodNotImplementedException()
    }

    @Override
    void removeComment(Long aLong, API_User user, EventDispatchOption eventDispatchOption) {
        throw new MethodNotImplementedException()
    }

    @Override
    void delete(API_User user) {
        throw new MethodNotImplementedException()
    }

    private _updateFieldValue(FieldModel fieldModel, value, API_User user, EventDispatchOption eventDispatchOption) {
        FieldValueModel fieldValueModel = Select.get(
                sql, config.getFieldValueModel(), new Q([taskId: taskModel.id, fieldId: fieldModel.id])
        )
        setValueToFieldValueModel(fieldValueModel, value, FieldType.fromString(fieldModel.type))
        fieldValueModel.update()
    }

    private insertFieldValue(FieldModel fieldModel, value, API_User user, EventDispatchOption eventDispatchOption) {
        FieldValueModel fieldValueModel = config.getFieldValueModel()
                .getDeclaredConstructor(Sql, Long, Long, String, Double, Date)
                .newInstance(sql, taskModel.id, fieldModel.id, null, null, null)
        setValueToFieldValueModel(fieldValueModel, value, FieldType.fromString(fieldModel.type))
        fieldValueModel.insert()
    }

    private void setValueToFieldValueModel(FieldValueModel fieldValueModel, Object value, FieldType fieldType) {
        switch (fieldType) {
            case FieldType.TEXT:
                fieldValueModel.textValue = value
                break
            case FieldType.NUMBER:
                fieldValueModel.numberValue = value
                break
            case FieldType.DATE:
                fieldValueModel.dateValue = value
                break
            case FieldType.USER:
                fieldValueModel.textValue = ((User)value)?.getKey()
                break
            case FieldType.JSON:
                fieldValueModel.textValue = new JsonBuilder(value).toString()
                break
        }
    }
}
