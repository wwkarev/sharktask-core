package com.github.wwkarev.sharktask.core.project

import com.github.wwkarev.sharktask.api.project.ProjectManager as API_ProjectManager
import com.github.wwkarev.sharktask.core.config.Config
import groovy.sql.Sql

final class ProjectManager implements API_ProjectManager, ProjectAccessor, ProjectCreator {
    private Sql sql
    private Config config

    private ProjectManager(Sql sql, Config config) {
        this.sql = sql
        this.config = config
    }

    static ProjectManager newInstance(Sql sql, Config config) {
        return new ProjectManager(sql, config)
    }

    @Override
    Sql getSql() {
        return sql
    }

    @Override
    Config getConfig() {
        return config
    }
}
