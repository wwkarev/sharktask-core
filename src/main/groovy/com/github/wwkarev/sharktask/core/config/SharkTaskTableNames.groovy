package com.github.wwkarev.sharktask.core.config

class SharkTaskTableNames implements DBTableNames {
    @Override
    String getProjectTable() {
        return 'sht_project'
    }

    @Override
    String getTaskTypeTable() {
        return 'sht_task_type'
    }

    @Override
    String getTaskTable() {
        return 'sht_task'
    }

    @Override
    String getFieldTable() {
        return 'sht_field'
    }

    @Override
    String getFieldValueTable() {
        return 'sht_field_value'
    }

    @Override
    String getStatusTable() {
        return 'sht_status'
    }

    @Override
    String getUserTable() {
        return 'sht_user'
    }
}
