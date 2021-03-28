package com.github.wwkarev.sharktask.core.user

import com.github.wwkarev.gorm.Select
import com.github.wwkarev.gorm.exceptions.RecordNotFoundException
import com.github.wwkarev.sharktask.api.user.UserAccessor as API_UserAccessor
import com.github.wwkarev.sharktask.api.user.UserNotFoundException
import com.github.wwkarev.sharktask.core.config.Config
import groovy.sql.Sql

trait UserAccessor implements API_UserAccessor {
    abstract Sql getSql()
    abstract Config getConfig()

    @Override
    User getById(Long id) {
        try {
            return new User(Select.get(getSql(), getConfig().getUserModel(), 'id', id))
        } catch(RecordNotFoundException e) {
            throw new UserNotFoundException()
        }
    }

    @Override
    User getByKey(String key) {
        try {
            return new User(Select.get(getSql(), getConfig().getUserModel(), 'key', key))
        } catch(RecordNotFoundException e) {
            throw new UserNotFoundException()
        }
    }

    @Override
    User getAtById(Long id) {
        try {
            return new User(Select.get(getSql(), getConfig().getUserModel(), 'id', id))
        } catch(RecordNotFoundException e) {
            return null
        }
    }

    @Override
    User getAtByKey(String key) {
        try {
            return new User(Select.get(getSql(), getConfig().getUserModel(), 'key', key))
        } catch(RecordNotFoundException e) {
            return null
        }
    }
}
