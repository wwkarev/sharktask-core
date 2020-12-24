package com.github.wwkarev.sharktask.core.task

import com.github.wwkarev.sharktask.core.config.DBTableNames
import com.github.wwkarev.sharktask.core.project.Project
import com.github.wwkarev.sharktask.core.status.Status
import com.github.wwkarev.sharktask.core.tasktype.TaskType
import com.github.wwkarev.sharktask.core.user.User
import groovy.sql.Sql

import com.github.wwkarev.sharktask.api.task.MutableTask as API_MutableTask
import com.github.wwkarev.sharktask.api.task.TaskAccessor as API_TaskAccessor

final class TaskAccessor implements API_TaskAccessor {
    private Sql sql
    private DBTableNames dbTableNames

    private TaskAccessor(Sql sql, DBTableNames dbTableNames) {
        this.sql = sql
        this.dbTableNames = dbTableNames
    }

    static TaskAccessor getInstance(Sql sql, DBTableNames table) {
        return new TaskAccessor(sql, table)
    }

    @Override
    API_MutableTask getTaskById(Long taskId) {
        def rowResult = sql.rows(
                ("select " +
                        "task.id id, task.summary summary," +
                        "project.id project_id, project.key project_key, project.name project_name, " +
                        "task_type.id task_type_id, task_type.name task_type_name, " +
                        "status.id status_id, status.name status_name, " +
                        "creator.id creator_id, creator.key creator_key, creator.first_name creator_first_name, creator.last_name creator_last_name, creator.full_name creator_full_name, " +
                        "assignee.id assignee_id, assignee.key assignee_key, assignee.first_name assignee_first_name, assignee.last_name assignee_last_name, assignee.full_name assignee_full_name, " +
                        "created " +
                        "from ${dbTableNames.getTaskTable()} task " +
                        "join ${dbTableNames.getProjectTable()} project on task.project_id = project.id " +
                        "join ${dbTableNames.getTaskTypeTable()} task_type on task.task_type_id = task_type.id " +
                        "join ${dbTableNames.getStatusTable()} status on task.status_id = status.id " +
                        "join ${dbTableNames.getUserTable()} creator on task.creator_id = creator.id " +
                        "join ${dbTableNames.getUserTable()} assignee on task.assignee_id = assignee.id " +
                        "where task.id = ?").toString(),
                [taskId]
        )[0]
        Project project = new Project(rowResult.project_id, rowResult.project_key, rowResult.project_name)
        TaskType taskType = new TaskType(rowResult.task_type_id, rowResult.task_type_name)
        Status status = new Status(rowResult.status_id, rowResult.status_name)
        User creator = new User(rowResult.creator_id, rowResult.creator_key, rowResult.creator_first_name, rowResult.creator_last_name, rowResult.creator_full_name)
        User assignee = new User(rowResult.assignee_id, rowResult.assignee_key, rowResult.assignee_first_name, rowResult.assignee_last_name, rowResult.assignee_full_name)

        return new MutableTask(sql, dbTableNames, rowResult.id, project, taskType, status, rowResult.summary, creator, assignee, rowResult.created)
    }
}
