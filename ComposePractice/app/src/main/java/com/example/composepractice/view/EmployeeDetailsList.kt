@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composepractice.view

import android.database.Cursor
import com.example.composepractice.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composepractice.EmployeeEntity
import com.example.composepractice.events.EmployeeListEvents
import com.example.composepractice.navigation.screen.Screen
import com.example.composepractice.states.EmployeesListState
import com.example.composepractice.states.FilterTypes
import com.example.composepractice.states.FilterUiData
import com.example.composepractice.viewmodel.EmployeeDetailViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun EmployeesDetailsList() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.EmployeeDetailList.route) {
        composable(Screen.EmployeeDetailList.route) {
            EmployeeList(navController)
        }
        composable(Screen.EmployeeProfilePage.route) {
            val serialId =
                navController.previousBackStackEntry?.savedStateHandle?.get<Long>("employeeId")
            if (serialId != null) {
                EmployeeProfile(navController, serialId)
            }
        }
        composable(Screen.InsertOrModifyEmployeeDetail.route) {
            val serialId =
                navController.previousBackStackEntry?.savedStateHandle?.get<Long>("employeeId")
            if (serialId != null) {
                InsertAndModifyEmployeeDetail(serialId, navController)
            }
        }
    }
}

@Composable
fun EmployeeList(navController: NavController) {
    val employeeDetailViewModel: EmployeeDetailViewModel = hiltViewModel()

    val designationKey = rememberSaveable {
        mutableStateOf("ALL")
    }
    val searchingName = rememberSaveable {
        mutableStateOf("")
    }
    val bottomSheetList = listOf(
        FilterUiData(key = "ALL", FilterTypes.ALL.type),
        FilterUiData(key = "Trainee", FilterTypes.TRAINEE.type),
        FilterUiData(key = "Employee", FilterTypes.EMPLOYEE.type),
        FilterUiData(key = "Team Leader", FilterTypes.TEAM_LEADER.type),
        FilterUiData(key = "Manager", FilterTypes.MANAGER.type),
        FilterUiData(key = "Admin", FilterTypes.ADMIN.type),
        FilterUiData(key = "Super Admin", FilterTypes.SUPER_ADMIN.type),
    )
    val isBottomSheetOpen = remember {
        mutableStateOf(false)
    }
    ScaffoldSetup(
        navController = navController,
        searchingName.value,
        isBottomSheetOpen,
        bottomSheetList,
        searchBarOnValueChange = {
            searchingName.value = it
            employeeDetailViewModel.handleEvent(
                EmployeeListEvents.LoadData(designationKey.value, searchingName.value)
            )
        },
        //Filter On Click Lambda function
        onFilterChange = {

            //close sheet after selecting filter
            isBottomSheetOpen.value = !isBottomSheetOpen.value

            designationKey.value = if (it.value == FilterTypes.ALL.type) {
                "ALL"
            } else {
                it.key
            }

            employeeDetailViewModel.selectedFilterType = it.value

            employeeDetailViewModel.handleEvent(
                EmployeeListEvents.LoadData(
                    designation = designationKey.value,
                    searchingName.value
                ),

                )
        }

    )
}

