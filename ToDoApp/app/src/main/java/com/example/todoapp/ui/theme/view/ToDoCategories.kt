@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.todoapp.ui.theme.view

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp.navigation.Screen
import com.example.todoapp.R
import com.example.todoapp.ReorderHapticFeedbackType
import com.example.todoapp.data.dataclasses.Categories
import com.example.todoapp.intent.event.ToDoCategoryEvents
import com.example.todoapp.intent.state.ToDoCategoryStates
import com.example.todoapp.rememberReorderHapticFeedback
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.viewmodel.TodoCategoryViewModel
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyGridState

@Composable
fun ToDoMyLists(
    navController: NavController, toDoCategoryViewModel: TodoCategoryViewModel = hiltViewModel()
) {

    val state by toDoCategoryViewModel.state.collectAsState()
//    val allCategories by toDoCategoryViewModel.getCategories.collectAsState(initial = emptyList())
    var isLaunched by rememberSaveable {
        mutableStateOf(false)
    }
    if (!isLaunched) {
        isLaunched = true
        LaunchedEffect(key1 = Unit) {
            toDoCategoryViewModel.handleEvent(ToDoCategoryEvents.LoadToDoData)
        }
    }


    var isDialogOpen by remember {
        mutableStateOf(false)
    }

    ToDoListPageSetup(state, addIconClick = {
        isDialogOpen = !isDialogOpen
    }, navigateToMyDayPage = {
        navController.navigate(Screen.ToDoList.route)
    }, isListCardClick = {
        navController.currentBackStackEntry?.savedStateHandle?.set("category", it)
        navController.navigate(Screen.CategoryBasedToDoList.route)
    })
    if (isDialogOpen) {
        DialogBox(onDismissRequest = {
            isDialogOpen = !isDialogOpen
        }, onConfirmation = {
            isDialogOpen = !isDialogOpen
            println("Check for DialogBox $isDialogOpen")
            toDoCategoryViewModel.handleEvent(ToDoCategoryEvents.AddToDoData(it))
        })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListPageSetup(
    state: ToDoCategoryStates,
    addIconClick: () -> Unit,
    navigateToMyDayPage: () -> Unit,
    isListCardClick: (String) -> Unit
) {
    var categoryList: List<Categories> by remember {
        mutableStateOf(emptyList())
    }
    val pageTitle by remember {
        mutableStateOf("My Lists")
    }
    ToDoAppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Scaffold(modifier = Modifier.background(Color.White),
                containerColor = Color.White,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = pageTitle,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                            )
                        }, colors = TopAppBarDefaults.topAppBarColors(Color.White)
                    )
                }, bottomBar = {
                    BottomAppBar(
                        modifier = Modifier.height(120.dp), containerColor = Color.White
                    ) {
                        SetUpBottomAppBarNavigationIcons(navigateToMyDayPage)
                    }
                }) {

//                Box(modifier = Modifier.padding(it)) {

                println("Check for state: $state")
//                when (state) {
//                    is ToDoCategoryStates.Error -> {
//                        //Error
//                    }
//
//                    ToDoCategoryStates.Loading -> {
//                        LoadingBarSetUp()
//                    }
//
//                    is ToDoCategoryStates.Success -> {
//
//                        categoryList = state.listOfCategory
////                        AnimatedVisibility(
////                            visible = visible,
////                            enter = fadeIn(),
////                            exit = fadeOut()
////                        ) {
////                            ToDoListCategorySetup(
////                                loading = state.isLoading,
////                                it,
////                                categoryList,
////                                addIconClick,
////                                isListCardClick
////                            )
////                        }
//                    }
////                    }
//                }
                val visible by remember {
                    mutableStateOf(value = true)
                }
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    ToDoListCategorySetup(
                        state,
                        it,
                        addIconClick,
                        isListCardClick
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingBarSetUp() {

    Box(
        modifier = Modifier
//            .fillMaxSize()
            .size(100.dp)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(R.raw.blue_amoung_us_animation)
//        LinearProgressIndicator()
    }
}

@Composable
fun SetUpBottomAppBarNavigationIcons(navigateToMyDayPage: () -> Unit) {

    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .shadow(2.dp, RoundedCornerShape(50)),
        shape = RoundedCornerShape(50),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.clickable {
                navigateToMyDayPage()
            }, horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.checked),
                    contentDescription = null,
                    tint = Color(0xff257EBE),
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "All Tasks",
                    modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp),
                    fontSize = 12.sp,
                    color = Color(0xff257EBE),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Column(
                modifier = Modifier.clickable {
                    Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sevenday),
                    contentDescription = null,
                    tint = Color(0xff257EBE),
                    modifier = Modifier
                        .padding(2.dp)
                        .size(25.dp)
                )
                Text(
                    text = "7 Days",
                    modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp),
                    fontSize = 12.sp,
                    color = Color(0xff257EBE),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Column(
                modifier = Modifier.clickable {
                    Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bullseye),
                    contentDescription = null,
                    tint = Color(0xff257EBE),
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "My Day",
                    modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Color(0xff257EBE)
                )
            }

        }
    }
}

