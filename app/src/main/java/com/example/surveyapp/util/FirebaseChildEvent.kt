package com.example.surveyapp.util


/******
 * Used to return a child with action performed on th child
 */
data class FirebaseChildEvent<out T>(val eventType: EventType, val data: T?) {

    companion object {
        fun <T> added(data: T?): FirebaseChildEvent<T> {
            return FirebaseChildEvent(EventType.ADDED, data)
        }

        fun <T> removed(data: T?): FirebaseChildEvent<T> {
            return FirebaseChildEvent(EventType.REMOVED, data)
        }

        fun <T> changed(data: T?): FirebaseChildEvent<T> {
            return FirebaseChildEvent(EventType.CHANGED, data)
        }

        fun <T> moved(data: T?): FirebaseChildEvent<T> {
            return FirebaseChildEvent(EventType.MOVED, data)
        }

        fun <T> failed(error: T?): FirebaseChildEvent<T> {
            return FirebaseChildEvent(EventType.FAILED, error)
        }
    }
}

enum class EventType {
    ADDED,
    REMOVED,
    CHANGED,
    MOVED,
    FAILED
}