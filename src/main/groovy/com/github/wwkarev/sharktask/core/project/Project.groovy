package com.github.wwkarev.sharktask.core.project

import com.github.wwkarev.sharktask.api.project.Project as API_Project
import com.github.wwkarev.sharktask.core.models.ProjectModel

final class Project implements API_Project {
    private ProjectModel projectModel

    Project(ProjectModel projectModel) {
        this.projectModel = projectModel
    }

    @Override
    Long getId() {
        return projectModel.id
    }

    @Override
    String getKey() {
        return projectModel.key
    }

    @Override
    String getName() {
        return projectModel.name
    }
}
