package com.example.weatherforecast.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentLocationsBinding
import com.example.weatherforecast.repository.Location

class FragmentLocations : Fragment(), OnLocationClick {

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: LocationAdapter

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
        adapter = LocationAdapter(this)
        val locations = listOf(Location(1, "Stockholm"), Location(2, "New York"), Location(3, "Mountain View"))
        adapter.setLocations(locations)
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(MyItemDecorator(requireContext()))
        }
    }

    override fun onLocationClick(location: Location) {
        if (!resources.getBoolean(R.bool.splitView)) {
            findNavController().navigate(R.id.action_fragmentLocations_to_fragmentForecast)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}