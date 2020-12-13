package com.example.weatherforecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherforecast.databinding.FragmentLocationsBinding

class FragmentLocations : Fragment() {

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpUI()
        return view
    }

    private fun setUpUI() {
        if (!resources.getBoolean(R.bool.splitView)) {
            binding.textView.setOnClickListener { findNavController().navigate(R.id.action_fragmentLocations_to_fragmentForecast) }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}