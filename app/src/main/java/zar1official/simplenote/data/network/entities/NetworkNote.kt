package zar1official.simplenote.data.network.entities

import com.google.gson.annotations.SerializedName

data class NetworkNote(
    @SerializedName("title") val title: String,
    @SerializedName("content") val text: String
)