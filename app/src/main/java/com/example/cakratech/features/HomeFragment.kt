package com.example.cakratech.features

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cakratech.R
import com.example.cakratech.databinding.FragmentHomeBinding
import com.example.cakratech.adapter.BannerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageResIds = listOf(
            R.drawable.banner_1,
            R.drawable.banner_2
        )
        binding.viewPager.adapter = BannerAdapter(imageResIds)
        binding.apply {
            btn1.setOnClickListener {
                startActivity(Intent(requireContext(), Booking::class.java))
            }
            btn2.setOnClickListener {
                startActivity(Intent(requireContext(), Booking::class.java))
            }
            btn3.setOnClickListener {
                startActivity(Intent(requireContext(), Booking::class.java))
            }
            btn4.setOnClickListener {
                startActivity(Intent(requireContext(), Booking::class.java))
            }
        }
    }

}