//@Composable
//fun BottomTabBar(navController: NavController) {
//    val list = listOf(NavigationItem.Home, NavigationItem.Profile, NavigationItem.History)
//    var selectedItem by remember {
//        mutableIntStateOf(0)
//    }
//    var currentRoute by remember {
//        mutableStateOf(NavigationItem.Home.route)
//    }
//    list.forEachIndexed { index, navigationItem ->
//        if (navigationItem.route == currentRoute) {
//
//            selectedItem = index
//        }
//    }
//    NavigationBar(
//        containerColor = Color.White, modifier = Modifier.background(Color.White)
//    ) {
//        list.forEachIndexed { index, navigationItem ->
//            NavigationBarItem(
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = Color(0xff257EBE)
//                ),
//                alwaysShowLabel = true,
//                selected = selectedItem == index,
//                modifier = Modifier
//                    .padding(0.dp, 20.dp, 0.dp, 0.dp)
//                    .size(30.dp),
//                icon = {
//                    Icon(
//                        painter = painterResource(id = navigationItem.icon),
//                        contentDescription = null,
//                        tint = Color(0xff257EBE)
//                    )
//                },
//                label = {
//                    Text(
//                        text = navigationItem.name,
//                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)
//                    )
//                },
//                onClick = {
//                    selectedItem = index
//                    currentRoute = navigationItem.route
//                    navController.navigate(navigationItem.route) {
//                        navController.graph.startDestinationRoute?.let {
//                            popUpTo(it) {
//                                saveState = true
//                            }
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                },
//            )
//        }
//    }
//}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoListCategorySetup(

    state: ToDoCategoryStates,
    padding: PaddingValues,
    onAddIconClick: () -> Unit,
    onListCardClick: (String) -> Unit
) {

    var categoryList: List<Categories> by remember {
        mutableStateOf(emptyList())
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    when (state) {
        is ToDoCategoryStates.Error -> {
            //Error
        }

        ToDoCategoryStates.Loading -> {
            LoadingBarSetUp()
        }

        is ToDoCategoryStates.Success -> {
            println("Used For ${state.listOfCategory}")
            categoryList = state.listOfCategory
            isLoading = state.isLoading
        }
    }
//    var categoryList by remember {
//        mutableStateOf(toDoListCategories)
//    }
    val haptic = rememberReorderHapticFeedback()
    val states = rememberLazyGridState()
    val reorderableLazyGridState = rememberReorderableLazyGridState(states) { from, to ->

        categoryList = categoryList.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
        haptic.performHapticFeedback(ReorderHapticFeedbackType.MOVE)
    }

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = Modifier.padding(padding),
        contentPadding = PaddingValues(
            top = 0.dp, start = 20.dp, end = 20.dp, bottom = 10.dp
        ),
        state = states,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            itemsIndexed(items = categoryList, key = { it, _ -> it }) { index, item ->

                ReorderableItem(reorderableLazyGridState, item) {
                    val interactionSource = remember { MutableInteractionSource() }
                    Card(
                        onClick = {
                            onListCardClick.invoke(categoryList[index].name ?: "")
                        },
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .shadow(2.dp)
                            .semantics {
                                customActions = listOf(
                                    CustomAccessibilityAction(
                                        label = "Move Before",
                                        action = {
                                            if (index > 0) {
                                                categoryList = categoryList
                                                    .toMutableList()
                                                    .apply {
                                                        add(index - 1, removeAt(index))
                                                    }
                                                true
                                            } else {
                                                false
                                            }
                                        }
                                    ),
                                    CustomAccessibilityAction(
                                        label = "Move After",
                                        action = {
                                            if (index < categoryList.size - 1) {
                                                categoryList = categoryList
                                                    .toMutableList()
                                                    .apply {
                                                        add(index + 1, removeAt(index))
                                                    }
                                                true
                                            } else {
                                                false
                                            }
                                        }
                                    ),
                                )
                            }
                            .draggableHandle(
                                onDragStarted = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.START)
                                },
                                onDragStopped = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.END)
                                }
                            ),
                        interactionSource = interactionSource,
                        colors = CardDefaults.cardColors(Color.White)
                    ) {
                        Box(modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()
                            .padding(10.dp)
                            .draggableHandle(
                                onDragStarted = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.START)
                                },
                                onDragStopped = {
                                    haptic.performHapticFeedback(ReorderHapticFeedbackType.END)
                                }
                            ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = categoryList[index].name ?: "Empty",
                                modifier = Modifier
                                    .background(
                                        Color.White
                                    )
                                    .padding(vertical = 20.dp)
                                    .draggableHandle (
                                        onDragStarted = {
                                            haptic.performHapticFeedback(ReorderHapticFeedbackType.START)
                                        },
                                        onDragStopped = {
                                            haptic.performHapticFeedback(ReorderHapticFeedbackType.END)
                                        }
                                    )
                                    .clearAndSetSemantics {  },
                                fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                    }
                }
            }
            item {

                if (isLoading) {

                    Card(
                        onClick = { },
                        modifier = Modifier.shadow(2.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(8.dp)

                    ) {
                        Box(
                            modifier = Modifier
                                .size(25.dp)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingCircleBarSetup()
                        }
                    }
                } else {
                    Card(
                        onClick = onAddIconClick,
                        modifier = Modifier.shadow(2.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .background(Color.White), contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = null,
                                tint = Color(0xff1171bf),
                                modifier = Modifier
                                    .padding(vertical = 20.dp)
                                    .background(
                                        Color.White
                                    )
                            )
                        }
                    }
                }
            }

        })
}

