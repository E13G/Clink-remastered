package com.endeavour.clink.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.endeavour.clink.model.Gate
import javax.inject.Singleton

@Singleton
class ClinkRepository (private val cache: ClinkLocalCache){


    fun searchAllGates(): LiveData<List<Gate>>{

        return cache.getAllGates()
    }

    fun getGateByCode(code: String): LiveData<Gate> {
        return cache.getGateByCode(code)
    }

    fun saveGates(gates :List<Gate>) : LiveData<Boolean>{


        val success = MutableLiveData<Boolean>()
        cache.insertGates(gates) {
            success.postValue(true)
        }

        return success
    }

    fun deleteGate(gate: Gate) {

        cache.deleteGates(listOf(gate))
    }

    fun editGate(gate: Gate) : LiveData<Boolean> {

        val success = MutableLiveData<Boolean>()
        cache.editGates(listOf(gate)){
            success.postValue(true)
        }
        return success
    }
}