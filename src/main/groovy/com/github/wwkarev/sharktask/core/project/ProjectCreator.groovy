package com.github.wwkarev.sharktask.core.project

import com.github.wwkarev.sharktask.api.project.ProjectCreator as API_ProjectCreator
import com.github.wwkarev.sharktask.core.config.DBTableNames
import groovy.sql.Sql

class ProjectCreator implements API_ProjectCreator {
    private Sql sql
    private DBTableNames dbTableNames
    private String key
    private String name

    private ProjectCreator(Sql sql, DBTableNames dbTableNames, String key, String name) {
        this.sql = sql
        this.dbTableNames = dbTableNames
        this.key = key
        this.name = name
    }

    static ProjectCreator getInstance(Sql sql, DBTableNames dbTableNames, String key, String name) {
        return new ProjectCreator(sql, dbTableNames, key, name)
    }

    @Override
    Project create() {
        Long id = sql.executeInsert(
                "insert into ${dbTableNames.getProjectTable()} (key, name) VALUES(?, ?)".toString(),
                [key, name]
        )[0][0]
        return new Project(id, key, name)
    }
}
