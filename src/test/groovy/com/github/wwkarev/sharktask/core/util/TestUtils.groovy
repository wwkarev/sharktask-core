package com.github.wwkarev.sharktask.core.util

import com.github.wwkarev.sharktask.core.field.Field
import com.github.wwkarev.sharktask.core.project.Project
import com.github.wwkarev.sharktask.core.status.Status
import com.github.wwkarev.sharktask.core.tasktype.TaskType
import com.github.wwkarev.sharktask.core.user.User
import com.github.wwkarev.sharktask.core.config.DBTableNames
import com.github.wwkarev.sharktask.core.field.FieldCreator
import com.github.wwkarev.sharktask.core.project.ProjectCreator
import com.github.wwkarev.sharktask.core.status.StatusCreator
import com.github.wwkarev.sharktask.core.task.MockTaskCreator
import com.github.wwkarev.sharktask.core.task.MutableTask
import com.github.wwkarev.sharktask.core.tasktype.TaskTypeCreator
import com.github.wwkarev.sharktask.core.user.UserCreator
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
    }

    static void compare(User a, User b) {
        assert a.getId() == b.getId()
        assert a.getKey() == b.getKey()
        assert a.getFirstName() == b.getFirstName()
        assert a.getLastName() == b.getLastName()
        assert a.getFullName() == b.getFullName()
    }

    static void clearDB(Sql sql, DBTableNames dbTableNames) {
        [
                dbTableNames.getTaskTable(),
                dbTableNames.getProjectTable(),
                dbTableNames.getTaskTypeTable(),
                dbTableNames.getStatusTable()
        ].each{String table ->
            sql.execute("truncate table $table cascade".toString())
        }
    }

    static Project createProject(Sql sql, DBTableNames dbTableNames) {
        return ProjectCreator.getInstance(sql, dbTableNames, UUID.randomUUID().toString(), UUID.randomUUID().toString()).create()
    }

    static TaskType createTaskType(Sql sql, DBTableNames dbTableNames) {
        return TaskTypeCreator.getInstance(sql, dbTableNames, UUID.randomUUID().toString()).create()
    }

    static Status createStatus(Sql sql, DBTableNames dbTableNames) {
        return StatusCreator.getInstance(sql, dbTableNames, UUID.randomUUID().toString()).create()
    }

    static Field createField(Sql sql, DBTableNames dbTableNames, String fieldValueType) {
        return FieldCreator.getInstance(sql, dbTableNames, UUID.randomUUID().toString(), fieldValueType).create()
    }

    static User createUser(Sql sql, DBTableNames dbTableNames) {
        return UserCreator.getInstance(
                sql, dbTableNames,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString())
                .create()
    }

    static MutableTask createTask(Sql sql, DBTableNames dbTableNames) {
        Project project = TestUtils.createProject(sql, dbTableNames)
        TaskType taskType = TestUtils.createTaskType(sql, dbTableNames)
        Status status = TestUtils.createStatus(sql, dbTableNames)
        User creator = TestUtils.createUser(sql, dbTableNames)
        User assignee = TestUtils.createUser(sql, dbTableNames)
        String summary = UUID.randomUUID().toString()
        Date created = new Date()

        MockTaskCreator taskCreator = MockTaskCreator.getInstance(
                sql, dbTableNames,
                project.getId(),
                taskType.getId(),
                status.getId(),
                summary,
                creator.getId(),
                assignee.getId(),
                created
        )
        return taskCreator.create()
    }
}
