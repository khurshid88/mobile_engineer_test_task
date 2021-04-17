package com.remyrobotics.graphql.model

data class Resident(val name: String?, val image: String?, val species: String?) {
    override fun toString(): String = name.toString()
}