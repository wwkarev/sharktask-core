package com.github.wwkarev.sharktask.core.task

import com.github.wwkarev.sharktask.api.params.ParamNotFoundException
import com.github.wwkarev.sharktask.api.params.Params
import com.github.wwkarev.sharktask.api.params.RequiredInParamException
import com.github.wwkarev.sharktask.api.task.TaskCreator as API_TaskCreator
import com.github.wwkarev.sharktask.core.config.Config

import com.github.wwkarev.sharktask.core.models.TaskModel
import com.github.wwkarev.sharktask.core.params.ParamId
import groovy.sql.Sql

trait TaskCreator implements API_TaskCreator {
    abstract Sql getSql()
    abstract Config getConfig()

    @Override
    MutableTask create(Long projectId, Long taskTypeId, Params params) {
        try {
            _create(projectId, taskTypeId, params)
        } catch(ParamNotFoundException e) {
            String name
            switch (e.paramId) {
                case ParamId.SUMMARY:
                    name = config.getSummaryFieldName()
                    break
                case ParamId.STATUS:
                    name = config.getStatusFieldName()
                    break
                case ParamId.CREATOR:
                    name = config.getCreatorFieldName()
                    break
            }
            throw new RequiredInParamException(paramId: e.paramId, paramName: name)
        }
    }

    MutableTask _create(Long projectId, Long taskTypeId, Params params) {
        String summary = params.get(ParamId.SUMMARY)
        Long statusId = params.get(ParamId.STATUS)
        String creatorKey = params.get(ParamId.CREATOR)
        TaskModel taskModel = getConfig()
                .getTaskModel()
                .getDeclaredConstructor(Sql, Long, Long, Long, Date)
                .newInstance(getSql(), projectId, taskTypeId, statusId, new Date())
                .insert()

        getConfig()
                .getFieldValueModel()
                .getDeclaredConstructor(Sql, Long, Long, String, Double, Date)
                .newInstance(getSql(), taskModel.id, config.getSummaryFieldId(), summary, null, null)
                .insert()

        getConfig()
                .getFieldValueModel()
                .getDeclaredConstructor(Sql, Long, Long, String, Double, Date)
                .newInstance(getSql(), taskModel.id, config.getCreatorFieldId(), creatorKey, null, null)
                .insert()

        return new MutableTask(sql, config, taskModel)
    }
}
