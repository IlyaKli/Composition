package com.ilya.composition.domain.usecases

import com.ilya.composition.domain.entity.GameSettings
import com.ilya.composition.domain.entity.Level
import com.ilya.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}