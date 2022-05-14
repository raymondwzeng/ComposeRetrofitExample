package com.macandswiss.composeretrofittutorial

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
    The butter of our API. Actually makes the request. Apparently if you use DI (Dagger, Hilt), you don't need this.
 */
object TodoAPIInstance {
    val api: TodoAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoAPI::class.java)
    }
}