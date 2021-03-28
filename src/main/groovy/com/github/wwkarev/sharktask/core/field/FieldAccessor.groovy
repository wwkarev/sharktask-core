package com.github.wwkarev.sharktask.core.field

import com.github.wwkarev.gorm.Select
import com.github.wwkarev.gorm.exceptions.RecordNotFoundException
import com.github.wwkarev.sharktask.api.field.FieldAccessor as API_FieldAccessor
import com.github.wwkarev.sharktask.api.field.FieldNotFoundException
import com.github.wwkarev.sharktask.core.config.Config
import groovy.sql.Sql

trait FieldAccessor implements API_FieldAccessor {
    abstract Sql getSql()
    abstract Config getConfig()

    @Override
    Field getById(Long id) {
        try {
            return new Field(Select.get(getSql(), getConfig().getFieldModel(), 'id', id))
        } catch (RecordNotFoundException e) {
            throw new FieldNotFoundException()
        }
    }

    @Override
    Field getAtById(Long id) {
        try {
            return new Field(Select.get(getSql(), getConfig().getFieldModel(), 'id', id))
        } catch (RecordNotFoundException e) {
            return null
        }
    }
}
