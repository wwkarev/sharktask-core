package com.github.wwkarev.sharktask.core.task

import com.github.wwkarev.gorm.Table
import com.github.wwkarev.sharktask.api.attachment.Attachment
import com.github.wwkarev.sharktask.api.eventdispatchoption.EventDispatchOption
import com.github.wwkarev.sharktask.core.C
import com.github.wwkarev.sharktask.core.config.DBConfig
import com.github.wwkarev.sharktask.core.config.Config
import com.github.wwkarev.sharktask.core.field.Field
import com.github.wwkarev.sharktask.core.field.FieldType
import com.github.wwkarev.sharktask.core.user.User
import com.github.wwkarev.sharktask.core.util.SharkTaskConfig
import com.github.wwkarev.sharktask.core.util.TestTableManager
import com.github.wwkarev.sharktask.core.util.TestUtils
import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class TaskSpec extends Specification {
    @Shared
    DBConfig dbConfig = DBConfig.load(C.DB_CONFIG)
    @Shared
    Sql sql = Sql.newInstance(dbConfig.host, dbConfig.user, dbConfig.password, dbConfig.driver)
    @Shared
    Config config = new SharkTaskConfig()

    def setupSpec() {
        TestTableManager.createTables(sql, config)
    }

    def "updateFieldValue. TEXT"() {
        given:
        User user = TestUtils.createUser(sql, config)
        Field field = TestUtils.createField(sql, config, FieldType.TEXT)
        Task task = TestUtils.createTask(sql, config)
        String destValue = "QWERTY"

        when:
        task.updateFieldValue(field.id, 'XXX', user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        String resultValue = task.getFieldValue(field.id)

        then:
        assert destValue == resultValue
    }

    def "updateFieldValue. NUMBER"() {
        given:
        User user = TestUtils.createUser(sql, config)
        Field field = TestUtils.createField(sql, config, FieldType.NUMBER)
        Task task = TestUtils.createTask(sql, config)
        Double destValue = 10

        when:
        task.updateFieldValue(field.id, 0, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        Double resultValue = task.getFieldValue(field.id)

        then:
        assert destValue == resultValue
    }

    def "updateFieldValue. DATE"() {
        given:
        User user = TestUtils.createUser(sql, config)
        Field field = TestUtils.createField(sql, config, FieldType.DATE)
        Task task = TestUtils.createTask(sql, config)
        Date destValue = new Date()

        when:
        task.updateFieldValue(field.id, null, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        Date resultValue = task.getFieldValue(field.id)

        then:
        assert ((Date)destValue).getTime() == ((Date)resultValue).getTime()
    }

    def "updateFieldValue. USER"() {
        given:
        User user = TestUtils.createUser(sql, config)
        Field field = TestUtils.createField(sql, config, FieldType.USER)
        Task task = TestUtils.createTask(sql, config)
        User destValue = TestUtils.createUser(sql, config)

        when:
        task.updateFieldValue(field.id, null, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        User resultUser = task.getFieldValue(field.id)

        then:
        assert ((User)destValue).getKey() == ((User)resultUser).getKey()
    }

    def "updateFieldValue. JSON"() {
        given:
        User user = TestUtils.createUser(sql, config)
        Field field = TestUtils.createField(sql, config, FieldType.JSON)
        Task task = TestUtils.createTask(sql, config)
        def destValue = [
                id: UUID.randomUUID().toString(),
                key: UUID.randomUUID().toString()
        ]

        when:
        task.updateFieldValue(field.id, null, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        def resultValue = task.getFieldValue(field.id)

        then:
        assert destValue.id == resultValue.id
        assert destValue.key == resultValue.key
    }

    def "updateFieldValue. TEXT NULL"() {
        given:
        User user = TestUtils.createUser(sql, config)
        Field field = TestUtils.createField(sql, config, FieldType.TEXT)
        Task task = TestUtils.createTask(sql, config)
        String destValue = null

        when:
        task.updateFieldValue(field.id, 'XXX', user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        String resultValue = task.getFieldValue(field.id)

        then:
        assert destValue == resultValue
    }

    def "updateFieldValue. NUMBER NULL"() {
        given:
        User user = TestUtils.createUser(sql, config)
        Field field = TestUtils.createField(sql, config, FieldType.NUMBER)
        Task task = TestUtils.createTask(sql, config)
        Double destValue = null

        when:
        task.updateFieldValue(field.id, 0, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        Double resultValue = task.getFieldValue(field.id)

        then:
        assert destValue == resultValue
    }

    def "updateFieldValue. DATE NULL"() {
        given:
        User user = TestUtils.createUser(sql, config)
        Field field = TestUtils.createField(sql, config, FieldType.DATE)
        Task task = TestUtils.createTask(sql, config)
        Date destValue = null

        when:
        task.updateFieldValue(field.id, new Date(), user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        Date resultValue = task.getFieldValue(field.id)

        then:
        assert destValue == resultValue
    }

    def "updateFieldValue. USER NULL"() {
        given:
        User user = TestUtils.createUser(sql, config)
        Field field = TestUtils.createField(sql, config, FieldType.USER)
        Task task = TestUtils.createTask(sql, config)
        Double destValue = null

        when:
        task.updateFieldValue(field.id, TestUtils.createUser(sql, config), user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        Double resultValue = task.getFieldValue(field.id)

        then:
        assert destValue == resultValue
    }

    def "updateFieldValue. JSON NULL"() {
        given:
        User user = TestUtils.createUser(sql, config)
        Field field = TestUtils.createField(sql, config, FieldType.JSON)
        Task task = TestUtils.createTask(sql, config)
        Date destValue = null

        when:
        task.updateFieldValue(field.id, [id: 0], user, EventDispatchOption.DO_NOT_DISPATCH)
        task.updateFieldValue(field.id, destValue, user, EventDispatchOption.DO_NOT_DISPATCH)
        Date resultValue = task.getFieldValue(field.id)

        then:
        assert destValue == resultValue
    }

    def "addAttachment. getAttachments"() {
        given:
        String filePath = 'src/test/resources/attachments/attachment.txt'
        String attachmentName1 = "attach1"
        String attachmentName2 = "attach2"
        String attachmentName3 = "attach3"

        User user = TestUtils.createUser(sql, config)
        MutableTask task = TestUtils.createTask(sql, config)


        when:
        task.addAttachment(filePath, attachmentName1, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.addAttachment(filePath, attachmentName2, user, EventDispatchOption.DO_NOT_DISPATCH)
        task.addAttachment(filePath, attachmentName3, user, EventDispatchOption.DO_NOT_DISPATCH)

        task.getAttachments().each{ Attachment attachment ->
            if (attachment.getName() == attachmentName2) {
                task.removeAttachment(attachment.getId(), user, EventDispatchOption.DO_NOT_DISPATCH)
            }
        }

        List<String> attachmentNames = task.getAttachments().collect{it.getName()}.sort()

        then:
        task.getAttachments().size() == 2
        attachmentNames[0] == attachmentName1
        attachmentNames[1] == attachmentName3
    }

    def "addAttachment. file not found"() {
        given:
        User user = TestUtils.createUser(sql, config)
        MutableTask task = TestUtils.createTask(sql, config)

        when:
        task.addAttachment(filePath, UUID.randomUUID().toString(), user, EventDispatchOption.DO_NOT_DISPATCH)

        then:
        thrown FileNotFoundException
        where:
        filePath|_
        'src/test/resources/attachments/_attachment.txt'|_
        'src/test/resources/attachments/'|_
    }

    def cleanup() {
        TestTableManager.truncate(sql)
    }

    def cleanupSpec() {
        TestTableManager.dropTables(sql)
        sql.close()
    }
}
