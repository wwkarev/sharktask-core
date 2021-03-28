package com.github.wwkarev.sharktask.core.field

import com.github.wwkarev.sharktask.api.status.StatusNotFoundException
import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.status.Status
import com.github.wwkarev.sharktask.core.status.StatusManager
import com.github.wwkarev.sharktask.core.util.SharkTaskConfig
import com.github.wwkarev.sharktask.core.util.TestTableManager
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class StatusManagerSpec extends Specification {
    @Shared
    DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
    @Shared
    Sql sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)
    @Shared
    Config config = new SharkTaskConfig()
    @Shared
    StatusManager statusManager = StatusManager.newInstance(sql, config)

    def setupSpec() {
        TestTableManager.createTables(sql, config)
    }

    def "create. getById. getAtById"() {
        when:
        Status createdStatus  = statusManager.create(UUID.randomUUID().toString())
        Status statusGetById = statusManager.getById(createdStatus.getId())
        Status statusAtGetById = statusManager.getAtById(createdStatus.getId())

        then:
        TestUtils.compare(createdStatus, statusGetById)
        TestUtils.compare(createdStatus, statusAtGetById)
    }

    def "create with id. getById. getAtById"() {
        given:
        Long STATUS_ID = 12345
        when:
        Status createdStatus  = statusManager.create(STATUS_ID, UUID.randomUUID().toString())
        Status statusGetById = statusManager.getById(createdStatus.getId())
        Status statusAtGetById = statusManager.getAtById(createdStatus.getId())

        then:
        TestUtils.compare(createdStatus, statusGetById)
        TestUtils.compare(createdStatus, statusAtGetById)
    }

    def "getAtById. Null"() {
        expect:
        statusManager.getAtById(-1) == null
    }

    def "getById. Exception"() {
        when:
        statusManager.getById(-1)
        then:
        thrown StatusNotFoundException
    }

    def cleanup() {
        TestTableManager.truncate(sql)
    }

    def cleanupSpec() {
        TestTableManager.dropTables(sql)
        sql.close()
    }
}
