package com.endeavour.clink.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.endeavour.clink.room.RoomContract

@Entity(tableName = RoomContract.TABLE_GATES)
data class Gate(@PrimaryKey(autoGenerate = true) val id: Long, var name: String, var code: String){
    override fun toString(): String {
        return name
    }
}