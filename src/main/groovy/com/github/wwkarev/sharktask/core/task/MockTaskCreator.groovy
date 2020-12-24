package com.github.wwkarev.sharktask.core.task

import com.github.wwkarev.sharktask.api.task.TaskCreator as API_TaskCreator
import com.github.wwkarev.sharktask.api.task.MutableTask as API_MutableTask
import com.github.wwkarev.sharktask.core.project.Project
import com.github.wwkarev.sharktask.core.status.Status
import com.github.wwkarev.sharktask.core.tasktype.TaskType
import com.github.wwkarev.sharktask.core.user.User
import com.github.wwkarev.sharktask.core.config.DBTableNames
import com.github.wwkarev.sharktask.core.project.ProjectAccessor
import com.github.wwkarev.sharktask.core.status.StatusAccessor
import com.github.wwkarev.sharktask.core.tasktype.TaskTypeAccessor
import com.github.wwkarev.sharktask.core.user.UserAccessor
import groovy.sql.Sql

class MockTaskCreator implements API_TaskCreator {
    private Sql sql
    private DBTableNames dbTableNames
    private Long projectId
    private Long taskTypeId
    private Long statusId
    private String summary
    private Long creatorId
    private Long assigneeId
    private Date created

    private MockTaskCreator(Sql sql, DBTableNames dbTableNames, Long projectId, Long taskTypeId, Long statusId, String summary, Long creatorId, Long assigneeId, Date created) {
        this.sql = sql
        this.dbTableNames = dbTableNames
        this.projectId = projectId
        this.taskTypeId = taskTypeId
        this.statusId = statusId
        this.summary = summary
        this.creatorId = creatorId
        this.assigneeId = assigneeId
        this.created = created
    }

    static MockTaskCreator getInstance(Sql sql, DBTableNames table, Long projectId, Long taskTypeId, Long statusId, String summary, Long creatorId, Long assigneeId, Date created) {
        return new MockTaskCreator(sql, table, projectId, taskTypeId, statusId, summary, creatorId, assigneeId, created)
    }

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



    @Override
    MutableTask create() {Project project = ProjectAccessor.getInstance(sql, dbTableNames).getById(projectId)
        TaskType taskType = TaskTypeAccessor.getInstance(sql, dbTableNames).getById(taskTypeId)
        Status status = StatusAccessor.getInstance(sql, dbTableNames).getById(statusId)
        User creator = UserAccessor.getInstance(sql, dbTableNames).getById(creatorId)
        User assignee = UserAccessor.getInstance(sql, dbTableNames).getById(assigneeId)

        Long id = createDBRecordMutableTask(projectId, taskTypeId, statusId, summary, creatorId, assigneeId, created)

        return new MutableTask(sql, dbTableNames, id, project, taskType, status, summary, creator, assignee, created)
    }

    private Long createDBRecordMutableTask(Long projectId, Long taskTypeId, Long statusId, String summary, Long creatorId, Long assigneeId, Date created) {
        return sql.executeInsert(
                ("insert into ${dbTableNames.getTaskTable()} " +
                        "(project_id, task_type_id, status_id, summary, creator_id, assignee_id, created) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)").toString(),
                [projectId, taskTypeId, statusId, summary, creatorId, assigneeId, created.toTimestamp()]
        )[0][0]
    }
}
