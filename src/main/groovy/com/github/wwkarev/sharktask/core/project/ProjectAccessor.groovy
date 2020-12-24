package com.github.wwkarev.sharktask.core.project

import com.github.wwkarev.sharktask.api.project.ProjectAccessor as API_ProjectAccessor
import com.github.wwkarev.sharktask.core.config.DBTableNames
import groovy.sql.Sql

final class ProjectAccessor implements API_ProjectAccessor {
    private Sql sql
    private DBTableNames dbTableNames

    private ProjectAccessor(Sql sql, DBTableNames dbTableNames) {
        this.sql = sql
        this.dbTableNames = dbTableNames
    }

    static ProjectAccessor getInstance(Sql sql, DBTableNames dbTable) {
        return new ProjectAccessor(sql, dbTable)
    }

    @Override
    Project getById(Long id) {
        def rowResult = sql.rows(
                "select * from ${dbTableNames.getProjectTable()} where id = ?".toString(),
                [id]
        )[0]
        return new Project(rowResult.id, rowResult.key, rowResult.name)
    }

    @Override
    Project getByKey(String key) {
        def rowResult = sql.rows(
                "select * from ${dbTableNames.getProjectTable()} where key = ?".toString(),
                [key]
        )[0]
        return new Project(rowResult.id, rowResult.key, rowResult.name)
    }
}
