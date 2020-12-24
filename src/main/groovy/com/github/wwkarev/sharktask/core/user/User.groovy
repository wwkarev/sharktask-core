package com.github.wwkarev.sharktask.core.user
import com.github.wwkarev.sharktask.api.user.User as API_User
class User implements API_User {
    private Long id
    private String key
    private String firstName
    private String lastName
    private String fullName

    User(Long id, String key, String firstName, String lastName, String fullName) {
        this.id = id
        this.key = key
        this.firstName = firstName
        this.lastName = lastName
        this.fullName = fullName
    }

    @Override
    Long getId() {
        return id
    }

    @Override
    String getKey() {
        return key
    }

    @Override
    String getFirstName() {
        return firstName
    }

    @Override
    String getLastName() {
        return lastName
    }

    @Override
    String getFullName() {
        return fullName
    }
}
