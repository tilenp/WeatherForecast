package com.example.weatherforecast.ui.locations

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.R
import com.example.weatherforecast.dagger.ComponentProvider
import com.example.weatherforecast.databinding.FragmentLocationsBinding
import com.example.weatherforecast.repository.Location
import com.example.weatherforecast.utils.SchedulerProvider
import com.example.weatherforecast.viewModel.LocationsViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FragmentLocations : Fragment(), OnLocationClick {

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var schedulerProvider: SchedulerProvider
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LocationsViewModel
    private lateinit var adapter: LocationAdapter
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
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpViewModel()
        setUpUI()
        return view
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(LocationsViewModel::class.java)
    }

    private fun setUpUI() {
        adapter = LocationAdapter(this)
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(MyItemDecorator(requireContext()))
        }
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable.add(
            viewModel.getLocations()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe({
                    showLocations(it)
                }, {})
        )
    }

    private fun showLocations(locations: List<Location>) {
        adapter.setLocations(locations)
    }

    override fun onLocationClick(location: Location) {
        if (!resources.getBoolean(R.bool.splitView)) {
            findNavController().navigate(R.id.action_fragmentLocations_to_fragmentForecast)
        }
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}