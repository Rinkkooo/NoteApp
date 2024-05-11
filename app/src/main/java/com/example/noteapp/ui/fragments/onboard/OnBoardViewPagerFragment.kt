package com.example.noteapp.ui.fragments.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieCompositionFactory
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardViewPagerBinding
import com.google.android.material.tabs.TabLayout

class OnBoardViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardViewPagerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() = with(binding) {
            when (requireArguments().getInt(ARG_ONBOARD_POSITION)) {
                0 -> {
                    LottieCompositionFactory.fromRawRes(context, R.raw.lottie1)
                    tvOnTxt.text = "Очень удобный функционал"
                }

                1 -> {
                    LottieCompositionFactory.fromRawRes(context, R.raw.lottie2)
                    tvOnTxt.text = "Быстрый, качественный продукт"
                }

                2 -> {
                    LottieCompositionFactory.fromRawRes(context, R.raw.lottie3)
                    tvOnTxt.text = "Куча функций и интересных фишек"
                }
            }
    }

    companion object {
        const val ARG_ONBOARD_POSITION = "onBoard"
    }
}