package com.github.wwkarev.sharktask.core.attachment

import com.github.wwkarev.sharktask.api.attachment.Attachment as API_Attachment
import com.github.wwkarev.sharktask.core.models.AttachmentModel

final class Attachment implements API_Attachment {
    private AttachmentModel attachmentModel

    Attachment(AttachmentModel attachmentModel) {
        this.attachmentModel = attachmentModel
    }

    @Override
    Long getId() {
        return attachmentModel.id
    }

    @Override
    String getName() {
        return attachmentModel.name
    }

    @Override
    File getFile() {
        return new File(attachmentModel.filePath)
    }
}
