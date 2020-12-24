package com.github.wwkarev.sharktask.core.project


import com.github.wwkarev.sharktask.api.project.Project
import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.project.ProjectAccessor as SHT_ProjectManager
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.config.DBTableNames
import com.github.wwkarev.sharktask.core.config.SharkTaskTableNames
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class ProjectAccessorTest extends Specification {
    @Shared
    Sql sql
    @Shared
    DBTableNames dbTableNames
    @Shared
    ProjectAccessor projectAccessor

    def setup() {
        DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
        sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)

        dbTableNames = new SharkTaskTableNames()

        projectAccessor = ProjectAccessor.getInstance(sql, dbTableNames)
    }

    def "test ProjectManager. Create project."() {
        when:
        Project createdProject = TestUtils.createProject(sql, dbTableNames)
        Project projectById = projectAccessor.getById(createdProject.getId())
        Project projectByKey = projectAccessor.getByKey(createdProject.getKey())

        then:
        TestUtils.compare(createdProject, projectById)
        TestUtils.compare(createdProject, projectByKey)

        cleanup:
        TestUtils.clearDB(sql, dbTableNames)
    }

    def cleanup() {
        sql.close()
    }
}
