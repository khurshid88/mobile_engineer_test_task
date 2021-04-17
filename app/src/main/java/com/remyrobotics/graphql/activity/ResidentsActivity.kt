package com.remyrobotics.graphql.activity

import android.os.Bundle
import android.view.MenuItem
import com.remyrobotics.graphql.fragment.ResidentsFragment
import com.remyrobotics.graphql.R
import com.remyrobotics.graphql.helper.DataSource

/**
 * An activity representing a list of Residents.
 */

class ResidentsActivity : BaseActivity() {

    /**
     * Default Methods
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_residents)
        setSupportActionBar(findViewById(R.id.detail_toolbar))

        initViews(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    finish()
                    //navigateUpTo(Intent(this, LocationsActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    /**
     * Custom Methods
     */

    fun initViews(savedInstanceState: Bundle?){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (savedInstanceState == null) {
            val fragment = ResidentsFragment().apply {
                arguments = Bundle().apply {
                    putString(DataSource.ARG_LOCATION_ID, intent.getStringExtra(DataSource.ARG_LOCATION_ID))
                }
            }
            supportFragmentManager.beginTransaction().add(R.id.detailContainer, fragment).commit()
        }
    }
}