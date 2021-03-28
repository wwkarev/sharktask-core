package com.github.wwkarev.sharktask.core.event

import com.github.wwkarev.sharktask.api.event.Event as API_Event
import com.github.wwkarev.sharktask.core.task.MutableTask

class Event implements API_Event {
    private EventType eventType
    private MutableTask task

    Event(EventType eventType, MutableTask task) {
        this.eventType = eventType
        this.task = task
    }

    @Override
    EventType getEventType() {
        return eventType
    }

    @Override
    MutableTask getTask() {
        return task
    }
}
