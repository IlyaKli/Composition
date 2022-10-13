package com.ilya.composition.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilya.composition.data.GameRepositoryImpl
import com.ilya.composition.domain.entity.GameSettings
import com.ilya.composition.domain.entity.Level
import com.ilya.composition.domain.entity.Question
import com.ilya.composition.domain.usecases.GenerateQuestionUseCase
import com.ilya.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel: ViewModel() {

    private val gameRepository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(gameRepository)

    private val getGameSettingsUseCase = GetGameSettingsUseCase(gameRepository)

    private val _gameSettings = MutableLiveData<GameSettings>()
    val gameSettings: LiveData<GameSettings>
        get() = _gameSettings

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _isRightAnswer = MutableLiveData<Boolean>()
    val isRightAnswer: LiveData<Boolean>
        get() = _isRightAnswer

    fun getGameSettings(level: Level) {
        _gameSettings.value = getGameSettingsUseCase.invoke(level)
    }

    fun generateQuestion() {
        _question.value = _gameSettings.value?.let { generateQuestionUseCase.invoke(it.maxSumValue) }
    }

    fun isRightAnswer(answer: Int) {
        val sum = question.value?.sum
        val visibleNumber = question.value?.visibleNumber
        val rightAnswer = sum!! - visibleNumber!!
        _isRightAnswer.value = answer == rightAnswer
    }
}