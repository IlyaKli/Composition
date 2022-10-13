package com.ilya.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ilya.composition.R
import com.ilya.composition.databinding.FragmentGameBinding
import com.ilya.composition.domain.entity.GameResult
import com.ilya.composition.domain.entity.GameSettings
import com.ilya.composition.domain.entity.Level
import kotlin.properties.Delegates

class GameFragment : Fragment() {

    val gameViewModel by lazy { ViewModelProvider(this)[GameViewModel::class.java] }

    private var level: Level? = null

    var minCountOfRightAnswers by Delegates.notNull<Int>()

    var rightAnswers = 0

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()
        level?.let { gameViewModel.getGameSettings(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvOption1.setOnClickListener {
            launchGameFinishedFragment(GameResult(true, 7, 8, GameSettings(15, 6, 80, 60)))
        }
        gameViewModelObserver()
        clickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clickListener() {
        with(binding) {
            tvOption1.setOnClickListener {
                val answer = binding.tvOption1.text.toString().toInt()
                gameViewModel.isRightAnswer(answer)
            }
            tvOption2.setOnClickListener {
                val answer = binding.tvOption2.text.toString().toInt()
                gameViewModel.isRightAnswer(answer)
            }
            tvOption3.setOnClickListener {
                val answer = binding.tvOption3.text.toString().toInt()
                gameViewModel.isRightAnswer(answer)
            }
            tvOption4.setOnClickListener {
                val answer = binding.tvOption4.text.toString().toInt()
                gameViewModel.isRightAnswer(answer)
            }
            tvOption5.setOnClickListener {
                val answer = binding.tvOption5.text.toString().toInt()
                gameViewModel.isRightAnswer(answer)
            }
            tvOption6.setOnClickListener {
                val answer = binding.tvOption6.text.toString().toInt()
                gameViewModel.isRightAnswer(answer)
            }
        }
    }

    private fun gameViewModelObserver() {
        gameViewModel.isRightAnswer.observe(viewLifecycleOwner) {
            if (it == true) {
                rightAnswers++
                binding.tvAnswersProgress.text = "$rightAnswers, $minCountOfRightAnswers"
            }
            gameViewModel.generateQuestion()
        }

        gameViewModel.gameSettings.observe(viewLifecycleOwner) {
            gameViewModel.generateQuestion()
           minCountOfRightAnswers = it.minCountOfRightAnswers
        }

        gameViewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            val string = String.format("%s, %s", rightAnswers, minCountOfRightAnswers)
            binding.tvAnswersProgress.text = string
            binding.tvOption1.text = it.options[0].toString()
            binding.tvOption2.text = it.options[1].toString()
            binding.tvOption3.text = it.options[2].toString()
            binding.tvOption4.text = it.options[3].toString()
            binding.tvOption5.text = it.options[4].toString()
            binding.tvOption6.text = it.options[5].toString()
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    private fun parsArgs() {
        requireArguments().getParcelable<Level>(ARG_LEVEL)?.let {
            level = it
        }
    }

    companion object {

        const val NAME = "GameFragment"
        private const val ARG_LEVEL = "level"

        @JvmStatic
        fun newInstance(level: Level) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_LEVEL, level)
                }
            }
    }
}