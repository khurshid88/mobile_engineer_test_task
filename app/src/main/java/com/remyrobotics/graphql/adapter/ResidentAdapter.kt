package com.remyrobotics.graphql.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.remyrobotics.graphql.R
import com.remyrobotics.graphql.fragment.ResidentsFragment
import com.remyrobotics.graphql.model.Resident


class ResidentAdapter(
    private val parentFragment: ResidentsFragment,
    private val items: List<Resident>,
) : RecyclerView.Adapter<ResidentAdapter.ViewHolder>() {


    /**
     * Default Methods
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_resident,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        Glide.with(parentFragment)
            .load(item.image)
            .skipMemoryCache(true)
            .placeholder(R.drawable.ic_default)
            .error(R.drawable.ic_default)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .transform(CircleCrop())
            .into(holder.res_image)


        holder.res_name.text = item.name
        holder.res_species.text = item.species
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val res_image: ImageView = view.findViewById(R.id.res_image)
        val res_name: TextView = view.findViewById(R.id.res_name)
        val res_species: TextView = view.findViewById(R.id.res_species)
    }
}