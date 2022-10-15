package com.ilya.composition.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilya.composition.domain.entity.Level

class GameViewModelFactory(private val level: Level, private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(application, level) as T
        } else {
            throw RuntimeException("Unknown view model class $modelClass")
        }
    }
}