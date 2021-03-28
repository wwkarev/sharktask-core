package com.github.wwkarev.sharktask.core.status

import com.github.wwkarev.sharktask.api.status.Status as API_Status
import com.github.wwkarev.sharktask.core.models.StatusModel

final class Status implements API_Status {
    private StatusModel statusModel

    Status(StatusModel statusModel) {
        this.statusModel = statusModel
    }

    @Override
    Long getId() {
        return statusModel.id
    }

    @Override
    String getName() {
        return statusModel.name
    }
}
