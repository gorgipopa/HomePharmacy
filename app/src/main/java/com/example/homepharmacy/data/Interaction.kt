package com.example.homepharmacy.data

import kotlinx.serialization.Serializable

@Serializable
data class Interaction(
    val id: Int,
    val substanceA: String,
    val substanceB: String,
    val description: String
)