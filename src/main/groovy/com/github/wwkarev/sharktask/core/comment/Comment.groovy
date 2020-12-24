package com.github.wwkarev.sharktask.core.comment

import com.github.wwkarev.sharktask.api.comment.Comment as API_Comment
import com.github.wwkarev.sharktask.api.user.User
import com.github.wwkarev.sharktask.api.user.User as API_User

final class Comment implements API_Comment {
    private Long id
    private String body
    private API_User user

    Comment(Long id, String body, User user) {
        this.id = id
        this.body = body
        this.user = user
    }

    @Override
    Long getId() {
        return id
    }

    @Override
    String getBody() {
        return body
    }

    @Override
    API_User getAuthor() {
        return user
    }
}
