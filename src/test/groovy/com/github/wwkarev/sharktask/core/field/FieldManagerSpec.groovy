package com.github.wwkarev.sharktask.core.field

import com.github.wwkarev.sharktask.api.field.FieldNotFoundException
import com.github.wwkarev.sharktask.api.params.RequiredInParamException
import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.config.Config

import com.github.wwkarev.sharktask.core.params.ParamId
import com.github.wwkarev.sharktask.core.params.Params
import com.github.wwkarev.sharktask.core.util.SharkTaskConfig
import com.github.wwkarev.sharktask.core.util.TestTableManager
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification


class FieldManagerSpec extends Specification {
    @Shared
    DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
    @Shared
    Sql sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)
    @Shared
    Config config = new SharkTaskConfig()
    @Shared
    FieldManager fieldManager = FieldManager.newInstance(sql, config)

    def setupSpec() {
        TestTableManager.createTables(sql, config)
    }

    def "create. getById. getAtById."() {
        given:
        Params params = new Params()
        params.add(ParamId.FIELD_TYPE, FieldType.TEXT)
        when:
        Field createdField  = fieldManager.create(UUID.randomUUID().toString(), params)
        Field fieldGetById = fieldManager.getById(createdField.getId())
        Field fieldGetAtById = fieldManager.getAtById(createdField.getId())

        then:
        TestUtils.compare(createdField, fieldGetById)
        TestUtils.compare(createdField, fieldGetAtById)
    }

    def "create with id. getById. getAtById."() {
        given:
        Long FIELD_ID = 1234
        Params params = new Params()
        params.add(ParamId.FIELD_TYPE, FieldType.TEXT)
        when:
        Field createdField  = fieldManager.create(FIELD_ID, UUID.randomUUID().toString(), params)
        Field fieldGetById = fieldManager.getById(FIELD_ID)
        Field fieldGetAtById = fieldManager.getAtById(FIELD_ID)

        then:
        TestUtils.compare(createdField, fieldGetById)
        TestUtils.compare(createdField, fieldGetAtById)
    }

    def "create. Field type not provided."() {
        given:
        Params params = new Params()
        when:
        fieldManager.create(UUID.randomUUID().toString(), params)
        then:
        thrown RequiredInParamException
    }

    def "getAtById. Null"() {
        expect:
        fieldManager.getAtById(-1) == null
    }

    def "getById. Exception"() {
        when:
        fieldManager.getById(-1)
        then:
        thrown FieldNotFoundException
    }

    def cleanup() {
        TestTableManager.truncate(sql)
    }

    def cleanupSpec() {
        TestTableManager.dropTables(sql)
        sql.close()
    }
}
