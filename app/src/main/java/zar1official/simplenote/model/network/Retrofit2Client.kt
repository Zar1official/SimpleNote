package zar1official.simplenote.model.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit2Client {

    companion object {
        const val BASE_URL = "https://firebasestorage.googleapis.com/v0/b/test-dfea4.appspot.com/o/"

        @Volatile
        private var INSTANCE: Retrofit? = null

        fun getInstance(): Retrofit =
            INSTANCE ?: synchronized(this) {
                val instance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(
                        GsonConverterFactory.create(
                            GsonBuilder()
                                .setLenient()
                                .create()
                        )
                    ).build()
                INSTANCE = instance
                instance
            }
    }
}