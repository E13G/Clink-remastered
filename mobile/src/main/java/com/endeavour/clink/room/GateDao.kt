package com.endeavour.clink.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.endeavour.clink.model.Gate



@Dao
interface GateDao {

    @Query(RoomContract.ALL_GATES + " where code = :code LIMIT 1")
    fun getGateByCode(code :String): LiveData<Gate>

    @Query(RoomContract.ALL_GATES)
    fun getAllGates(): LiveData<List<Gate>>

    @Query(RoomContract.GATES_COUNT)
    fun getGatesCount(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(gates: List<Gate>)

    @Delete
    fun deleteAll(gates: List<Gate>)

    @Update
    fun updateGate(gate: List<Gate>)

}