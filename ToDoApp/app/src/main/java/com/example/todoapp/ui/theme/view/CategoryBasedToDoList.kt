@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.todoapp.ui.theme.view

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.data.dataclasses.UserToDoList
import com.example.todoapp.intent.event.CategoryBasedTodoEvents
import com.example.todoapp.intent.state.CategoryBasedToDoStates
import com.example.todoapp.viewmodel.CategoryBasedTodoViewModel
import java.util.Locale

@Composable

fun CategoryBasedToDoList(
    navController: NavHostController,
    category: String?,
    categoryBasedTodoViewModel: CategoryBasedTodoViewModel = hiltViewModel()
) {
    val content = remember { mutableStateOf("") }
    val context = LocalContext.current
    var isLaunched by remember {
        mutableStateOf(false)
    }
    var isAddedLoading by remember {
        mutableStateOf(false)
    }
    if (!isLaunched) {
        isLaunched = true
        LaunchedEffect(key1 = Unit) {
            categoryBasedTodoViewModel.handleEvent(
                CategoryBasedTodoEvents.LoadData(
                    category ?: "Personal"
                )
            )
        }
    }
    val state: CategoryBasedToDoStates by categoryBasedTodoViewModel.state.collectAsState()
    ScaffoldSetupBasedCategory(
        context,
        isAddedLoading,
        category = category,
        states = state,
        content.value,
        navController,
        addToDoButton = {
            categoryBasedTodoViewModel.handleEvent(
                CategoryBasedTodoEvents.AddToDo(
                    category = category ?: "", it
                )
            )
        }, isTaskCompleted = {
            categoryBasedTodoViewModel.handleEvent(
                CategoryBasedTodoEvents.UpdateToDo(
                    category = category ?: "", it
                )
            )
        }, deleteToDo = {
            categoryBasedTodoViewModel.handleEvent(
                CategoryBasedTodoEvents.DeleteToDo(
                    category = category ?: "", it
                )
            )
        }, onTextChanged = {
            content.value = it
        }) {
        isAddedLoading = it
    }


}

