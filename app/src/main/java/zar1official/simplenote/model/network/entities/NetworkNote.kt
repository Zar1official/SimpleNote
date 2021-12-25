package zar1official.simplenote.model.network.entities

import com.google.gson.annotations.SerializedName

data class NetworkNote(
    @SerializedName("title") val title: String,
    @SerializedName("content") val text: String
)