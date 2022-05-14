package com.macandswiss.composeretrofittutorial.model

data class TodoListModel(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean //Although this is "mutable", since State management doesn't handle updates, we have to copy the item. Ew.
)