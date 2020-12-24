package com.github.wwkarev.sharktask.core.task

import com.github.wwkarev.sharktask.api.eventdispatchoption.EventDispatchOption
import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.config.DBTableNames
import com.github.wwkarev.sharktask.core.config.SharkTaskTableNames
import com.github.wwkarev.sharktask.core.field.Field
import com.github.wwkarev.sharktask.core.field.FieldCreator
import com.github.wwkarev.sharktask.core.user.User
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.postgresql.util.PSQLException
import spock.lang.Shared
import spock.lang.Specification

class TaskTest extends Specification {
    @Shared
    Sql sql
    @Shared
    DBTableNames dbTableNames

    def setup() {
        DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
        sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)

        dbTableNames = new SharkTaskTableNames()
    }

    def "test Task. get update value. TEXT"() {
        when:
        User user = TestUtils.createUser(sql, dbTableNames)
        Field field = TestUtils.createField(sql, dbTableNames, Field.ValueType.TEXT)
        Task task = TestUtils.createTask(sql, dbTableNames)

        String destValue = "QWERTY"
        task.updateValue(field.id, 'XXX', user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        String resultValue = task.getFieldValue(field.id)
        List<GroovyRowResult> fieldValueRowResults =
                sql.rows((
                        "select * from ${dbTableNames.getFieldValueTable()} " +
                                "where task_id = ${task.getId()} and field_id = ${field.getId()}"
                ).toString())
        then:
        assert destValue == resultValue
        assert fieldValueRowResults.size() == 1
        cleanup:
        TestUtils.clearDB(sql, dbTableNames)
    }

    def "test Task. get update value. NUMBER"() {
        when:
        User user = TestUtils.createUser(sql, dbTableNames)
        Field field = TestUtils.createField(sql, dbTableNames, Field.ValueType.NUMBER)
        Task task = TestUtils.createTask(sql, dbTableNames)

        Double destValue = 10
        task.updateValue(field.id, 1, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        Double resultValue = task.getFieldValue(field.id)
        List<GroovyRowResult> fieldValueRowResults =
                sql.rows((
                        "select * from ${dbTableNames.getFieldValueTable()} " +
                                "where task_id = ${task.getId()} and field_id = ${field.getId()}"
                ).toString())
        then:
        assert destValue == resultValue
        assert fieldValueRowResults.size() == 1
        cleanup:
        TestUtils.clearDB(sql, dbTableNames)
    }

    def "test Task. get update value. DATE"() {
        when:
        User user = TestUtils.createUser(sql, dbTableNames)
        Field field = TestUtils.createField(sql, dbTableNames, Field.ValueType.DATE)
        Task task = TestUtils.createTask(sql, dbTableNames)

        Date destValue = new Date()
        task.updateValue(field.id, new Date(), user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        Date resultValue = task.getFieldValue(field.id)
        List<GroovyRowResult> fieldValueRowResults =
                sql.rows((
                        "select * from ${dbTableNames.getFieldValueTable()} " +
                                "where task_id = ${task.getId()} and field_id = ${field.getId()}"
                ).toString())
        then:
        assert destValue.getTime() == resultValue.getTime()
        assert fieldValueRowResults.size() == 1
        cleanup:
        TestUtils.clearDB(sql, dbTableNames)
    }

    def "test Task. update value illegal type"() {
        when:
        User user = TestUtils.createUser(sql, dbTableNames)
        Field field = TestUtils.createField(sql, dbTableNames, Field.ValueType.NUMBER)
        Task task = TestUtils.createTask(sql, dbTableNames)

        task.updateValue(field.id, 'XXX', user, EventDispatchOption.DO_NOT_DISPATCH)
        then:
        thrown PSQLException
        cleanup:
        TestUtils.clearDB(sql, dbTableNames)
    }


    def cleanup() {
        sql.close()
    }
}
