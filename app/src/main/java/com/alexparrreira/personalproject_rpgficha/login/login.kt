package com.alexparrreira.personalproject_rpgficha.login

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.alexparrreira.personalproject_rpgficha.ui.theme.tormenta
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView

private fun Context.buildExoPlayer(uri: Uri): ExoPlayer = ExoPlayer.Builder(this).build().apply {
    setMediaItem(MediaItem.fromUri(uri))
    repeatMode = Player.REPEAT_MODE_ALL
    playWhenReady = true
    prepare()
}

private fun Context.buildPlayerView(exoPlayer: ExoPlayer): StyledPlayerView =
    StyledPlayerView(this).apply {
        player = exoPlayer
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        useController = false
        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
    }


@Composable
fun Login(
    videoUri: Uri,
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToSignUpPage: () -> Unit,

    ) {
    val context = LocalContext.current
    val exoPlayer: ExoPlayer = remember { context.buildExoPlayer(videoUri) }
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null

    if (isError) {
        Text(
            text = loginUiState?.loginError ?: "Unknown error", color = Color.Red
        )
    }
    DisposableEffect(
        AndroidView(
            factory = { it.buildPlayerView(exoPlayer) }, modifier = Modifier.fillMaxSize()
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }

    ProvideWindowInsets {


        Column(
            modifier = Modifier
                .navigationBarsWithImePadding()
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Mail", fontFamily = tormenta) },
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                isError = isError,
                value = loginUiState?.userName ?: "",
                onValueChange = { loginViewModel?.onUserNameChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                    )
                },

                )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password", fontFamily = tormenta) },
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                isError = isError,
                value = loginUiState?.password ?: "",
                onValueChange = { loginViewModel?.onPasswordNameChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                    )
                },
                visualTransformation = PasswordVisualTransformation()

            )

            Button(
                onClick = {
                    loginViewModel?.loginUser(context)
                }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(30.dp)
            ) {
                Text(text = "SING IN", Modifier.padding(8.dp), fontFamily = tormenta)
            }

            Divider(
                color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 30.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Don't have an account?", color = Color.White, fontFamily = tormenta)
                TextButton(onClick = { onNavToSignUpPage.invoke() }) {
                    Text(text = "SING UP", fontFamily = tormenta)
                }
            }
            if (loginUiState?.isLoding == true) {
                CircularProgressIndicator()
            }
            LaunchedEffect(key1 = loginViewModel?.hasUser) {
                if (loginViewModel?.hasUser == true) {
                    onNavToHomePage.invoke()
                }
            }
        }
    }
}


@Composable
fun SingUpSreen(
    videoUri: Uri,
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToLoginPage: () -> Unit,

    ) {
    val context = LocalContext.current
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpErro != null
    val exoPlayer: ExoPlayer = remember { context.buildExoPlayer(videoUri) }


    DisposableEffect(
        AndroidView(
            factory = { it.buildPlayerView(exoPlayer) }, modifier = Modifier.fillMaxSize()
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
    ProvideWindowInsets {


        Column(
            modifier = Modifier
                .navigationBarsWithImePadding()
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (isError) {
                Text(
                    text = loginUiState?.signUpErro ?: "Unknown error", color = Color.Red
                )
            }
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Mail", fontFamily = tormenta) },
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                isError = isError,
                value = loginUiState?.userNameSingUp ?: "",
                onValueChange = { loginViewModel?.onUserNameChangeSignup(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                    )
                },

                )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password", fontFamily = tormenta) },
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                isError = isError,
                value = loginUiState?.passwordSingUp ?: "",
                onValueChange = { loginViewModel?.onPasswordChangeSingup(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                    )
                },
                visualTransformation = PasswordVisualTransformation()

            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Confirm Password", fontFamily = tormenta) },
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                isError = isError,
                value = loginUiState?.confirmPasswordSingUp ?: "",
                onValueChange = { loginViewModel?.onConfirmPasswordChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                    )
                },
                visualTransformation = PasswordVisualTransformation()

            )

            Button(
                onClick = {
                    loginViewModel?.createUser(context)
                }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(30.dp)
            ) {
                Text(text = "SING IN", Modifier.padding(8.dp), fontFamily = tormenta)
            }

            Divider(
                color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 30.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Already have an account?", color = Color.White, fontFamily = tormenta)
                TextButton(onClick = { onNavToLoginPage.invoke() }) {
                    Text(text = "SING UP", fontFamily = tormenta)
                }
            }
            if (loginUiState?.isLoding == true) {
                CircularProgressIndicator()
            }
            LaunchedEffect(key1 = loginViewModel?.hasUser) {
                if (loginViewModel?.hasUser == true) {
                    onNavToHomePage.invoke()
                }
            }
        }
    }
}
