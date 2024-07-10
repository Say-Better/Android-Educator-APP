package com.example.saybettereducator.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MyEventListener : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        return
    }

    override fun onCancelled(error: DatabaseError) {

    }
}