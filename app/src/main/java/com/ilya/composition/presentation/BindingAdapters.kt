package com.ilya.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.ilya.composition.R
import com.ilya.composition.domain.entity.GameResult

interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        count
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        count
    )
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        calculateScorePercent(gameResult)
    )
}

private fun calculateScorePercent(gameResult: GameResult): Int {
    return if (gameResult.countOfQuestions == 0) {
        0
    } else {
        (gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble() * 100).toInt()
    }
}

@BindingAdapter("setImage")
fun bindSmile(imageView: ImageView, winner: Boolean) {
    if (winner) {
        imageView.setImageResource(R.drawable.ic_smile)
    } else {
        imageView.setImageResource(R.drawable.ic_sad)
    }
}

@BindingAdapter("numberAsText")
fun bindScorePercentage(textView: TextView, number: Number) {
    textView.text = number.toString()
}

@BindingAdapter("progressAnswersColor")
fun bindEnoughCount(textView: TextView, enoughCount: Boolean) {
    textView.setTextColor(getColorByState(textView.context, enoughCount))
}


@BindingAdapter("progressPercentageRightAnswers")
fun bindProgressPercentage(progressBar: ProgressBar, percentOfRightAnswer: Int) {
    progressBar.setProgress(percentOfRightAnswer, true)
}

@BindingAdapter("secondaryProgress")
fun bindSecondaryProgress(progressBar: ProgressBar, minPercent: Int) {
    progressBar.secondaryProgress = minPercent
}

@BindingAdapter("progressBarColor")
fun bindPercentCount(progressBar: ProgressBar, enoughPercent: Boolean) {
    val color = getColorByState(progressBar.context, enoughPercent)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("onOptionClickListener")
fun setOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}

private fun getColorByState(context: Context, goodeState: Boolean): Int {
    val colorResId = if (goodeState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}