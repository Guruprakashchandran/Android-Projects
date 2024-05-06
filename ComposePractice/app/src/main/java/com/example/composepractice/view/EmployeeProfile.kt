package com.example.composepractice.view

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composepractice.EmployeeEntity
import com.example.composepractice.events.EmployeeListEvents
import com.example.composepractice.states.EmployeeProfileState
import com.example.composepractice.ui.theme.ComposePracticeTheme
import com.example.composepractice.viewmodel.EmployeeProfileViewModel

@Composable
fun EmployeeProfile(navController: NavController, employeeId: Long) {

    val employeeProfileViewModel: EmployeeProfileViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .verticalScroll(rememberScrollState()),
    ) {

        Log.e("emp", employeeId.toString())
        ShowEmployeeProfileDetails(navController, employeeId, employeeProfileViewModel)
    }
}


@Composable
fun ShowEmployeeProfileDetails(
    navController: NavController,
    employeeId: Long,
    employeeProfileViewModel: EmployeeProfileViewModel
) {

    val state by employeeProfileViewModel.state.collectAsState()

    LaunchedEffect(key1 = EmployeeListEvents.LoadProfileData(employeeId)) {

        employeeProfileViewModel.handleEvent(EmployeeListEvents.LoadProfileData(employeeId))
    }
    when (state) {
        EmployeeProfileState.Loading -> {
            LoadingProgressBar()
        }

        is EmployeeProfileState.Success -> {
            val employee = (state as EmployeeProfileState.Success).employee
            ShowContent(employee, navController, employeeProfileViewModel)

        }

        is EmployeeProfileState.Error -> {

            val msg = (state as EmployeeProfileState.Error).message
            ShowError(msg)
        }
    }

}

@Composable
fun ShowError(msg: String) {

    Text(text = msg)
}

@Composable
fun ShowContent(
    employee: EmployeeEntity,
    navController: NavController,
    employeeProfileViewModel: EmployeeProfileViewModel
) {

    Box(
        modifier = Modifier
            .height(330.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = employeeProfileViewModel.getImageId(employee.id)),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .padding(30.dp, 30.dp, 0.dp, 0.dp)
                .size(40.dp)
                .background(MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(10.dp))
        ) {

            IconButton(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier
                    .padding(10.dp),
                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
    Spacer(modifier = Modifier.padding(20.dp))


    SetUpNameAndMailId(navController, employee)
    AboutEmployee(navController, employee)
}

@Composable
fun AboutEmployee(navController: NavController, employee: EmployeeEntity) {

    Text(
        text = "About",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        maxLines = 1,
        fontWeight = FontWeight(520),
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(15.dp))
    Text(
        text = "Department",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        fontWeight = FontWeight(520),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Text(
        text = employee.department,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(15.dp))
    Text(
        text = "Designation",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        fontWeight = FontWeight(520),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Text(
        text = employee.designation,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(15.dp))
    Text(
        text = "Employee id",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        fontWeight = FontWeight(520),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Text(
        text = employee.employee_id,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(15.dp))
    Text(
        text = "Email id",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        fontWeight = FontWeight(520),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Text(
        text = employee.mail_id.toString(),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = Color.Blue,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(15.dp))
    Text(
        text = "Mobile No",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        fontWeight = FontWeight(520),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Text(
        text = employee.mobile_no.toString(),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = Color.Blue,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(15.dp))
    Text(
        text = "Date Of Birth",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        fontWeight = FontWeight(520),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Text(
        text = employee.date_of_birth,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(15.dp))
    Text(
        text = "Salary",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        fontWeight = FontWeight(520),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Text(
        text = employee.salary.toString(),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Left
    )
    Spacer(modifier = Modifier.padding(20.dp))
}

@Composable
fun SetUpNameAndMailId(navController: NavController, data: EmployeeEntity) {
    Text(
        text = data.name,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        maxLines = 1,
        fontWeight = FontWeight(720),
        overflow = TextOverflow.Ellipsis,
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Text(
        text = data.mail_id,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        fontWeight = FontWeight(420),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.padding(20.dp))
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Content() {
    ComposePracticeTheme {
//        EmployeeProfile()
    }
}


