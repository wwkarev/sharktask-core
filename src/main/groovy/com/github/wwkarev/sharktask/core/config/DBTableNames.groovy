package com.github.wwkarev.sharktask.core.config

interface DBTableNames {
    abstract String getProjectTable()
    abstract String getTaskTypeTable()
    abstract String getTaskTable()
    abstract String getFieldTable()
    abstract String getFieldValueTable()
    abstract String getStatusTable()
    abstract String getUserTable()
}
