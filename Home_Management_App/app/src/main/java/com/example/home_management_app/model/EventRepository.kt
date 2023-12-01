package com.example.home_management_app.model

object EventRepository {
    private val events = mutableListOf<SimpleEvent>()

    fun addEvent(event: SimpleEvent) {
        events.add(event)
    }

    fun getEvents(): List<SimpleEvent> {
        return events
    }

    fun deleteEvent(event: SimpleEvent) {
        events.remove(event)
    }
}
