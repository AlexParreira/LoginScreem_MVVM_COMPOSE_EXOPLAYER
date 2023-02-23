package com.alexparrreira.personalproject_rpgficha.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexparrreira.personalproject_rpgficha.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
):ViewModel() {
    val currentUser = repository.currentUser

    val hasUser:Boolean
        get() = repository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun onUserNameChange(userName: String){
        loginUiState = loginUiState.copy(userName = userName)
    }
    fun onPasswordNameChange(password: String){
        loginUiState = loginUiState.copy(password = password)
    }
    fun onUserNameChangeSignup(userName: String){
        loginUiState = loginUiState.copy(userNameSingUp =  userName)
    }
    fun onPasswordChangeSingup(password: String){
        loginUiState = loginUiState.copy(passwordSingUp = password)
    }
    fun onConfirmPasswordChange(password: String){
        loginUiState = loginUiState.copy(confirmPasswordSingUp = password)
    }

    private fun validateLoginForm()=
        loginUiState.userName.isNotBlank() &&
                loginUiState.password.isNotBlank()

    private fun validateSingupForm() =
        loginUiState.userNameSingUp.isNotBlank() &&
                loginUiState.passwordSingUp.isNotBlank() &&
                    loginUiState.confirmPasswordSingUp.isNotBlank()


    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if(!validateSingupForm()){
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUiState = loginUiState.copy(isLoding = true)
            if(loginUiState.passwordSingUp != loginUiState.confirmPasswordSingUp){
                throw  IllegalArgumentException(
                    "Password do not match"
                )
            }
            loginUiState = loginUiState.copy(signUpErro = null)
            repository.createUser(
            loginUiState.userNameSingUp,
            loginUiState.passwordSingUp
            ){isSuccessful ->
                if(isSuccessful){
                    Toast.makeText(
                        context,
                        "Success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = true)
                }else{
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = false)
                }
            }
        }catch (e:Exception){
            loginUiState = loginUiState.copy(signUpErro = e.localizedMessage)
            e.printStackTrace()
        }finally{
            loginUiState = loginUiState.copy(isLoding = false)
        }
    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if(!validateLoginForm()){
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUiState = loginUiState.copy(isLoding = true)
            loginUiState = loginUiState.copy(loginError = null)
            repository.login(
                loginUiState.userName,
                loginUiState.password
            ){isSuccessful ->
                if(isSuccessful){
                    Toast.makeText(
                        context,
                        "Success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = true)
                }else{
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = false)
                }
            }
        }catch (e:Exception){
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        }finally{
            loginUiState = loginUiState.copy(isLoding = false)
        }
    }

}

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val userNameSingUp: String = "",
    val passwordSingUp: String = "",
    val confirmPasswordSingUp: String = "",
    val isLoding:Boolean = false,
    val isSuccessLogin: Boolean = false,
    val signUpErro:String? = null,
    val loginError:String? = null
)