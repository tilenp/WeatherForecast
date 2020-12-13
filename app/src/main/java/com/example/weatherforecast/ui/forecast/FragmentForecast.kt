package com.example.weatherforecast.ui.forecast

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherforecast.R
import com.example.weatherforecast.dagger.ComponentProvider
import com.example.weatherforecast.databinding.FragmentForecastBinding
import com.example.weatherforecast.repository.Forecast
import com.example.weatherforecast.utils.SchedulerProvider
import com.example.weatherforecast.viewModel.ForecastViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FragmentForecast : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ForecastViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as ComponentProvider).provideAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpViewModel()
        return view
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(ForecastViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable.add(
            viewModel.getTomorrowForecast()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe({
                    updateUI(it)
                }, {
                    System.out.println("TTT " + it.message)
                })
        )
    }

    private fun updateUI(forecast: Forecast) {
        with(binding) {
            Glide.with(image.context)
                .load(forecast.imagePath?.imageSize64)
                .into(image)
            weatherStateTextView.text = String.format(getString(R.string.weather_state_format), forecast.weatherStateName)
            minTempTextView.text = String.format(getString(R.string.min_temp_format), forecast.minTemp)
            maxTempTextView.text = String.format(getString(R.string.max_temp_format), forecast.maxTemp)
            windSpeedTextView.text = String.format(getString(R.string.wind_speed_format), forecast.windSpeed)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}