package com.remyrobotics.graphql.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.remyrobotics.graphql.R
import com.remyrobotics.graphql.adapter.ResidentAdapter
import com.remyrobotics.graphql.helper.DataSource
import com.remyrobotics.graphql.model.Location

/**
 * A fragment representing Residents.
 */

class ResidentsFragment : Fragment() {

    private var location: Location? = null

    /**
     * Default Methods
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadArguments()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_residents, container, false)
        initRootViews(rootView)

        return rootView
    }

    /**
     * Custom Methods
     */

    fun loadArguments(){
        arguments?.let {
            if (it.containsKey(DataSource.ARG_LOCATION_ID)) {
                location = DataSource.ITEM_MAP[it.getString(DataSource.ARG_LOCATION_ID)]
                activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title = location?.name
            }
        }
    }

    fun initRootViews(rootView: View){
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.residentRecyclerView)
        refreshAdapter(recyclerView)
    }

    private fun refreshAdapter(recyclerView: RecyclerView) {
        val adapter = ResidentAdapter(this, location?.residents!!)
        recyclerView.adapter = adapter
    }
}