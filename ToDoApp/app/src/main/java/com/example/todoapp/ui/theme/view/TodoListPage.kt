@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.todoapp.ui.theme.view

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.data.dataclasses.UserToDoList
import com.example.todoapp.intent.event.TodoListEvents
import com.example.todoapp.intent.state.ToDoListStates
import com.example.todoapp.intent.state.TodoUiState
import com.example.todoapp.navigation.Screen
import com.example.todoapp.viewmodel.ToDoListViewModel
import com.zoho.catalyst.setup.ZCatalystApp

@Composable
fun ToDoListPage(
    navController: NavHostController,
    toDoListViewModel: ToDoListViewModel = hiltViewModel()
) {
    val content = remember { mutableStateOf("") }
    val uiState by toDoListViewModel.uiState.collectAsState()
    var isAddButtonLoading by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var isLaunched by remember {
        mutableStateOf(false)
    }
    val listOfToDos: List<UserToDoList> by remember {
        mutableStateOf(emptyList())
    }

    if (!isLaunched) {
        isLaunched = true
        LaunchedEffect(key1 = Unit) {
            toDoListViewModel.handleEvent(TodoListEvents.LoadToDoData)
        }
    }

    val state: ToDoListStates by toDoListViewModel.state.collectAsState()
    ScaffoldSetup(
        context,
        isAddButtonLoading,
        isAddButtonLoad = {
            isAddButtonLoading = it
        },
        state = state,
        uiState = uiState,
        content.value,
        navController,
        listOfToDos,
        addToDoButton = {
            toDoListViewModel.handleEvent(TodoListEvents.AddToDoData(it))
        },
        isTaskCompleted = {
            toDoListViewModel.handleEvent(TodoListEvents.UpdateData(it))
        },
        deleteToDo = {
            toDoListViewModel.handleEvent(TodoListEvents.DeleteData(it))
        },
        onTextChanged = {
            content.value = it
        },
        userSignout = {
            ZCatalystApp.getInstance().logout(success = {
                Toast.makeText(context, "User Sign-out Successfully!!!", Toast.LENGTH_SHORT).show()
                navController.navigate(Screen.ToDoSignInOrSignUp.route){
                    popUpTo(Screen.ToDoSignInOrSignUp.route){
                        inclusive = true
                        saveState = true
                    }
                }

            }, failure = {
                Toast.makeText(context, "User Sign-out Failed!!!", Toast.LENGTH_SHORT).show()
            })
        },
    )
}


@Composable
fun LoadingCircleBarSetup() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        CircularProgressIndicator(
//            color = Color(0xff257EBE)
//        )
        LottieAnimation(R.raw.blue_amoung_us_animation)
    }
}

@Composable
fun ScaffoldSetup(
    context: Context,
    isAddButtonLoading: Boolean,
    isAddButtonLoad: (Boolean) -> Unit,
    state: ToDoListStates,
    uiState: TodoUiState,
    content: String,
    navController: NavHostController,
    listOfToDos: List<UserToDoList>,
    addToDoButton: (String) -> Unit,
    isTaskCompleted: (UserToDoList) -> Unit,
    deleteToDo: (UserToDoList) -> Unit,
    onTextChanged: (String) -> Unit,
    userSignout: () -> Unit,
) {

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
                        text = "All Tasks",
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
                    MenuIconSetUp(context, userSignout)
                },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White
            ) {
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    },
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
                                    if (content.isNotEmpty() && !isAddButtonLoading) {
                                        isAddButtonLoad.invoke(true)
                                        addToDoButton(content)
                                        onTextChanged.invoke("")
                                    } else if (content.isEmpty()) {
                                        Toast.makeText(
                                            context,
                                            "Please!!! Enter Content!!!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Already Add Process Running!!!",
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
                        if (uiState.loading == "AddedItemLoading") {
                            println("Used Loading Button!!!")
                            Box(modifier = Modifier.padding(10.dp)) {
                                CircularProgressIndicator(
                                    color = Color(0xff257EBE)
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    if (content.isNotEmpty()) {
                                        isAddButtonLoad(true)
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
        content = { padding ->

            if (uiState.loading == "PageLoading") {

                println("Circular Loading Bar Setup!!!")
                LoadingCircleBarSetup()
            } else {

                AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                    SetUpToDoListItems(
                        uiState,
                        "",
                        false,
                        padding,
                        isTaskCompleted,
                        deleteToDo
                    )
                }
            }
        }
    )
}

@Composable
fun MenuIconSetUp(context: Context, userSignout: () -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        IconButton(onClick = {
            expanded = true
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
    }
    DropdownMenu(expanded = expanded, onDismissRequest = {
        expanded = false
    }) {
        DropdownMenuItem(text = {
            Text(text = "Signout", fontSize = 16.sp, modifier = Modifier.padding(start = 5.dp))
        }, onClick = {
            expanded = false
            userSignout.invoke()
        })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetUpToDoListItems(
    uiState: TodoUiState,
    createdTime: String,
    isLoading: Boolean,
    it: PaddingValues,
    isTaskCompleted: (UserToDoList) -> Unit,
    deleteToDo: (UserToDoList) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(it)
    ) {
//        items(items = toDoLists, key = { it.rowId }) {
        items(items = uiState.data ?: emptyList(), key = { it.rowId }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)
                    .animateItemPlacement(
                        tween(durationMillis = 700, easing = LinearEasing)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemsSetup(it, isTaskCompleted, createdTime, isLoading, deleteToDo)
            }

        }
    }
}

@Composable
fun ItemsSetup(
    it: UserToDoList,
    isTaskCompleted: (UserToDoList) -> Unit,
    createdTime: String,
    isLoading: Boolean,
    deleteToDo: (UserToDoList) -> Unit
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
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()

    ) {
        Box(
            modifier = Modifier
                .weight(0.5f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = it.text,
                color = Color.Black,
                textDecoration = if (it.status == "Completed") {
                    println("Used For Data Line Through " + it.text)
                    TextDecoration.LineThrough
                } else {
                    println("Used For Data None " + it.text)
                    TextDecoration.None
                },
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
        } else if (it.createdTime == createdTime && isLoading) {
            Surface(
                modifier = Modifier
                    .size(30.dp)
                    .padding(2.dp)
            ) {
                LoadingCircleBarSetup()
            }
        } else {
            Spacer(modifier = Modifier.width(50.dp)) // Spacer to maintain the default padding when delete icon is not visible
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ToDoListPage(navController = rememberNavController())
}
