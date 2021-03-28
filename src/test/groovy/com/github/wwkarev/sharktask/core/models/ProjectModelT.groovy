package com.github.wwkarev.sharktask.core.models

import com.github.wwkarev.gorm.config.Config
import groovy.sql.Sql

class ProjectModelT extends ProjectModel {
    ProjectModelT(Sql sql, Long id, String key, String name) {
        super(sql, id, key, name)
    }

    @Override
    String getTableName() {
        return 'project'
    }
}
