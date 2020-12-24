package com.github.wwkarev.sharktask.core.tasktype

import com.github.wwkarev.sharktask.api.tasktype.TaskType
import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.config.DBTableNames
import com.github.wwkarev.sharktask.core.config.SharkTaskTableNames
import com.github.wwkarev.sharktask.core.tasktype.TaskTypeAccessor as SHT_TaskTypeManager
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class TaskTypeAccessorTest extends Specification {
    @Shared
    Sql sql
    @Shared
    DBTableNames dbTableNames
    @Shared
    TaskTypeAccessor taskTypeAccessor

    def setup() {
        DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
        sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)

        dbTableNames = new SharkTaskTableNames()

        taskTypeAccessor = TaskTypeAccessor.getInstance(sql, dbTableNames)
    }

    def "test TaskTypeManager. Create taskType."() {
        when:
        TaskType createdTaskType  = TestUtils.createTaskType(sql, dbTableNames)
        TaskType taskTypeById = taskTypeAccessor.getById(createdTaskType.getId())

        then:
        TestUtils.compare(createdTaskType, taskTypeById)

        cleanup:
        TestUtils.clearDB(sql, dbTableNames)
    }

    def cleanup() {
        sql.close()
    }
}
