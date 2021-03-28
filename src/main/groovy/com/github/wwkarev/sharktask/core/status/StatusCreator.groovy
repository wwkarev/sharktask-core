package com.github.wwkarev.sharktask.core.status

import com.github.wwkarev.sharktask.api.params.Params
import com.github.wwkarev.sharktask.api.status.StatusCreator as API_StatusCreator
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.models.StatusModel
import groovy.sql.Sql

trait StatusCreator implements API_StatusCreator {
    abstract Sql getSql()
    abstract Config getConfig()

    @Override
    Status create(Long id, String name, Params params = null) {
        return _create(id, name, params)
    }

    @Override
    Status create(String name, Params params = null) {
        return _create(null, name, params)
    }

    private Status _create(Long id, String name, Params params) {
        StatusModel statusModel = getConfig().getStatusModel()
                .getDeclaredConstructor(Sql, Long, String)
                .newInstance(getSql(), id, name)
                .insert()
        return new Status(statusModel)
    }
}
