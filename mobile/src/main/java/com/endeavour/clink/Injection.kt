package com.endeavour.clink

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.endeavour.clink.repository.ClinkLocalCache
import com.endeavour.clink.repository.ClinkRepository
import com.endeavour.clink.room.GateDatabase
import com.endeavour.clink.ui.main.ViewModelFactory
import java.util.concurrent.Executors

object Injection {


    private fun provideCache(context: Context): ClinkLocalCache {
        val database = GateDatabase.getInstance(context)
        return ClinkLocalCache(database.gatesDao(), Executors.newSingleThreadExecutor())
    }

    private fun provideClinkRepository(context: Context): ClinkRepository {
        return ClinkRepository(provideCache(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideClinkRepository(context))
    }

}