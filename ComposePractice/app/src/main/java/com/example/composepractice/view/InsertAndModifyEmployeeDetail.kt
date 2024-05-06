package com.example.composepractice.view

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Configuration
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composepractice.EmployeeEntity
import com.example.composepractice.events.EmployeeListEvents
import com.example.composepractice.states.EmployeeProfileState
import com.example.composepractice.ui.theme.ComposePracticeTheme
import com.example.composepractice.util.StringValidationUtil
import com.example.composepractice.viewmodel.ManipulateEmployeeDetailViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertAndModifyEmployeeDetail(id: Long, navController: NavHostController) {

    val manipulateEmployeeDetailViewModel: ManipulateEmployeeDetailViewModel = hiltViewModel()
    val state by manipulateEmployeeDetailViewModel.state.collectAsState()

    if (id != 0L) {
        GetEmployeeDetails(navController, id, manipulateEmployeeDetailViewModel, state)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Scaffold(topBar = {
            TopAppBar(title = {
                Text(text = "Employee", color = MaterialTheme.colorScheme.secondary)
            },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack, contentDescription = "",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                })
        }) { values ->
            LazyColumn(
                modifier = Modifier
                    .padding(values)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                item {
                    EntryDetails(
                        navController,
                        manipulateEmployeeDetailViewModel = manipulateEmployeeDetailViewModel,
                        state = state
                    )
                }
            }
        }
    }
}

