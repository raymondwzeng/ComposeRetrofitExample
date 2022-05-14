package com.macandswiss.composeretrofittutorial

import com.macandswiss.composeretrofittutorial.model.TodoListModel
import retrofit2.Response
import retrofit2.http.GET

/*
    The bread of our Retrofit front-end
 */
interface TodoAPI {
    @GET("/todos")
    suspend fun getTodos(): Response<List<TodoListModel>>
}