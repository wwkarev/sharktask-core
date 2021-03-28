package com.github.wwkarev.sharktask.core.models

import com.github.wwkarev.gorm.config.ColumnConfig
import com.github.wwkarev.gorm.config.Config
import com.github.wwkarev.gorm.config.ForeignKey
import groovy.sql.Sql

abstract class AttachmentModel extends SharkTaskModel {
    String filePath
    String name
    Long taskId

    AttachmentModel(Sql sql, String filePath, String name, Long taskId) {
        super(sql)
        this.filePath = filePath
        this.name = name
        this.taskId = taskId
    }

    @Override
    Config config() {
        return new Config(
                tableName: getTableName(),
                columns: [
                        taskId: new ColumnConfig(foreignKey: new ForeignKey(dest: getTaskModelClass(), getterName: 'getTaskModel'))
                ]
        )
    }

    TaskModel getTaskModel() {}
    abstract Class<? extends TaskModel>  getTaskModelClass()
}