@Composable
fun LoadingProgressBar() {

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        LinearProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun                                 ScaffoldSetup(
    navController: NavController,
    searchingName: String,
    isBottomSheetOpen: MutableState<Boolean>,
    bottomSheetList: List<FilterUiData>,
    searchBarOnValueChange: (String) -> Unit,
    onFilterChange: (FilterUiData) -> Unit
) {

    val employeeDetailViewModel: EmployeeDetailViewModel = hiltViewModel()
    val sheetState = rememberModalBottomSheetState()
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    if (isBottomSheetOpen.value) {
        ModalBottomSheet(onDismissRequest = {
            isBottomSheetOpen.value = false
        }, sheetState = sheetState, containerColor = MaterialTheme.colorScheme.primary) {
            BottomSheetContentSetup(
                bottomSheetList,
                employeeDetailViewModel.selectedFilterType,
                onFilterChange
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    TextField(
                        value = searchingName,
                        onValueChange = {
                            searchBarOnValueChange.invoke(it)
                        },
                        textStyle = TextStyle(fontSize = 16.sp),
                        placeholder = {
                            Text(
                                text = "Search Employee By Name",
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
//                        cursorBrush = SolidColor(MaterialTheme.colorScheme.secondary),
                        modifier = Modifier
                            .padding(10.dp, 3.dp, 20.dp, 3.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .border(
                                0.5.dp, MaterialTheme.colorScheme.secondary,
                                RoundedCornerShape(5.dp)
                            )
                            .background(
                                MaterialTheme.colorScheme.background
                            ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background),
            )
        },
        snackbarHost = {
                       SnackbarHost(hostState = snackBarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "employeeId",
                        0L
                    )
                    navController.navigate(Screen.InsertOrModifyEmployeeDetail.route)
                },
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 16.dp),
                containerColor = FloatingActionButtonDefaults.containerColor,
                contentColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
            FloatingActionButton(
                onClick = {
                    isBottomSheetOpen.value = true
                },
                modifier = Modifier
                    .padding(16.dp, 96.dp, 16.dp, 16.dp)
                    .size(60.dp),
                containerColor = FloatingActionButtonDefaults.containerColor,
                contentColor = MaterialTheme.colorScheme.tertiary
            ) {
                Box(modifier = Modifier) {

                    Icon(
                        painter = painterResource(id = R.drawable.outline_filter_alt_24),
                        contentDescription = "",
                    )
                    if (employeeDetailViewModel.selectedFilterType != "ALL") {
                        Icon(
                            painter = painterResource(id = R.drawable.circle),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(15.dp, 3.dp, 0.dp, 0.dp)
                                .size(7.dp),
                            tint = Color.Red
                        )
                    }
                }
            }
            /*ExtendedFloatingActionButton(
                text = { Text("Show snackbar") },
                icon = { Icon(Icons.Filled.Favorite, contentDescription = "") },
                onClick = {
                    scope.launch {
                        val result = snackBarHostState
                            .showSnackbar(
                                message = "Snackbar",
                                actionLabel = "Action",
                                // Defaults to SnackbarDuration.Short
                                duration = SnackbarDuration.Indefinite
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                *//* Handle snackbar action performed *//*
                            }
                            SnackbarResult.Dismissed -> {
                                *//* Handle snackbar dismissed *//*
                            }
                        }
                    }
                }
            )*/
        }
    ) {

        val state by employeeDetailViewModel.employeesList.collectAsState()
        var initialFetchCompleted by rememberSaveable {
            mutableStateOf(false)
        }
        if (!initialFetchCompleted) {
            LaunchedEffect(key1 = Unit) {

                employeeDetailViewModel.loadEmployeeDetails(
                    EmployeeListEvents.LoadData("ALL", "")
                )
                initialFetchCompleted = true
            }
        }

        when (state) {
            EmployeesListState.Loading -> {
                LoadingProgressBar()
            }

            is EmployeesListState.Success -> {
                val data = (state as EmployeesListState.Success).employees
                    //rememberSaveable { (state as EmployeesListState.Success).employees.toMutableList() }
                if (data.isNotEmpty()) {
                    LazyListSetup(data, navController, employeeDetailViewModel, it)
                } else {
                    SetupNoList()
                }
            }

            is EmployeesListState.Error -> {
                val msg = (state as EmployeesListState.Error).message
                ShowErrorMessage(msg)
            }
        }
    }
}


@Composable
fun BottomSheetContentSetup(
    bottomSheetList: List<FilterUiData>,
    selectedFilterType: String,
    onFilterChange: (FilterUiData) -> Unit
) {

    val underLine = MaterialTheme.colorScheme.secondary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.primary)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.primary)
                .drawBehind {

                    val borderSize = 2.dp.toPx()
                    drawLine(
                        color = underLine,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = borderSize
                    )
                }
                .padding(20.dp, 5.dp, 20.dp, 24.dp)
        ) {

            Text(
                text = "Designation",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight(800)
            )
        }

        Spacer(modifier = Modifier.padding(0.dp,0.dp,0.dp,10.dp))

        bottomSheetList.forEach {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(20.dp, 5.dp, 20.dp, 15.dp)
                    .clickable {
                        onFilterChange.invoke(it)
                    }

            ) {
                Text(text = it.key, fontSize = 17.sp, color = MaterialTheme.colorScheme.secondary)
                if (selectedFilterType == it.value && selectedFilterType != "ALL") {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.padding(0.dp, 0.dp, 5.dp, 0.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(0.dp,0.dp,0.dp,20.dp))
    }
}

@Composable
fun LazyListSetup(
    data: List<EmployeeEntity>,
    navController: NavController,
    employeeDetailViewModel: EmployeeDetailViewModel,
    padding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .padding(padding)
            .padding(0.dp, 10.dp, 0.dp, 0.dp)
    ) {
        items(items = data, key = { it.id }) {

            SetupEmployeeDetailsList(
                data = it,
                data,
                navController = navController,
                employeeDetailViewModel,
                padding
            )
        }
    }
}

@Composable
fun SetupNoList() {

    Text(
        text = "No Records",
        fontSize = 30.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ShowErrorMessage(msg: String) {

    Text(
        text = msg,
        fontSize = 30.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SetupEmployeeDetailsList(
    data: EmployeeEntity,
    datas: List<EmployeeEntity>,
    navController: NavController,
    employeeDetailViewModel: EmployeeDetailViewModel,
    padding: PaddingValues
) {

    val edit = SwipeAction(
        onSwipe = {
            navController.currentBackStackEntry?.savedStateHandle?.set("employeeId", data.id)
            navController.navigate(Screen.InsertOrModifyEmployeeDetail.route)
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier.padding(16.dp),
                tint = Color.White
            )
        },
        background = Color.Green
    )

    val delete = SwipeAction(
        onSwipe = {
//                  employeeDetailViewModel.deleteFromList(data)
            employeeDetailViewModel.deleteEmployeeDetailsById(data.id)
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier.padding(16.dp),
                tint = Color.White
            )
        },
        background = Color.Red
    )

    SwipeableActionsBox(
        swipeThreshold = 100.dp,
        startActions = listOf(edit),
        endActions = listOf(delete)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "employeeId", data.id
                    )
                    navController.navigate(Screen.EmployeeProfilePage.route)
                }
                .background(MaterialTheme.colorScheme.background)
        ) {

            Card(
                modifier = Modifier
                    .padding(20.dp, 10.dp, 10.dp, 10.dp)
                    .width(60.dp)
                    .height(60.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ) {

                Image(
                    painter = painterResource(id = employeeDetailViewModel.getImageId(data.id)),
//                    painter = rememberAsyncImagePainter(model = "https://images.pexels.com/photos/65894/peacock-pen-alluring-yet-lure-65894.jpeg"),
                    contentDescription = "Flower",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.surface),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {

                Text(
                    text = data.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(15.dp, 12.dp, 10.dp, 5.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 16.sp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Text(
                        text = data.department,
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(15.dp, 0.dp, 0.dp, 10.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "-",
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(8.dp, 0.dp, 0.dp, 10.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = data.designation,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(8.dp, 0.dp, 10.dp, 10.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

