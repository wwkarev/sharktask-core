package com.github.wwkarev.sharktask.core.models

import com.github.wwkarev.gorm.config.ColumnConfig
import com.github.wwkarev.gorm.config.Config
import com.github.wwkarev.gorm.config.ForeignKey
import groovy.sql.Sql

abstract class TaskModel extends SharkTaskModel {
    Long projectId
    Long taskTypeId
    Long statusId
    Date created

    TaskModel(Sql sql, Long projectId, Long taskTypeId, Long statusId, Date created) {
        super(sql)
        this.projectId = projectId
        this.taskTypeId = taskTypeId
        this.statusId = statusId
        this.created = created
    }

    abstract Class<? extends ProjectModel> getProjectModelClass()
    abstract Class<? extends TaskTypeModel> getTaskTypeModelClass()
    abstract Class<? extends StatusModel> getStatusModelClass()

    @Override
    Config config() {
        return new Config(
                tableName: getTableName(),
                columns: [
                        projectId: new ColumnConfig(foreignKey: new ForeignKey(dest: getProjectModelClass(), getterName: 'getProjectModel')),
                        taskTypeId: new ColumnConfig(foreignKey: new ForeignKey(dest: getTaskTypeModelClass(), getterName: 'getTaskTypeModel')),
                        statusId: new ColumnConfig(foreignKey: new ForeignKey(dest: getStatusModelClass(), getterName: 'getStatusModel'))
                ]
        )
    }

    ProjectModel getProjectModel() {}
    TaskTypeModel getTaskTypeModel() {}
    StatusModel getStatusModel() {}
}
