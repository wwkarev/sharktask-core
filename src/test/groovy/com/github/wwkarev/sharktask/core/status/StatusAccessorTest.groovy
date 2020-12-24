package com.github.wwkarev.sharktask.core.status

import com.github.wwkarev.sharktask.api.status.Status
import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.config.DBTableNames
import com.github.wwkarev.sharktask.core.config.SharkTaskTableNames
import com.github.wwkarev.sharktask.core.status.StatusAccessor as SHT_StatusManager
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class StatusAccessorTest extends Specification {
    @Shared
    Sql sql
    @Shared
    DBTableNames dbTableNames
    @Shared
    StatusAccessor statusAccessor

    def setup() {
        DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
        sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)

        dbTableNames = new SharkTaskTableNames()

        statusAccessor = StatusAccessor.getInstance(sql, dbTableNames)
    }

    def "test StatusManager"() {
        when:
        Status statusField  = TestUtils.createStatus(sql, dbTableNames)
        Status statusById = statusAccessor.getById(statusField.getId())

        then:
        TestUtils.compare(statusField, statusById)

        cleanup:
        TestUtils.clearDB(sql, dbTableNames)
    }

    def cleanup() {
        sql.close()
    }
}
