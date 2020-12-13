package com.example.weatherforecast.ui.locations

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.repository.Location
import java.util.ArrayList

class LocationAdapter(
    private val onLocationClick: OnLocationClick
) : RecyclerView.Adapter<LocationViewHolder>() {

    private var locations: List<Location> = ArrayList()

    fun setLocations(locations: List<Location>) {
        this.locations = locations
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder.create(parent, onLocationClick)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    override fun getItemCount(): Int {
        return locations.size
    }
}