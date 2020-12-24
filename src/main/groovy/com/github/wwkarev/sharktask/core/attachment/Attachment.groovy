package com.github.wwkarev.sharktask.core.attachment

import com.github.wwkarev.sharktask.api.attachment.Attachment as API_Attachment

final class Attachment implements API_Attachment {
    private Long id
    private String name
    private File file

    Attachment(Long id, String name, File file) {
        this.id = id
        this.name = name
        this.file = file
    }

    @Override
    Long getId() {
        return id
    }

    @Override
    String getName() {
        return name
    }

    @Override
    File getFile() {
        return file
    }
}
