package com.example.noteapp.ui.fragments.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardBinding
import com.example.noteapp.ui.adapter.OnBoardViewPagerAdapter
import com.example.noteapp.utils.PreferenceHelper
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceHelper = PreferenceHelper()
        preferenceHelper.unit(requireContext())
        initialize()
        setupListener()
    }

    private fun initialize() {
        binding.viewPager2.adapter = OnBoardViewPagerAdapter(this@OnBoardFragment)
        TabLayoutMediator(binding.dotsTabLayout, binding.viewPager2) { _, _ -> }.attach()
    }

    private fun setupListener() {
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    binding.tvStart.visibility = View.VISIBLE
                    binding.tvSkip.visibility = View.INVISIBLE
                } else {
                    binding.tvStart.visibility = View.INVISIBLE
                    binding.tvSkip.visibility = View.VISIBLE
                }
            }
        })

        binding.tvSkip.setOnClickListener {
            if (binding.viewPager2.currentItem < 2) {
                binding.viewPager2.currentItem += 1
            }
        }

        binding.tvStart.setOnClickListener {
            preferenceHelper.isOnBoardShow = true
            findNavController().navigate(R.id.action_onBoardFragment_to_signUpFragment)
        }
    }
}
