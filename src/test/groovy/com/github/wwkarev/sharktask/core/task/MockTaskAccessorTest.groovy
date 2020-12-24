package com.github.wwkarev.sharktask.core.task

import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.config.DBTableNames
import com.github.wwkarev.sharktask.core.config.SharkTaskTableNames
import com.github.wwkarev.sharktask.core.project.Project
import com.github.wwkarev.sharktask.core.status.Status
import com.github.wwkarev.sharktask.core.tasktype.TaskType
import com.github.wwkarev.sharktask.core.user.User
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class MockTaskAccessorTest extends Specification {
    @Shared
    Sql sql
    @Shared
    DBTableNames dbTableNames

    def setup() {
        DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
        sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)

        dbTableNames = new SharkTaskTableNames()
    }

    def "test MockTaskManager."() {
        when:
        MutableTask createdTask = TestUtils.createTask(sql, dbTableNames)
        MutableTask taskById = TaskAccessor.getInstance(sql, dbTableNames).getTaskById(createdTask.getId())

        then:
        assert createdTask.getId() == taskById.getId()
        TestUtils.compare(createdTask.getProject(), taskById.getProject())
        TestUtils.compare(createdTask.getTaskType(), taskById.getTaskType())
        TestUtils.compare(createdTask.getStatus(), taskById.getStatus())
        TestUtils.compare(createdTask.getCreator(), taskById.getCreator())
        TestUtils.compare(createdTask.getAssignee(), taskById.getAssignee())
        assert createdTask.getSummary() == taskById.getSummary()
        assert createdTask.getCreatedDate().toTimestamp() == taskById.getCreatedDate().toTimestamp()

        cleanup:
        TestUtils.clearDB(sql, dbTableNames)
    }

    def cleanup() {
        sql.close()
    }
}
