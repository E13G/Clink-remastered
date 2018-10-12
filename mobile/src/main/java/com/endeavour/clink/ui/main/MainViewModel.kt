package com.endeavour.clink.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.endeavour.clink.model.Gate
import com.endeavour.clink.repository.ClinkRepository

class MainViewModel(private val repository: ClinkRepository) : ViewModel() {

    val gate = repository.searchAllGates()

    private var gateCode = ""

    fun saveGate(gate: Gate): LiveData<Boolean>{

        return repository.saveGates(listOf(gate))
    }

    fun getCode(): String {

        return gateCode
    }

    fun setCode(code:String){

        gateCode = code
    }

    fun getGateByCode(code:String) : LiveData<Gate> {

        return repository.getGateByCode(code)
    }

    fun deleteGate(gate: Gate) {

        repository.deleteGate(gate)
    }

    fun editGate(gate: Gate) {

        repository.editGate(gate)
    }
}
