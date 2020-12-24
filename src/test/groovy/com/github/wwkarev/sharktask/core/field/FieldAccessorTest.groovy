package com.github.wwkarev.sharktask.core.field

import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.config.DBTableNames
import com.github.wwkarev.sharktask.core.config.SharkTaskTableNames
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class FieldAccessorTest extends Specification {
    @Shared
    Sql sql
    @Shared
    DBTableNames dbTableNames
    @Shared
    FieldAccessor fieldAccessor

    def setup() {
        DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
        sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)

        dbTableNames = new SharkTaskTableNames()

        fieldAccessor = FieldAccessor.getInstance(sql, dbTableNames)
    }

    def "test FieldManager"() {
        when:
        Field createdField  = TestUtils.createField(sql, dbTableNames, Field.ValueType.TEXT)
        Field fieldById = fieldAccessor.getById(createdField.getId())

        then:
        TestUtils.compare(createdField, fieldById)

        cleanup:
        TestUtils.clearDB(sql, dbTableNames)
    }

    def cleanup() {
        sql.close()
    }
}
