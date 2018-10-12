package com.endeavour.clink.repository

import androidx.lifecycle.LiveData
import com.endeavour.clink.model.Gate
import com.endeavour.clink.room.GateDao
import java.util.concurrent.Executor

class ClinkLocalCache(
        private val gateDao: GateDao,
        private val ioExecutor: Executor
) {
    fun insertGates(gates: List<Gate>, insertFinished: ()-> Unit) {
        ioExecutor.execute {
            gateDao.insertAll(gates)
            insertFinished()
        }
    }

    fun deleteGates(gates: List<Gate>) {
        ioExecutor.execute {
            gateDao.deleteAll(gates)
        }
    }

    fun getAllGates(): LiveData<List<Gate>> {

        return gateDao.getAllGates()
    }

    fun getGateByCode(code: String): LiveData<Gate> {
        return gateDao.getGateByCode(code)
    }

    fun editGates(gates: List<Gate>, insertFinished: ()-> Unit) {
        ioExecutor.execute {
            gateDao.updateGate(gates)
            insertFinished()
        }
    }

}