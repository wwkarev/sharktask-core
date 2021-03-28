package com.github.wwkarev.sharktask.core.user

import com.github.wwkarev.sharktask.api.user.UserManager as API_UserManager
import com.github.wwkarev.sharktask.core.config.Config
import groovy.sql.Sql

final class UserManager implements API_UserManager, UserAccessor, UserCreator {
    private Sql sql
    private Config config

    private UserManager(Sql sql, Config config) {
        this.sql = sql
        this.config = config
    }

    static UserManager newInstance(Sql sql, Config config) {
        return new UserManager(sql, config)
    }

    @Override
    Sql getSql() {
        return sql
    }

    @Override
    Config getConfig() {
        return config
    }
}
