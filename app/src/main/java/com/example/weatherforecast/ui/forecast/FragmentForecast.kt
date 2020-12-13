package com.example.weatherforecast.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentForecastBinding

class FragmentForecast : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpUI()
        return view
    }

    private fun setUpUI() {
        with(binding) {
            weatherStateTextView.text =
                String.format(getString(R.string.weather_state_format), "Sunny")
            minTempTextView.text = String.format(getString(R.string.min_temp_format), 26)
            maxTempTextView.text = String.format(getString(R.string.max_temp_format), 30)
            windSpeedTextView.text = String.format(getString(R.string.wind_speed_format), 5)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}