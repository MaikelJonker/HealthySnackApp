package nl.inholland.healthysnackapp.ui.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import nl.inholland.healthysnackapp.data.UserInfo.SessionManager
import nl.inholland.healthysnackapp.data.modules.Api
import nl.inholland.healthysnackapp.data.repositories.ApiRepository
import nl.inholland.healthysnackapp.models.User
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @Api private val apiRepository: ApiRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    fun getUser(): Flow<User?> = flow {
        if(sessionManager.getUserId() != null) {
            emit(apiRepository.getUsers().find { user -> user.id == sessionManager.getUserId() })
        }
    }.flowOn(Dispatchers.IO)

    fun logout(toHome: () -> Unit) {
        sessionManager.clearSession()
        toHome()
    }
}