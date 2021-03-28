package com.github.wwkarev.sharktask.core.project

import com.github.wwkarev.gorm.Select
import com.github.wwkarev.gorm.exceptions.RecordNotFoundException
import com.github.wwkarev.sharktask.api.project.ProjectAccessor as API_ProjectAccessor
import com.github.wwkarev.sharktask.api.project.ProjectNotFoundException
import com.github.wwkarev.sharktask.core.config.Config
import groovy.sql.Sql

trait ProjectAccessor implements API_ProjectAccessor {
    abstract Sql getSql()
    abstract Config getConfig()

    @Override
    Project getById(Long id) {
        try {
            return new Project(Select.get(getSql(), getConfig().getProjectModel(), 'id', id))
        } catch(RecordNotFoundException e) {
            throw new ProjectNotFoundException()
        }
    }

    @Override
    Project getByKey(String key) {
        try {
            return new Project(Select.get(getSql(), getConfig().getProjectModel(), 'key', key))
        } catch(RecordNotFoundException e) {
            throw new ProjectNotFoundException()
        }
    }

    @Override
    Project getAtById(Long id) {
        try {
            return new Project(Select.get(getSql(), getConfig().getProjectModel(), 'id', id))
        } catch(RecordNotFoundException e) {
            return null
        }
    }

    @Override
    Project getAtByKey(String key) {
        try {
            return new Project(Select.get(getSql(), getConfig().getProjectModel(), 'key', key))
        } catch(RecordNotFoundException e) {
            return null
        }
    }
}
