package com.github.wwkarev.sharktask.core.field


import com.github.wwkarev.sharktask.api.tasktype.TaskTypeNotFoundException
import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.tasktype.TaskType
import com.github.wwkarev.sharktask.core.tasktype.TaskTypeManager
import com.github.wwkarev.sharktask.core.util.SharkTaskConfig
import com.github.wwkarev.sharktask.core.util.TestTableManager
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class TaskTypeManagerSpec extends Specification {
    @Shared
    DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
    @Shared
    Sql sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)
    @Shared
    Config config = new SharkTaskConfig()
    @Shared
    TaskTypeManager manager = TaskTypeManager.newInstance(sql, config)

    def setupSpec() {
        TestTableManager.createTables(sql, config)
    }

    def "create. getById. getAtById"() {
        when:
        TaskType createdTaskType  = manager.create(UUID.randomUUID().toString())
        TaskType taskTypeGetById = manager.getById(createdTaskType.getId())
        TaskType taskTypeGetAtById = manager.getAtById(createdTaskType.getId())

        then:
        TestUtils.compare(createdTaskType, taskTypeGetById)
        TestUtils.compare(createdTaskType, taskTypeGetAtById)
    }

    def "create with id. getById. getAtById"() {
        given:
        Long TASK_TYPE_ID = 12345
        when:
        TaskType createdTaskType  = manager.create(TASK_TYPE_ID, UUID.randomUUID().toString())
        TaskType taskTypeGetById = manager.getById(createdTaskType.getId())
        TaskType taskTypeGetAtById = manager.getAtById(createdTaskType.getId())

        then:
        TestUtils.compare(createdTaskType, taskTypeGetById)
        TestUtils.compare(createdTaskType, taskTypeGetAtById)
    }

    def "getAtById. Null"() {
        expect:
        manager.getAtById(-1) == null
    }

    def "getById. Exception"() {
        when:
        manager.getById(-1)
        then:
        thrown TaskTypeNotFoundException
    }

    def cleanup() {
        TestTableManager.truncate(sql)
    }

    def cleanupSpec() {
        TestTableManager.dropTables(sql)
        sql.close()
    }
}
