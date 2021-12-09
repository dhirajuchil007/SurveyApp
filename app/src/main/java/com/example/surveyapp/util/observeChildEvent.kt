package com.example.surveyapp.util

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

private const val TAG = "observeChildEvent"
@ExperimentalCoroutinesApi
fun DatabaseReference.observeChildEvent(): Flow<FirebaseChildEvent<DataSnapshot>> {
    return callbackFlow {
        val listener = object : ChildEventListener {


            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                offer(FirebaseChildEvent.moved(snapshot))
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                offer(FirebaseChildEvent.changed(snapshot))
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                offer(FirebaseChildEvent.added(snapshot))
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                offer(FirebaseChildEvent.removed(snapshot))
            }
        }

        addChildEventListener(listener)
        awaitClose { removeEventListener(listener) }
    }


}