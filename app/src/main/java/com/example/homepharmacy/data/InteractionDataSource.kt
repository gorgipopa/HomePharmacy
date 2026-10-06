package com.example.homepharmacy.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class InteractionDataSource(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun loadInteractions(): List<Interaction> = withContext(Dispatchers.IO) {
        val raw = context.assets
            .open("interactions.json")
            .bufferedReader()
            .use { it.readText() }
        json.decodeFromString<List<Interaction>>(raw)
    }
}