package com.example.composepractice

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composepractice.ui.theme.ComposePracticeTheme

@Preview(showBackground = true)
@Composable
fun ProfileView() {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary)
                .verticalScroll(rememberScrollState()),
        ) {
            ShowEmployeeProfileDetails()
        }
    }
}

@Composable
fun ShowEmployeeProfileDetails() {

    Box(
        modifier = Modifier
            .height(330.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentScale = ContentScale.FillBounds
        )
        Box(modifier = Modifier
            .padding(30.dp, 30.dp, 0.dp, 0.dp)
            .size(40.dp)
            .background(MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(10.dp))){

            IconButton(
                onClick = { },
                modifier = Modifier
                    .padding(10.dp),
                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = MaterialTheme.colorScheme.secondary)
            }
        }


//        Image(
//            painter = painterResource(id = R.drawable.cancel),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(),
////            alignment = Alignment.TopStart
//        )
    }
    Spacer(modifier = Modifier.padding(20.dp))
    Text(
        text = "Guruprakash C",
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
        text = "Guruprakash0107@gmail.com",
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
        text = "Jambav",
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
        text = "Trainee",
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
        text = "TS-257",
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
        text = "guruprakash0107@gmail.com",
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
        text = "9384348641",
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
        text = "01-07-2002",
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
        text = "25000",
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Content() {
    ComposePracticeTheme {
        ProfileView()
    }
}


