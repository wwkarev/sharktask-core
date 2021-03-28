package com.github.wwkarev.sharktask.core.status

import com.github.wwkarev.gorm.Select
import com.github.wwkarev.gorm.exceptions.RecordNotFoundException
import com.github.wwkarev.sharktask.api.status.StatusAccessor as API_StatusAccessor
import com.github.wwkarev.sharktask.api.status.StatusNotFoundException
import com.github.wwkarev.sharktask.core.config.Config
import groovy.sql.Sql

trait StatusAccessor implements API_StatusAccessor {
    abstract Sql getSql()
    abstract Config getConfig()

    @Override
    Status getById(Long id) {
        try {
            return new Status(Select.get(getSql(), getConfig().getStatusModel(), 'id', id))
        } catch(RecordNotFoundException e) {
            throw new StatusNotFoundException()
        }
    }

    @Override
    Status getAtById(Long id) {
        try {
            return new Status(Select.get(getSql(), getConfig().getStatusModel(), 'id', id))
        } catch(RecordNotFoundException e) {
            return null
        }
    }
}
