package com.macandswiss.composeretrofittutorial.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.macandswiss.composeretrofittutorial.model.TodoListModel

/*
    The ViewModel will actually be in charge of our data handling.
 */
class TodoListViewModel : ViewModel() {
    var todoItems = mutableStateListOf<TodoListModel>()

    //This function needs to exist because mSLO doesn't pick up on inner item updates (1 degree of mutability invariant).
    fun toggleItemAtIndex(index: Int) { //Maybe not a great thing, but assume that we will always have a valid item in here...
        todoItems[index] = todoItems[index].let { item ->
            item.copy(completed = !item.completed) //Return this new item from the let block
        }
    }
}