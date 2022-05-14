package com.macandswiss.composeretrofittutorial

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.macandswiss.composeretrofittutorial.model.TodoListModel
import com.macandswiss.composeretrofittutorial.ui.theme.ComposeRetrofitTutorialTheme
import com.macandswiss.composeretrofittutorial.viewmodel.TodoListViewModel
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[TodoListViewModel::class.java]
        lifecycleScope.launchWhenCreated {
            val response = try {
                TodoAPIInstance.api.getTodos()
            } catch (e: Exception) {
                Log.d("None", "Error while handling API: $e")
                return@launchWhenCreated
            }
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                response.body()?.forEach { element ->
                    viewModel.todoItems.add(element)
                }
            }
        }
        setContent {
            ComposeRetrofitTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TodoList(viewModel)
                }
            }
        }
    }
}

@Composable
fun TodoList(viewModel: TodoListViewModel, modifier: Modifier = Modifier) {
    val list = remember { viewModel.todoItems }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        itemsIndexed(viewModel.todoItems) { index, item ->
            TodoItem(
                name = item.title,
                isChecked = item.completed,
                onCheckedChange = { viewModel.toggleItemAtIndex(index) } //Just a simple toggle box
            )
        }
    }
}

@Composable
fun TodoItem(
    name: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(0.9f)
    ) {
        Text(text = name, modifier = Modifier.fillMaxWidth())
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    TodoItem(name = "Test", false, {})
}
