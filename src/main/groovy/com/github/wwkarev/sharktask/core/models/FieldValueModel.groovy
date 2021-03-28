package com.github.wwkarev.sharktask.core.models

import com.github.wwkarev.gorm.config.ColumnConfig
import com.github.wwkarev.gorm.config.Config
import com.github.wwkarev.gorm.config.ForeignKey
import groovy.sql.Sql

abstract class FieldValueModel extends SharkTaskModel {
    Long taskId
    Long fieldId
    String textValue
    Double numberValue
    Date dateValue

    FieldValueModel(Sql sql, Long taskId, Long fieldId, String textValue, Double numberValue, Date dateValue) {
        super(sql)
        this.taskId = taskId
        this.fieldId = fieldId
        this.textValue = textValue
        this.numberValue = numberValue
        this.dateValue = dateValue
    }

    abstract Class<? extends TaskModel> getTaskModelClass()
    abstract Class<? extends FieldModel> getFieldModelClass()

    @Override
    Config config() {
        return new Config(
                tableName: getTableName(),
                columns: [
                        taskId: new ColumnConfig(foreignKey: new ForeignKey(dest: getTaskModelClass(), getterName: 'getTaskModel')),
                        fieldId: new ColumnConfig(foreignKey: new ForeignKey(dest: getFieldModelClass(), getterName: 'getFieldModel'))
                ]
        )
    }

    TaskModel getTaskModel() {}
    FieldModel getFieldModel() {}
}
