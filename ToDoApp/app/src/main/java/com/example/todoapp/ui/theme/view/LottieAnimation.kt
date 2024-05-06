package com.example.todoapp.ui.theme.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.todoapp.R

@Composable
fun LottieAnimation(id: Int) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            resId = id
        )
    )
    if(id == R.raw.login_page_animation){
        LottieAnimation(
            composition = composition,
            iterations = 1,
            speed = 0.7f
        )
    }
    else{
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }

}