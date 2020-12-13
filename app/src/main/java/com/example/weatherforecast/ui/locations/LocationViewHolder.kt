package com.example.weatherforecast.ui.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.LocationRowBinding
import com.example.weatherforecast.repository.Location

class LocationViewHolder(
    private val binding: LocationRowBinding,
    private val onLocationClick: OnLocationClick
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var location: Location

    init {
        binding.root.setOnClickListener { onLocationClick.onLocationClick(location) }
    }

    fun bind(location: Location) {
        this.location = location
        binding.titleTextView.text = location.title
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onLocationClick: OnLocationClick
        ): LocationViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.location_row, parent, false)
            val binding: LocationRowBinding = LocationRowBinding.bind(view)
            return LocationViewHolder(binding, onLocationClick)
        }
    }
}