@Composable
fun ScaffoldSetupBasedCategory(
    context: Context,
    isAddedLoading: Boolean,
    category: String?,
    states: CategoryBasedToDoStates,
    content: String,
    navController: NavHostController,
    addToDoButton: (String) -> Unit,
    isTaskCompleted: (UserToDoList) -> Unit,
    deleteToDo: (UserToDoList) -> Unit,
    onTextChanged: (String) -> Unit,
    isAddedLoad: (Boolean) -> Unit
) {
    var listOfToDos: List<UserToDoList> by remember {
        mutableStateOf(emptyList())
    }
    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    Color.White
                ),
                title = {
                    Text(
                        text = category?.uppercase(Locale.ROOT) ?: "NO NAME",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        color = Color.Black,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
                    }) {
                        Card(
                            modifier = Modifier
                                .size(30.dp)
                                .shadow(2.dp, CircleShape),
                            colors = CardDefaults.cardColors(Color.White)
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xff257EBE),
                                modifier = Modifier
                                    .padding(3.dp)
                                    .background(
                                        Color.White
                                    )
                            )
                        }

                    }
                    IconButton(onClick = {
                        Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
                    }) {
                        Card(
                            modifier = Modifier
                                .size(30.dp)
                                .shadow(2.dp, CircleShape),
                            colors = CardDefaults.cardColors(Color.White)
                        ) {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier
                                    .padding(3.dp)
                                    .background(
                                        Color.White
                                    )
                            )
                        }
                    }
                },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(30.dp, 10.dp, 0.dp, 0.dp)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.Home,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = Color(0xff257EBE),
                    )
                }

                Surface(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 0.dp, bottom = 10.dp, end = 10.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(50))
                        .shadow(2.dp, CircleShape),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        TextField(
                            value = content,
                            onValueChange = {
                                onTextChanged.invoke(it)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(start = 10.dp)
                                .weight(1f)
                                .alignByBaseline(),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (content.isNotEmpty() && !isAddedLoading) {
                                        addToDoButton(content)
                                        onTextChanged.invoke("")
                                        isAddedLoad.invoke(true)
                                    } else if (content.isEmpty()) {
                                        Toast.makeText(
                                            context,
                                            "Please!!! Enter content!!!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Already!! Item Adding process!!! Wait!!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            ),
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontSize = 20.sp,
                            ),
                            placeholder = {
                                Text(
                                    text = "I Want to...",
                                    fontSize = 20.sp,
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                focusedIndicatorColor = Color.White,
                                unfocusedIndicatorColor = Color.White,
                                disabledIndicatorColor = Color.White,
                                unfocusedPlaceholderColor = Color(0xFFb5b3b3)
                            )
                        )
                        if (isAddedLoading) {
                            Box(modifier = Modifier.padding(10.dp)) {
                                CircularProgressIndicator(
                                    color = Color(0xff257EBE)
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    if (content.isNotEmpty()) {
                                        addToDoButton(content)
                                        onTextChanged.invoke("")
                                    }
                                },
                                colors = IconButtonDefaults.iconButtonColors(Color.White),
                                modifier = Modifier.padding(end = 10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.up_arrow),
                                    contentDescription = null,
                                    tint = Color(0xff257EBE)
                                )
                            }
                        }
                    }
                }
            }
        },
        content = {
            Box(modifier = Modifier.padding(it))
            when (states) {
                CategoryBasedToDoStates.Loading -> {
                    LoadingBarSetUp()
                }

                is CategoryBasedToDoStates.Error -> {
                    // Error
                }


                is CategoryBasedToDoStates.Success -> {

                    listOfToDos = states.toDoList
                    isAddedLoad.invoke(false)
                    AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                        SetUpCategoryBasedToDoList(
                            "",
                            false,
                            category,
                            it,
                            listOfToDos,
                            isTaskCompleted,
                            deleteToDo
                        )
                    }
                }

                is CategoryBasedToDoStates.ItemLoading -> {
                    println("Used For States is Reached!!!$listOfToDos")
                    AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                        SetUpCategoryBasedToDoList(
                            states.toDoList.createdTime,
                            states.isLoading,
                            category,
                            it,
                            listOfToDos,
                            isTaskCompleted,
                            deleteToDo
                        )
                    }
                }

                CategoryBasedToDoStates.AddedItemLoading -> {
                    isAddedLoad.invoke(true)
                    AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                        SetUpCategoryBasedToDoList(
                            "",
                            false,
                            category,
                            it,
                            listOfToDos,
                            isTaskCompleted,
                            deleteToDo
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun SetUpCategoryBasedToDoList(
    createdTime: String,
    isLoading: Boolean,
    category: String?,
    it: PaddingValues,
    toDoLists: List<UserToDoList>,
    isTaskCompleted: (UserToDoList) -> Unit,
    deleteToDo: (UserToDoList) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(it)
            .fillMaxSize()
    ) {
        items(items = toDoLists, key = { it.createdTime }) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = {
                    isTaskCompleted(it)
                }) {

                    Card(
                        border = if (it.status == "InCompleted") {
                            BorderStroke(1.dp, Color.Black)
                        } else {
                            BorderStroke(1.dp, Color.White)
                        },
                        colors = CardDefaults.cardColors(Color.White),
                        shape = CircleShape,
                        modifier = Modifier.size(25.dp)
                    ) {

                        if (it.status == "Completed") {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFFb5b3b3)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Align items vertically,
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Box(
                        modifier = Modifier
                            .weight(0.5f) // Default padding for the text
                            .align(Alignment.CenterVertically) // Align Box vertically
                    ) {
                        Text(
                            text = it.text,
                            color = Color.Black,
                            textDecoration = if (it.status == "Completed") TextDecoration.LineThrough else TextDecoration.None,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (it.status == "Completed" && createdTime != it.createdTime) {
                        IconButton(
                            onClick = { deleteToDo(it) },
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            Card(
                                modifier = Modifier
                                    .size(30.dp)
                                    .shadow(2.dp, CircleShape),
                                colors = CardDefaults.cardColors(Color.White)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = null,
                                    modifier = Modifier.padding(3.dp),
                                    tint = Color(0xFFb5b3b3)
                                )
                            }
                        }

                    } else if (createdTime == it.createdTime && isLoading) {
                        Surface(
                            modifier = Modifier
                                .size(30.dp)
//                                .shadow(2.dp, CircleShape)
                                .padding(2.dp),
//                            color =Color.White
                        ) {
                            LoadingCircleBarSetup()
                        }
                    } else {
                        Spacer(modifier = Modifier.width(50.dp)) // Spacer to maintain the default padding when delete icon is not visible
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBasedCategory() {
    ToDoListPage(navController = rememberNavController())
}
