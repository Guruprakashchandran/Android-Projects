package com.example.todoapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView

enum class ReorderHapticFeedbackType {
    START,
    MOVE,
    END,
}

open class ReorderHapticFeedback {
    open fun performHapticFeedback(type: ReorderHapticFeedbackType) {

    }
}


@Composable
fun rememberReorderHapticFeedback(): ReorderHapticFeedback {
    val view = LocalView.current

    val reorderHapticFeedback = remember {
        object : ReorderHapticFeedback() {
            @RequiresApi(Build.VERSION_CODES.R)
            override fun performHapticFeedback(type: ReorderHapticFeedbackType) {
                when (type) {
                    ReorderHapticFeedbackType.START ->
                        view.performHapticFeedback(android.view.HapticFeedbackConstants.LONG_PRESS)

                    ReorderHapticFeedbackType.MOVE ->
                        view.performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY)

                    ReorderHapticFeedbackType.END ->
                        view.performHapticFeedback(android.view.HapticFeedbackConstants.REJECT)
                }
            }
        }
    }

    return reorderHapticFeedback
}