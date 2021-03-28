package com.github.wwkarev.sharktask.core.util

import com.github.wwkarev.gorm.Table
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.init.Initializer
import com.github.wwkarev.sharktask.core.models.AttachmentModel
import com.github.wwkarev.sharktask.core.models.AttachmentModelT
import com.github.wwkarev.sharktask.core.models.FieldModelT
import com.github.wwkarev.sharktask.core.models.FieldValueModelT
import com.github.wwkarev.sharktask.core.models.ProjectModelT
import com.github.wwkarev.sharktask.core.models.StatusModelT
import com.github.wwkarev.sharktask.core.models.TaskModelT
import com.github.wwkarev.sharktask.core.models.TaskTypeModelT
import com.github.wwkarev.sharktask.core.models.UserModelT
import groovy.sql.Sql

class TestTableManager {

    static final List<Class> MODELS = [
            AttachmentModelT,
            FieldModelT,
            ProjectModelT,
            StatusModelT,
            TaskTypeModelT,
            UserModelT,
            TaskModelT,
            FieldValueModelT
    ]

    static void createTables(Sql sql, Config config) {
        MODELS.each{model ->
            Table.create(sql, model)
        }

        Initializer initializer = new Initializer(sql, config)
        initializer.init()
    }

    static void dropTables(sql) {
        MODELS.reverse().each{model ->
            Table.drop(sql, model)
        }
    }

    static void truncate(sql) {
        MODELS.reverse().each{model ->
            Table.truncate(sql, model, true)
        }
    }
}
