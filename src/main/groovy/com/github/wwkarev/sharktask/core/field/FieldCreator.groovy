package com.github.wwkarev.sharktask.core.field

import com.github.wwkarev.sharktask.api.field.FieldCreator as API_FieldCreator
import com.github.wwkarev.sharktask.api.params.ParamNotFoundException
import com.github.wwkarev.sharktask.api.params.Params
import com.github.wwkarev.sharktask.api.params.RequiredInParamException
import com.github.wwkarev.sharktask.core.config.Config

import com.github.wwkarev.sharktask.core.models.FieldModel
import com.github.wwkarev.sharktask.core.params.ParamId
import groovy.sql.Sql

trait FieldCreator implements API_FieldCreator {
    abstract Sql getSql()
    abstract Config getConfig()

    @Override
    Field create(Long id,String name, Params params) {
        try {
            _create(id, name, params)
        } catch (ParamNotFoundException e) {
            throw new RequiredInParamException()
        }
    }

    @Override
    Field create(String name, Params params) {
        try {
            _create(null, name, params)
        } catch (ParamNotFoundException e) {
            throw new RequiredInParamException()
        }
    }

    private _create(Long id, String name, Params params) {
        FieldType type = params.get(ParamId.FIELD_TYPE)
        FieldModel fieldModel = getConfig().getFieldModel()
                .getDeclaredConstructor(Sql, Long, String, String)
                .newInstance(getSql(), id, name, type.toString())
                .insert()
        return new Field(fieldModel)
    }
}
