package com.remyrobotics.graphql.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.remyrobotics.graphql.GetLocaltionsQuery
import com.remyrobotics.graphql.R
import com.remyrobotics.graphql.adapter.LocationAdapter
import com.remyrobotics.graphql.helper.ApolloHttp
import com.remyrobotics.graphql.helper.DataSource
import kotlinx.android.synthetic.main.activity_locations.*
import kotlinx.android.synthetic.main.layout_location_list.*


/**
 * An activity representing a list of Locations.
 */

class LocationsActivity : BaseActivity() {

    private var twoPane: Boolean = false
    private lateinit var adapter: LocationAdapter

    /**
     * Default Methods
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)

        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * Custom Methods
     */

    private fun initViews() {
        setSupportActionBar(toolbar)
        toolbar.title = title

        ibSearch.setOnClickListener {
            searchWithKeyword()
        }
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                val keyword = s.toString().trim()
                if (keyword.isEmpty()) {
                    DataSource.clear(keyword)
                    apiLocations()
                }
            }
        })
        etSearch.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.getAction() === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchWithKeyword()
                    return true
                }
                return false
            }
        })

        locationRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (DataSource.currentPage < DataSource.maximumPage) {
                        DataSource.currentPage++
                        apiLocations()
                    }
                }
            }
        })

        if (findViewById<NestedScrollView>(R.id.detailContainer) != null) {
            twoPane = true
        }

        swipeContainer?.setOnRefreshListener(OnRefreshListener {
            val keyword = etSearch.text.toString().trim()
            DataSource.clear(keyword)
            apiLocations()
        })
        swipeContainer?.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        initAdapter()
        apiLocations()
    }

    private fun initAdapter() {
        adapter = LocationAdapter(this, DataSource.ITEMS, twoPane)
        locationRecyclerView.adapter = adapter
    }

    private fun searchWithKeyword(){
        val keyword = etSearch.text.toString().trim()
        DataSource.clear(keyword)
        apiLocations()
    }

    /**
     * Api Requests & Responses
     */

    private fun apiLocations() {
        showLoadDialog(this)
        swipeContainer?.isRefreshing = false

        val page = DataSource.currentPage
        val keyword = DataSource.keyword

        ApolloHttp.queryLocations(page,keyword){ res, err ->
            if(err == null){
                showLocaltions(res?.data?.locations)
            }else{
                // In case, server is failed with some reasons
            }
        }
    }

    private fun showLocaltions(locations: GetLocaltionsQuery.Locations?) {
        hideLoadDialog()

        runOnUiThread(Runnable {
            if (locations != null) {
                DataSource.maximumPage = locations.info?.pages!!
                DataSource.addLocations(locations)
                adapter.addLocations(locations)

                layNoData.visibility = View.GONE
                locationRecyclerView.visibility = View.VISIBLE
            }else{
                layNoData.visibility = View.VISIBLE
                locationRecyclerView.visibility = View.GONE
            }
        })
    }
}