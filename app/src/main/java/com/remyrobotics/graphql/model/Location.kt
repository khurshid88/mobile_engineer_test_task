package com.remyrobotics.graphql.model

data class Location(val id: String?, val name: String?, val type: String?, val dimension: String?, val residents: List<Resident>?) {
    override fun toString(): String = name.toString()
}