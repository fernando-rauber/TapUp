package uk.fernando.tapup.ext

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

fun DatabaseReference.toDeferredAsync(): Deferred<DataSnapshot> {
    val deferred = CompletableDeferred<DataSnapshot>()

    this.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
            deferred.completeExceptionally(p0.toException())
        }

        override fun onDataChange(p0: DataSnapshot) {
            deferred.complete(p0)
        }
    })
    return deferred
}