@Composable
fun ListCard(
    onClick: () -> Unit, content: @Composable () -> Unit
) {
    Card(modifier = Modifier
        .height(100.dp)
        .padding(0.dp, 10.dp, 0.dp, 10.dp)
        .fillMaxSize()
        .background(Color.White)
        .clickable {
            onClick.invoke()
        }
        .shadow(3.dp, RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(Color.White)

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun DialogBox(
    onDismissRequest: () -> Unit, onConfirmation: (Categories) -> Unit
) {

    var dialogTextField by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = { onDismissRequest() }) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp, 16.dp, 16.dp, 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Color.White),
        ) {

            Column(
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
            ) {

                OutlinedTextField(
                    value = dialogTextField,
                    onValueChange = {
                        dialogTextField = it
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp, 0.dp, 0.dp),

                    label = { Text(text = "Category") },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedLabelColor = Color.Black,
                        focusedBorderColor = Color.Black
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 20.dp, 0.dp, 20.dp),
                    horizontalArrangement = Arrangement.Absolute.Right
                ) {

                    Button(
                        onClick = {
                            onDismissRequest()
                        }, colors = ButtonDefaults.buttonColors(
                            Color.White
                        )
                    ) {
                        Text(text = "Dismiss", color = Color.Black, fontSize = 16.sp)
                    }
                    Button(
                        onClick = {
                            onConfirmation(Categories(dialogTextField, "", "", 0L, 0L))
                        }, colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Text(text = "Confirm", color = Color(0xff1171bf), fontSize = 16.sp)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreviewList() {

//    ToDoListPageSetup(listOf("Personal", "Home", "Working Place Task", "PlusIcon$$"),)
}