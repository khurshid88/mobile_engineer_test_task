package com.remyrobotics.graphql.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.remyrobotics.graphql.GetLocaltionsQuery
import com.remyrobotics.graphql.R
import com.remyrobotics.graphql.activity.ResidentsActivity
import com.remyrobotics.graphql.activity.LocationsActivity
import com.remyrobotics.graphql.fragment.ResidentsFragment
import com.remyrobotics.graphql.helper.DataSource
import com.remyrobotics.graphql.model.Location


class LocationAdapter(
    private val parentActivity: LocationsActivity,
    private val items: List<Location>,
    private val twoPane: Boolean
) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    /**
     * Default Methods
     */

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Location
            if (twoPane) {
                val fragment = ResidentsFragment().apply {
                    arguments = Bundle().apply {
                        putString(DataSource.ARG_LOCATION_ID, item.id)
                    }
                }
                parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.detailContainer, fragment)
                        .commit()
            } else {
                val intent = Intent(v.context, ResidentsActivity::class.java).apply {
                    putExtra(DataSource.ARG_LOCATION_ID, item.id)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_location,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.loc_id.text = item.id
        holder.loc_name.text = item.name
        holder.loc_type.text = item.type
        holder.loc_dimension.text = item.dimension

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val loc_id: TextView = view.findViewById(R.id.loc_id)
        val loc_name: TextView = view.findViewById(R.id.loc_name)
        val loc_type: TextView = view.findViewById(R.id.loc_type)
        val loc_dimension: TextView = view.findViewById(R.id.loc_dimension)
    }

    /**
     * Custom Methods
     */

    fun addLocation(item: GetLocaltionsQuery.Result) {
        val location = Location(id = item.id, name = item.name, type = item.type, dimension = item.dimension,residents = DataSource.getResidents(item))
        items.toMutableList().add(location)
    }

    fun addLocations(locations: GetLocaltionsQuery.Locations) {
        for (item in locations.results!!)
            if (item != null) addLocation(item)
        notifyDataSetChanged()
    }
}