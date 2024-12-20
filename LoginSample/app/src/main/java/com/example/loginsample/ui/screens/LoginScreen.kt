package com.example.loginsample.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.loginsample.ui.components.PasswordTextField
import com.example.loginsample.ui.components.UserTextField
import kotlin.math.min

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var validationMessage by remember { mutableStateOf("") }
    val isButtonEnabled by remember { derivedStateOf { user.isNotBlank() && password.isNotBlank() } }

    val isError = validationMessage.isNotBlank()
    val login = {
        validationMessage = validateLogin(user, password)
    }
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val bgTransition by infiniteTransition.animateColor(
        initialValue = Color.White,
        targetValue = Color.LightGray,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1500
            },
            repeatMode = RepeatMode.Reverse
        ), label = "bgTransition"
    )
    var counter by remember { mutableIntStateOf(0) }
    val transition = updateTransition(targetState = counter, label = "updateTransitionCounter")
    val animateDp by transition.animateDp(label = "animateDpCount") { count -> count.dp }
    val animateColor by transition.animateColor(label = "animateColorCount") {
        Color.Gray.copy(alpha = min(1f, it / 10f))
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(animateColor)
                .border(animateDp, Color.Gray)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            UserTextField(
                value = user,
                onValueChange = { user = it },
            )
            PasswordTextField(
                value = password,
                onValueChange = { password = it },
            )
            AnimatedVisibility(
                visible = validationMessage.isNotBlank(),
                enter = slideInHorizontally(initialOffsetX = { 2 * it })
            ) {
                Text(text = validationMessage, color = MaterialTheme.colorScheme.error)
            }
            AnimatedVisibility(visible = isButtonEnabled) {
                Button(
                    onClick = { counter++ }
                ) {
                    Text(text = "Login")
                }
            }
            //AnimatedContentComponent()
        }
    }
}

@Composable
fun AnimatedContentComponent() {
    var counter by remember { mutableIntStateOf(0) }
    Button(onClick = {
        counter++
    }) {
        Text("Increase counter")
    }
    AnimatedContent(
        targetState = counter,
        transitionSpec = {
            (slideIntoContainer(Up) + fadeIn())
                .togetherWith(slideOutOfContainer(Up) + fadeOut())
        },
        label = ""
    ) {
        Text("Click counter $it")
    }
}

fun validateLogin(user: String, password: String): String {
    val result = when {
        !user.contains("@") -> "User must be a valid email"
        password.length < 5 -> "Password must have at least 5 characters"
        else -> ""
    }
    return result
}
