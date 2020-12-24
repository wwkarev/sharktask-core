package com.github.wwkarev.sharktask.core.status

import com.github.wwkarev.sharktask.api.status.Status as API_Status

final class Status implements API_Status {
    private Long id
    private String name

    Status(Long id, String name) {
        this.id = id
        this.name = name
    }

    @Override
    Long getId() {
        return id
    }

    @Override
    String getName() {
        return name
    }
}
