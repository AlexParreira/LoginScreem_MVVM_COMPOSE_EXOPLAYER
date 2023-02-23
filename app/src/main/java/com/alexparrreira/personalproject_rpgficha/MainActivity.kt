package com.alexparrreira.personalproject_rpgficha


import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexparrreira.personalproject_rpgficha.login.Login
import com.alexparrreira.personalproject_rpgficha.login.LoginViewModel
import com.alexparrreira.personalproject_rpgficha.ui.theme.PersonalProjectRPGFichaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel = viewModel( modelClass = LoginViewModel::class.java )
            PersonalProjectRPGFichaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    Navigation(videoUri = getVideoUri(), loginViewModel = loginViewModel)
                }
            }
        }
    }
    fun getVideoUri(): Uri {
        //resources.getIdentifier("eye", "raw", this.packageName)
        val rawId = resources.getIdentifier("eye", "raw", this.packageName)
        val videoUri = "android.resource://$packageName/$rawId"
        return Uri.parse(videoUri)
    }

}

