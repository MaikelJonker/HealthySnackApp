package nl.inholland.healthysnackapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.inholland.healthysnackapp.data.services.UserService

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserService
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.login(email, password)

            _loginState.value = if (user != null) {
                LoginState.Success(user.id.toString())  // Return User ID
            } else {
                LoginState.Error("Gebruikersnaam en/of wachtwoord is incorrect.")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    data class Success(val userId: String) : LoginState() // Now includes User ID
    data class Error(val message: String) : LoginState()
}

