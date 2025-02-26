package nl.inholland.healthysnackapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.inholland.healthysnackapp.data.UserInfo.SessionManager
import nl.inholland.healthysnackapp.data.services.UserService
import javax.inject.Inject

sealed class LoginState {
    data class Success(val userId: Int) : LoginState()
    data class Error(val message: String) : LoginState()
    object Idle : LoginState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userService: UserService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    init {
        checkIfUserAlreadyLoggedIn()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = userService.login(email, password)
            if (user != null) {
                sessionManager.saveUserCredentials(user.id, email, password) // Save securely
                _loginState.value = LoginState.Success(user.id)
            } else {
                _loginState.value = LoginState.Error("Incorrect email or password.")
            }
        }
    }

    private fun checkIfUserAlreadyLoggedIn() {
        if (sessionManager.isLoggedIn()) {
            val userId = sessionManager.getUserId()
            if (userId != null) {
                _loginState.value = LoginState.Success(userId) // Auto-login
            }
        }
    }

    fun logout() {
        sessionManager.clearSession()
        _loginState.value = LoginState.Idle
    }
}


