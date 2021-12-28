package zar1official.simplenote.data.network.service

import retrofit2.http.GET
import zar1official.simplenote.data.network.entities.NetworkNote

interface NoteService {
    @GET("note.json?alt=media")
    suspend fun getNote(): NetworkNote
}
