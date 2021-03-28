package com.github.wwkarev.sharktask.core.task

import com.github.wwkarev.sharktask.api.params.RequiredInParamException
import com.github.wwkarev.sharktask.api.task.TaskNotFoundException
import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.config.Config

import com.github.wwkarev.sharktask.core.params.ParamId
import com.github.wwkarev.sharktask.core.params.Params
import com.github.wwkarev.sharktask.core.project.Project
import com.github.wwkarev.sharktask.core.status.Status
import com.github.wwkarev.sharktask.core.tasktype.TaskType
import com.github.wwkarev.sharktask.core.user.User
import com.github.wwkarev.sharktask.core.util.SharkTaskConfig
import com.github.wwkarev.sharktask.core.util.TestTableManager
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class TaskManagerSpec extends Specification {
    @Shared
    DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
    @Shared
    Sql sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)
    @Shared
    Config config = new SharkTaskConfig()
    @Shared
    TaskManager manager = TaskManager.newInstance(sql, config)

    def setupSpec() {
        TestTableManager.createTables(sql, config)
    }

    def "create. getById. getAtById"() {
        when:
        MutableTask createdTask = TestUtils.createTask(sql, config)
        MutableTask taskGetById = manager.getById(createdTask.getId())
        MutableTask taskGetAtById = manager.getById(createdTask.getId())

        then:
        TestUtils.compare(createdTask.getProject(), taskGetById.getProject())
        TestUtils.compare(createdTask.getStatus(), taskGetById.getStatus())
        TestUtils.compare(createdTask.getCreator(), taskGetById.getCreator())
        TestUtils.compare(createdTask.getTaskType(), taskGetById.getTaskType())
        createdTask.getId() == taskGetById.getId()
        createdTask.getSummary() == taskGetById.getSummary()
        createdTask.getCreatedDate().toTimestamp() == taskGetById.getCreatedDate().toTimestamp()

        TestUtils.compare(createdTask.getProject(), taskGetAtById.getProject())
        TestUtils.compare(createdTask.getStatus(), taskGetAtById.getStatus())
        TestUtils.compare(createdTask.getCreator(), taskGetAtById.getCreator())
        TestUtils.compare(createdTask.getTaskType(), taskGetAtById.getTaskType())
        createdTask.getId() == taskGetAtById.getId()
        createdTask.getSummary() == taskGetAtById.getSummary()
        createdTask.getCreatedDate().toTimestamp() == taskGetAtById.getCreatedDate().toTimestamp()
    }

    def "getAtById. Null"() {
        expect:
        manager.getAtById(-1) == null
    }

    def "getById. Exception"() {
        when:
        manager.getById(-1)
        then:
        thrown TaskNotFoundException
    }

    def "create. STATUS not provided"() {
        given:
        Project project = TestUtils.createProject(sql, config)
        TaskType taskType = TestUtils.createTaskType(sql, config)
        User creator = TestUtils.createUser(sql, config)

        Params params = new Params()
        params.add(ParamId.CREATOR, creator.getId())
        params.add(ParamId.SUMMARY, UUID.randomUUID().toString())

        when:
        TaskManager
                .newInstance(sql, config)
                .create(project.getId(), taskType.getId(), params)

        then:
        thrown RequiredInParamException
    }

    def "create. CREATOR not provided"() {
        given:
        Project project = TestUtils.createProject(sql, config)
        TaskType taskType = TestUtils.createTaskType(sql, config)
        Status status = TestUtils.createStatus(sql, config)

        Params params = new Params()
        params.add(ParamId.STATUS, status.getId())
        params.add(ParamId.SUMMARY, UUID.randomUUID().toString())

        when:
        TaskManager
                .newInstance(sql, config)
                .create(project.getId(), taskType.getId(), params)

        then:
        thrown RequiredInParamException
    }

    def "create. SUMMARY not provided"() {
        given:
        Project project = TestUtils.createProject(sql, config)
        TaskType taskType = TestUtils.createTaskType(sql, config)

        User creator = TestUtils.createUser(sql, config)
        Status status = TestUtils.createStatus(sql, config)

        Params params = new Params()
        params.add(ParamId.STATUS, status.getId())
        params.add(ParamId.CREATOR, creator.getId())

        when:
        TaskManager
                .newInstance(sql, config)
                .create(project.getId(), taskType.getId(), params)

        then:
        thrown RequiredInParamException
    }

    def cleanup() {
        TestTableManager.truncate(sql)
    }

    def cleanupSpec() {
        TestTableManager.dropTables(sql)
        sql.close()
    }
}
