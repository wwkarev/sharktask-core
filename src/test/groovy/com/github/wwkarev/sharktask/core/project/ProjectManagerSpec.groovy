package com.github.wwkarev.sharktask.core.field

import com.github.wwkarev.sharktask.api.project.ProjectNotFoundException
import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.project.Project
import com.github.wwkarev.sharktask.core.project.ProjectManager
import com.github.wwkarev.sharktask.core.util.SharkTaskConfig
import com.github.wwkarev.sharktask.core.util.TestTableManager
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class ProjectManagerSpec extends Specification {
    @Shared
    DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
    @Shared
    Sql sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)
    @Shared
    Config config = new SharkTaskConfig()
    @Shared
    ProjectManager projectManager = ProjectManager.newInstance(sql, config)

    def setupSpec() {
        TestTableManager.createTables(sql, config)
    }

    def "create. getById. getAtById. getByKey. getAtByKey"() {
        given:
        String projectKey = UUID.randomUUID().toString()
        when:
        Project createdProject  = projectManager.create(projectKey, UUID.randomUUID().toString())
        Project projectGetById = projectManager.getById(createdProject.getId())
        Project projectGetAtById = projectManager.getAtById(createdProject.getId())
        Project projectGetByKey = projectManager.getByKey(projectKey)
        Project projectGetAtByKey = projectManager.getAtByKey(projectKey)

        then:
        TestUtils.compare(createdProject, projectGetById)
        TestUtils.compare(createdProject, projectGetAtById)
        TestUtils.compare(createdProject, projectGetByKey)
        TestUtils.compare(createdProject, projectGetAtByKey)
    }

    def "create with id. getById. getByKey"() {
        given:
        Long PROJECT_ID = 12345
        String projectKey = UUID.randomUUID().toString()
        when:
        Project createdProject  = projectManager.create(PROJECT_ID, projectKey, UUID.randomUUID().toString())
        Project projectGetById = projectManager.getById(createdProject.getId())
        Project projectGetByKey = projectManager.getByKey(projectKey)

        then:
        TestUtils.compare(createdProject, projectGetById)
        TestUtils.compare(createdProject, projectGetByKey)
    }

    def "getById. Null"() {
        expect:
        projectManager.getAtById(-1) == null
    }

    def "getByKey. Null"() {
        expect:
        projectManager.getAtByKey("-1") == null
    }

    def "getById. Exception"() {
        when:
        projectManager.getById(-1)
        then:
        thrown ProjectNotFoundException
    }

    def "getByKey. Exception"() {
        when:
        projectManager.getByKey("-1")
        then:
        thrown ProjectNotFoundException
    }

    def cleanup() {
        TestTableManager.truncate(sql)
    }

    def cleanupSpec() {
        TestTableManager.dropTables(sql)
        sql.close()
    }
}
