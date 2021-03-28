package com.github.wwkarev.sharktask.core.user
import com.github.wwkarev.sharktask.api.user.User as API_User
import com.github.wwkarev.sharktask.core.models.UserModel

final class User implements API_User {
    private UserModel userModel

    User(UserModel userModel) {
        this.userModel = userModel
    }

    @Override
    Long getId() {
        return userModel.id
    }

    @Override
    String getKey() {
        return userModel.key
    }

    @Override
    String getFirstName() {
        return userModel.firstName
    }

    @Override
    String getLastName() {
        return userModel.lastName
    }

    @Override
    String getFullName() {
        return userModel.fullName
    }
}
