package com.ilya.composition.domain.repository

import com.ilya.composition.domain.entity.GameSettings
import com.ilya.composition.domain.entity.Level
import com.ilya.composition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int) : Question

    fun getGameSettings(level: Level) : GameSettings
}