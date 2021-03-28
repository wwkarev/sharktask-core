package com.github.wwkarev.sharktask.core.user

import com.github.wwkarev.sharktask.api.params.Params
import com.github.wwkarev.sharktask.api.user.UserCreator as API_UserCreator
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.models.UserModel
import groovy.sql.Sql

trait UserCreator implements API_UserCreator {
    abstract Sql getSql()
    abstract Config getConfig()

    @Override
    User create(String key, String firstName, String lastName, String fullName, Params params = null) {
        UserModel userModel = getConfig().getUserModel()
                .getDeclaredConstructor(Sql, String, String, String, String)
                .newInstance(getSql(), key, firstName, lastName, fullName)
                .insert()
        return new User(userModel)
    }
}
