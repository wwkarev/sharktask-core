package com.github.wwkarev.sharktask.core.project

import com.github.wwkarev.sharktask.api.project.Project as API_Project

final class Project implements API_Project {
    private Long id
    private String key
    private String name

    Project(Long id, String key, String name) {
        this.id = id
        this.key = key
        this.name = name
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
    String getName() {
        return name
    }
}
