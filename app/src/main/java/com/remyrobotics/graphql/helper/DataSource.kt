package com.remyrobotics.graphql.helper

import com.remyrobotics.graphql.GetLocaltionsQuery
import com.remyrobotics.graphql.model.Location
import com.remyrobotics.graphql.model.Resident
import java.util.ArrayList
import java.util.HashMap

/**
 * DataSource class for providing locations for Phone and Tablet
 */
object DataSource {

    const val ARG_LOCATION_ID = "location_id"

    var currentPage: Int = 1
    var maximumPage: Int = 1
    var keyword: String = ""

    /**
     * An array of location items.
     */
    var ITEMS: MutableList<Location> = ArrayList()

    /**
     * A map of location items, by ID.
     */
    var ITEM_MAP: MutableMap<String, Location> = HashMap()

    fun getResidents(item: GetLocaltionsQuery.Result): MutableList<Resident> {
        var residents: MutableList<Resident> = ArrayList()
        for (item in item.residents!!){
            val resident = Resident(name = item?.name, image = item?.image, species = item?.species)
            residents.add(resident)
        }
        return residents
    }

    fun addLocation(item: GetLocaltionsQuery.Result) {

        val location = Location(id = item.id, name = item.name, type = item.type, dimension = item.dimension, residents = getResidents(item))
        ITEMS.add(location)
        ITEM_MAP.put(location.id.toString(), location)
    }

    fun addLocations(locations: GetLocaltionsQuery.Locations) {
        for (item in locations.results!!)
            if (item != null) addLocation(item)
    }

    fun clear(word: String) {
        ITEMS.clear()
        ITEM_MAP.clear()
        currentPage = 1
        keyword = word
    }

}