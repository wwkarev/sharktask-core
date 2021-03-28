package com.github.wwkarev.sharktask.core.project

import com.github.wwkarev.sharktask.api.params.Params
import com.github.wwkarev.sharktask.api.project.ProjectCreator as API_ProjectCreator
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.models.ProjectModel
import groovy.sql.Sql

trait ProjectCreator implements API_ProjectCreator {
    abstract Sql getSql()
    abstract Config getConfig()

    @Override
    Project create(Long id, String key, String name, Params params = null) {
        return _create(id, key, name, params)
    }

    @Override
    Project create(String key, String name, Params params = null) {
        return _create(null, key, name, params)
    }

    private Project _create(Long id, String key, String name, Params params) {
        ProjectModel projectModel = getConfig().getProjectModel()
                .getDeclaredConstructor(Sql, Long, String, String)
                .newInstance(getSql(), id, key, name)
                .insert()
        return new Project(projectModel)
    }
}
