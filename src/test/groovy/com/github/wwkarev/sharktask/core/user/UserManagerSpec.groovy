package com.github.wwkarev.sharktask.core.field


import com.github.wwkarev.sharktask.api.user.UserNotFoundException
import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.user.User
import com.github.wwkarev.sharktask.core.user.UserManager
import com.github.wwkarev.sharktask.core.util.SharkTaskConfig
import com.github.wwkarev.sharktask.core.util.TestTableManager
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class UserManagerSpec extends Specification {
    @Shared
    DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
    @Shared
    Sql sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)
    @Shared
    Config config = new SharkTaskConfig()
    @Shared
    UserManager manager = UserManager.newInstance(sql, config)

    def setupSpec() {
        TestTableManager.createTables(sql, config)
    }

    def "create. getById. getAtById"() {
        when:
        String userKey = UUID.randomUUID().toString()
        User createdUser  = manager.create(userKey, UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        User userGetById = manager.getById(createdUser.getId())
        User userGetAtById = manager.getAtById(createdUser.getId())

        then:
        TestUtils.compare(createdUser, userGetById)
        TestUtils.compare(createdUser, userGetAtById)
    }

    def "create. getAtByKey. getAtAtByKey"() {
        when:
        String userKey = UUID.randomUUID().toString()
        User createdProject  = manager.create(userKey, UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        User userGetByKey = manager.getByKey(userKey)
        User userGetAtByKey = manager.getByKey(userKey)

        then:
        TestUtils.compare(createdProject, userGetByKey)
        TestUtils.compare(createdProject, userGetAtByKey)
    }

    def "getAtById. Null"() {
        expect:
        manager.getAtById(-1) == null
    }

    def "getById. Exception"() {
        when:
        manager.getById(-1)
        then:
        thrown UserNotFoundException
    }

    def "getAtByKey. Null"() {
        expect:
        manager.getAtByKey('-1') == null
    }

    def "getByKey. Exception"() {
        when:
        manager.getByKey('-1')
        then:
        thrown UserNotFoundException
    }

    def cleanup() {
        TestTableManager.truncate(sql)
    }

    def cleanupSpec() {
        TestTableManager.dropTables(sql)
        sql.close()
    }
}
