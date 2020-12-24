package com.github.wwkarev.sharktask.core.user

import com.github.wwkarev.sharktask.api.user.User
import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.config.DBTableNames
import com.github.wwkarev.sharktask.core.config.SharkTaskTableNames
import com.github.wwkarev.sharktask.core.user.UserAccessor as SHT_UserManager
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class UserAccessorTest extends Specification {
    @Shared
    Sql sql
    @Shared
    DBTableNames dbTableNames
    @Shared
    UserAccessor userAccessor

    def setup() {
        DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
        sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)

        dbTableNames = new SharkTaskTableNames()

        userAccessor = UserAccessor.getInstance(sql, dbTableNames)
    }

    def "test ProjectManager. Create project."() {
        when:
        User createdUser = TestUtils.createUser(sql, dbTableNames)
        User userById = userAccessor.getById(createdUser.getId())
        User userByKey = userAccessor.getByKey(createdUser.getKey())

        then:
        TestUtils.compare(createdUser, userById)
        TestUtils.compare(createdUser, userByKey)

        cleanup:
        TestUtils.clearDB(sql, dbTableNames)
    }

    def cleanup() {
        sql.close()
    }
}
