package com.example.todoapp.ui.theme.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.navigation.Screen
import com.example.todoapp.viewmodel.TodoCategoryViewModel
import com.zoho.catalyst.setup.ZCatalystApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginOrSignUpPage(navController: NavHostController) {

    val context = LocalContext.current
    val toDoCategory: TodoCategoryViewModel = hiltViewModel()
    if (ZCatalystApp.getInstance().isUserSignedIn()) {
        navController.navigate(Screen.ToDoListCategory.route)
    } else {
        LoginPage(navController, context, toDoCategory)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController, context: Context, toDoCategory: TodoCategoryViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(
                        text = "Todo App",
                        fontSize = 22.sp,
                        color = Color.Black,
                        fontWeight = FontWeight(800)
                    )
                }
                )
            }
        ) {

            LoginContentPage(it, toDoCategory, navController, context)
        }

    }
}

@Composable
fun LoginContentPage(
    it: PaddingValues,
    toDoCategory: TodoCategoryViewModel,
    navController: NavController,
    context: Context
) {
    var isProcess by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(it),
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
//            Surface(modifier = Modifier.size(300.dp)) {
            LottieAnimation(R.raw.login_page_animation)
//            }
        }

        if(!isProcess) {
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        isProcess = true
                        if (!ZCatalystApp.getInstance().isUserSignedIn()) {
                            val isSuccess = toDoCategory.login()
                            if (isSuccess) {
                                isProcess = false
                                navController.navigate(Screen.ToDoListCategory.route)
                            }
                        }
                    }

                },
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(0xff257EBE)
                )
            ) {

                Text(
                    text = "Sign In",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(7.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.Black
                ),
                shape = RoundedCornerShape(5.dp),
            ) {

                Text(
                    text = "Sign Up",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(7.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
//        LinearProgressIndicator()

        }
    }
}

fun loginSetup(navController: NavController, context: Context) {

    ZCatalystApp.getInstance().login(
        success = {
            navController.navigate(Screen.ToDoListCategory.route)

        },
        failure = {
            Toast.makeText(context, "Logout Failed!!!", Toast.LENGTH_SHORT).show()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginOrSignUpPage() {

    val navController = rememberNavController()
    LoginOrSignUpPage(
        navController = navController,
    )
}