@Composable
fun GetEmployeeDetails(
    navController: NavHostController,
    id: Long,
    manipulateEmployeeDetailViewModel: ManipulateEmployeeDetailViewModel,
    state: EmployeeProfileState
) {

    if (id != 0L) {
        LaunchedEffect(key1 = EmployeeListEvents.LoadProfileData(id)) {
            manipulateEmployeeDetailViewModel.handleEvent(EmployeeListEvents.LoadProfileData(id))
        }
    }

    when (state) {

        EmployeeProfileState.Loading -> {
            LoadingProgressBar()
        }

        is EmployeeProfileState.Error -> {

            ShowError(msg = (state as EmployeeProfileState.Error).message)
        }

        is EmployeeProfileState.Success -> {

            EntryDetails(
                navController,
                manipulateEmployeeDetailViewModel,
                state
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDetails(
    navController: NavHostController,
    manipulateEmployeeDetailViewModel: ManipulateEmployeeDetailViewModel,
    state: EmployeeProfileState
) {

    val context = LocalContext.current

    var addDetails by remember {
        mutableStateOf(false)
    }
    var expandGender: Boolean by remember {
        mutableStateOf(false)
    }
    val genders = arrayOf("Male", "Female")

    val departments =
        arrayOf("Jambav", "CRM", "Creater App", "Zoho One", "People", "Trainer Central")

    var expandDepartment: Boolean by remember {
        mutableStateOf(false)
    }
    val designations =
        arrayOf("Trainee", "Employee", "Team Leader", "Manager", "Admin", "Super Admin")

    var expandDesignation: Boolean by remember {
        mutableStateOf(false)
    }
    val calender = Calendar.getInstance()
    val year = calender.get(Calendar.YEAR)
    val month = calender.get(Calendar.MONTH)
    val day = calender.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            manipulateEmployeeDetailViewModel.dateOfBirth.value = "$dayOfMonth-$month-$year"
        }, year, month, day
    )

    val id by manipulateEmployeeDetailViewModel.id.collectAsState()
    val name by manipulateEmployeeDetailViewModel.name.collectAsState()
    val employeeId by manipulateEmployeeDetailViewModel.employeeId.collectAsState()
    val emailId by manipulateEmployeeDetailViewModel.emailId.collectAsState()
    val mobileNo by manipulateEmployeeDetailViewModel.mobileNo.collectAsState()
    val salary by manipulateEmployeeDetailViewModel.salary.collectAsState()
    val experience by manipulateEmployeeDetailViewModel.experience.collectAsState()
    val address by manipulateEmployeeDetailViewModel.address.collectAsState()
    val dateOfBirth by manipulateEmployeeDetailViewModel.dateOfBirth.collectAsState()
    val gender by manipulateEmployeeDetailViewModel.selectedGender.collectAsState()
    val department by manipulateEmployeeDetailViewModel.selectedDepartment.collectAsState()
    val designation by manipulateEmployeeDetailViewModel.selectedDesignation.collectAsState()

    OutlinedTextField(
        value = name,
        onValueChange = {
            manipulateEmployeeDetailViewModel.name.value = it
        },
        label = { Text(text = "Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 30.dp, 20.dp, 15.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.run {
            colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.secondary
            )
        }
    )
    OutlinedTextField(
        value = employeeId,
        onValueChange = {
            manipulateEmployeeDetailViewModel.employeeId.value = it
        },
        label = { Text(text = "Employee id") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 15.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.run {
            colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.secondary
            )
        }
    )
    OutlinedTextField(
        value = address,
        onValueChange = { manipulateEmployeeDetailViewModel.address.value = it },
        label = { Text(text = "Address") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 15.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.run {
            colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.secondary
            )
        }
    )
    OutlinedTextField(
        value = mobileNo,
        onValueChange = { manipulateEmployeeDetailViewModel.mobileNo.value = it },
        label = { Text(text = "Mobile No") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 15.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.run {
            colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.secondary
            )
        }
    )
    OutlinedTextField(
        value = emailId,
        onValueChange = { manipulateEmployeeDetailViewModel.emailId.value = it },
        label = { Text(text = "Email Id") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 15.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.run {
            colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.secondary
            )
        }
    )

    OutlinedTextField(
        value = salary,
        onValueChange = { manipulateEmployeeDetailViewModel.salary.value = it },
        label = { Text(text = "Salary") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 15.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.run {
            colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.secondary
            )
        }
    )

    OutlinedTextField(
        value = experience,
        onValueChange = { manipulateEmployeeDetailViewModel.experience.value = it },
        label = { Text(text = "Year Of Experience") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 15.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.run {
            colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.secondary
            )
        }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 15.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expandGender,
            onExpandedChange = {
                expandGender = !expandGender
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary),
        ) {
            OutlinedTextField(
                value = gender,
                onValueChange = { },
                readOnly = true,

                label = { Text(text = "Gender") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
                    disabledTextColor = MaterialTheme.colorScheme.secondary,
                    disabledLabelColor = MaterialTheme.colorScheme.secondary,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.secondary,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondary

                ),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expandGender
                    )
                },
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
            )
            ExposedDropdownMenu(
                expanded = expandGender, onDismissRequest = {
                    expandGender = false
                }, modifier = Modifier.fillMaxWidth()

            ) {

                genders.forEach { item ->
                    DropdownMenuItem(text = {
                        Text(
                            text = item,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }, onClick = {
                        manipulateEmployeeDetailViewModel.selectedGender.value = item
                        expandGender = false
                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }, modifier = Modifier.background(MaterialTheme.colorScheme.secondary))
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 15.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expandDepartment, onExpandedChange = {
                expandDepartment = !expandDepartment
            }, modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary)
        ) {
            OutlinedTextField(
                value = department,
                onValueChange = { },
                readOnly = true,
                enabled = false,
                label = { Text(text = "Department", color = MaterialTheme.colorScheme.secondary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
                    disabledTextColor = MaterialTheme.colorScheme.secondary,
                    disabledLabelColor = MaterialTheme.colorScheme.secondary,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.secondary,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondary
                ),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expandDepartment
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
            )
            ExposedDropdownMenu(
                expanded = expandDepartment, onDismissRequest = {
                    expandDepartment = false
                }, modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
            ) {

                departments.forEach { item ->
                    DropdownMenuItem(text = {
                        Text(
                            text = item,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }, onClick = {
                        manipulateEmployeeDetailViewModel.selectedDepartment.value = item
                        expandDepartment = false
                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondary)
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 15.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expandDesignation, onExpandedChange = {
                expandDesignation = !expandDesignation
            }, modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary)
        ) {
            OutlinedTextField(
                value = designation,
                onValueChange = { },
                readOnly = true,
                enabled = false,
                label = { Text(text = "Designation") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
                    disabledTextColor = MaterialTheme.colorScheme.secondary,
                    disabledLabelColor = MaterialTheme.colorScheme.secondary,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.secondary,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondary
                ),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expandDesignation
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
            )
            ExposedDropdownMenu(expanded = expandDesignation, onDismissRequest = {
                expandDesignation = false
            }, modifier = Modifier.fillMaxWidth()) {

                designations.forEach { item ->
                    DropdownMenuItem(text = {
                        Text(
                            text = item,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }, onClick = {
                        manipulateEmployeeDetailViewModel.selectedDesignation.value = item
                        expandDesignation = false
                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }, modifier = Modifier.background(MaterialTheme.colorScheme.secondary))
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 0.dp, 20.dp, 15.dp)
            .border(1.dp, MaterialTheme.colorScheme.secondary, RectangleShape)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 15.dp, 10.dp, 0.dp)
        ) {

            Text(
                text = "Date Of Birth", modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = dateOfBirth, modifier = Modifier
                        .width(140.dp)
                        .height(30.dp)
                        .align(Alignment.CenterVertically),
                    color = MaterialTheme.colorScheme.secondary
                )
                Button(
                    onClick = { datePickerDialog.show() },
                    modifier = Modifier
                        .padding(40.dp, 8.dp, 0.dp, 10.dp)
                        .height(40.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                ) {

                    Text(text = "Select Date", color = MaterialTheme.colorScheme.primary)
                }
            }

        }
    }

    Button(
        onClick = {

            if (validateEmployeeDetail(
                    context,
                    name,
                    emailId,
                    employeeId,
                    mobileNo,
                    address,
                    salary,
                    experience,
                    gender,
                    department,
                    designation,
                    dateOfBirth
                )
            ) {
                addDetails = true
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(20.dp, 10.dp, 20.dp, 20.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(5.dp),

        ) {
        Text(text = "ADD", color = MaterialTheme.colorScheme.primary, fontSize = 15.sp)
    }
    if (addDetails) {
        val employee = convertEmployeeEntity(
            id,
            name,
            emailId,
            employeeId,
            address,
            dateOfBirth,
            department,
            designation,
            mobileNo,
            gender,
            salary,
            experience
        )
        LaunchedEffect(key1 = EmployeeListEvents.ManipulateData(employee)) {

            println("println${employee}")
            manipulateEmployeeDetailViewModel.handleEvent(EmployeeListEvents.ManipulateData(employee))
        }
        when (state) {
            EmployeeProfileState.Loading -> {
                CircularProgressIndicator()
            }

            is EmployeeProfileState.Success -> {
                Toast.makeText(context, "Employee Detail Added Successfully!!!", Toast.LENGTH_SHORT)
                    .show()
                navController.navigateUp()

            }

            is EmployeeProfileState.Error -> {
                Toast.makeText(context, (state).message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun convertEmployeeEntity(
    id: String,
    name: String,
    emailId: String,
    employeeId: String,
    address: String,
    dateOfBirth: String,
    department: String,
    designation: String,
    mobileNo: String,
    gender: String,
    salary: String,
    experience: String
): EmployeeEntity {

    var uid = 0L
    if(id.isNotEmpty()){
         uid = id.toLong()
    }
    return EmployeeEntity(
        uid,
        name,
        address,
        emailId,
        mobileNo.toLong(),
        experience.toLong(),
        salary.toLong(),
        department,
        designation,
        employeeId,
        dateOfBirth,
        gender
    )
}

fun validateEmployeeDetail(
    context: Context,
    name: String,
    emailId: String,
    employeeId: String,
    mobileNo: String,
    address: String,
    salary: String,
    experience: String,
    gender: String,
    department: String,
    designation: String,
    dateOfBirth: String
): Boolean {
    if (!StringValidationUtil.checkEmployeeName { name }) {

        Toast.makeText(context, "Wrong name!!!", Toast.LENGTH_SHORT).show()
        return false
    }
    if (!StringValidationUtil.employeeid { employeeId }) {
        Toast.makeText(context, "Wrong Employee Id!!!", Toast.LENGTH_SHORT).show()
        return false
    }
    if (!StringValidationUtil.checkEmployeeMailId { emailId }) {
        Toast.makeText(context, "Wrong Email id!!!", Toast.LENGTH_SHORT).show()
        return false

    }
    if (!StringValidationUtil.checkEmployeeMobileNo { mobileNo.toLong() }) {
        Toast.makeText(context, "Wrong Mobile no!!!", Toast.LENGTH_SHORT).show()
        return false
    }
    if (!StringValidationUtil.checkEmployeeAddress { address }) {
        Toast.makeText(context, "Wrong Address!!!", Toast.LENGTH_SHORT).show()
        return false
    }
    if (!StringValidationUtil.checkEmployeeSalary { salary.toLong() }) {
        Toast.makeText(context, "Wrong Salary!!!", Toast.LENGTH_SHORT).show()
        return false
    }
    if (!StringValidationUtil.checkEmployeeExperience { experience.toLong() }) {
        Toast.makeText(context, "Wrong Experience!!!", Toast.LENGTH_SHORT).show()
        return false
    }
    if (gender.isEmpty()) {
        Toast.makeText(context, "Wrong Gender!!!", Toast.LENGTH_SHORT).show()
        return false

    }
    if (department.isEmpty()) {
        Toast.makeText(context, "Wrong Department!!!", Toast.LENGTH_SHORT).show()
        return false

    }
    if (designation.isEmpty()) {
        Toast.makeText(context, "Wrong Designation!!!", Toast.LENGTH_SHORT).show()
        return false

    }
    if (dateOfBirth.isEmpty()) {
        Toast.makeText(context, "Wrong Date Of Birth!!!", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingDarkPreview() {
    ComposePracticeTheme {
//        Greeting()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposePracticeTheme {
//        Greeting()
    }
}