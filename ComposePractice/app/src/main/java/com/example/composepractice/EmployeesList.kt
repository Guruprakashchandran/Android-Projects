package com.example.composepractice

import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.composepractice.ui.theme.Black
import com.example.composepractice.ui.theme.ComposePracticeTheme
import com.example.composepractice.ui.theme.LightWhite

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeesDetails() {

    var employeeDetails: String by remember {
        mutableStateOf("")
    }
    ComposePracticeTheme {
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
        ) {

            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        title = {
                            TextField(
                                value = employeeDetails,
                                onValueChange = { employeeDetails = it },
                                textStyle = TextStyle(fontSize = 16.sp),
                                placeholder = {
                                    Text(
                                        text = "Search Employee By Name",
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                },
                                modifier = Modifier

                                    .padding(10.dp, 3.dp, 20.dp, 3.dp)
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .border(
                                        1.dp, MaterialTheme.colorScheme.secondary,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .background(
                                        MaterialTheme.colorScheme.background
                                    ),
                                colors = TextFieldDefaults.colors(unfocusedContainerColor = MaterialTheme.colorScheme.primary, focusedContainerColor = MaterialTheme.colorScheme.primary)
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background),
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { },
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
                        onClick = { },
                        modifier = Modifier
                            .padding(16.dp, 96.dp, 16.dp, 16.dp)
                            .size(60.dp),
                        containerColor = FloatingActionButtonDefaults.containerColor,
                        contentColor = MaterialTheme.colorScheme.tertiary
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_filter_alt_24),
                            contentDescription = "",

                        )
                    }
                }


            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(it)
                        .padding(20.dp, 20.dp, 20.dp, 0.dp)
                ) {
                    item {
                        for (i in 0 until 10) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            ) {

                                Card(
                                    modifier = Modifier
                                        .padding(10.dp, 10.dp, 10.dp, 10.dp)
                                        .width(60.dp)
                                        .height(60.dp)
                                        .background(MaterialTheme.colorScheme.primary)
                                ) {

                                    Image(
//                                    painter = painterResource(id = R.drawable.ic_launcher_background),
                                        painter = rememberAsyncImagePainter(model = "https://images.pexels.com/photos/65894/peacock-pen-alluring-yet-lure-65894.jpeg"),
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
                                        text = "Guruprakash",
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
                                            text = "Jambav",
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
                                            text = "Software Develper",
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
                }
            }
        }
    }

}

/*
BasicTextField(
value = employeeDetails,
onValueChange = {
    employeeDetails = it
},
modifier = Modifier
.padding(10.dp, 15.dp, 22.dp, 10.dp)
.fillMaxWidth()
.size(40.dp),
colors = TextFieldDefaults.colors(Color.White),
shape = RoundedCornerShape(20.dp)
)*/
