package com.ilya.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level : Parcelable {

    TEAST, EASY, NORMAL, HARD
}