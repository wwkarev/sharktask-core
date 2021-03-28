package com.github.wwkarev.sharktask.core.util

import com.github.wwkarev.sharktask.core.field.Field
import com.github.wwkarev.sharktask.core.field.FieldManager
import com.github.wwkarev.sharktask.core.field.FieldType
import com.github.wwkarev.sharktask.core.params.ParamId
import com.github.wwkarev.sharktask.core.params.Params
import com.github.wwkarev.sharktask.core.project.Project
import com.github.wwkarev.sharktask.core.project.ProjectManager
import com.github.wwkarev.sharktask.core.status.Status
import com.github.wwkarev.sharktask.core.status.StatusManager
import com.github.wwkarev.sharktask.core.task.MutableTask
import com.github.wwkarev.sharktask.core.task.TaskManager
import com.github.wwkarev.sharktask.core.tasktype.TaskType
import com.github.wwkarev.sharktask.core.tasktype.TaskTypeManager
import com.github.wwkarev.sharktask.core.user.User
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.user.UserManager
import groovy.sql.Sql

class TestUtils {
    static void compare(Project a, Project b) {
        assert a.getId() == b.getId()
        assert a.getKey() == b.getKey()
        assert a.getName() == b.getName()
    }

    static void compare(TaskType a, TaskType b) {
        assert a.getId() == b.getId()
        assert a.getName() == b.getName()
    }

    static void compare(Status a, Status b) {
        assert a.getId() == b.getId()
        assert a.getName() == b.getName()
    }

    static void compare(Field a, Field b) {
        assert a.getId() == b.getId()
        assert a.getName() == b.getName()
        assert a.getType() == b.getType()
    }

    static void compare(User a, User b) {
        assert a.getId() == b.getId()
        assert a.getKey() == b.getKey()
        assert a.getFirstName() == b.getFirstName()
        assert a.getLastName() == b.getLastName()
        assert a.getFullName() == b.getFullName()
    }

    static void clearDB(Sql sql, Config dbTableNames) {
        [
                dbTableNames.getTaskTable(),
                dbTableNames.getProjectTable(),
                dbTableNames.getTaskTypeTable(),
                dbTableNames.getStatusTable()
        ].each{String table ->
            sql.execute("truncate table $table cascade".toString())
        }
    }

    static Project createProject(Sql sql, Config dbTableNames) {
        return ProjectManager.newInstance(sql, dbTableNames).create(UUID.randomUUID().toString(), UUID.randomUUID().toString())
    }

    static TaskType createTaskType(Sql sql, Config dbTableNames) {
        return TaskTypeManager.newInstance(sql, dbTableNames).create(UUID.randomUUID().toString())
    }

    static Status createStatus(Sql sql, Config dbTableNames) {
        return StatusManager.newInstance(sql, dbTableNames).create(UUID.randomUUID().toString())
    }

    static Field createField(Sql sql, Config models, FieldType fieldType) {
        Params params = new Params()
        params.add(ParamId.FIELD_TYPE, fieldType)
        return FieldManager.newInstance(sql, models).create(UUID.randomUUID().toString(), params)
    }

    static User createUser(Sql sql, Config dbTableNames) {
        return UserManager
                .newInstance(sql, dbTableNames)
                .create(
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString()
                )
    }

    static MutableTask createTask(Sql sql, Config config) {
        Project project = TestUtils.createProject(sql, config)
        TaskType taskType = TestUtils.createTaskType(sql, config)
        Status status = TestUtils.createStatus(sql, config)
        User creator = TestUtils.createUser(sql, config)

        Params params = new Params()
        params.add(ParamId.SUMMARY, UUID.randomUUID().toString())
        params.add(ParamId.STATUS, status.getId())
        params.add(ParamId.CREATOR, creator.getKey())

        return TaskManager
                .newInstance(sql, config)
                .create(project.getId(), taskType.getId(), params)
    }
}
