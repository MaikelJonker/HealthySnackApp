package nl.inholland.healthysnackapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.inholland.healthysnackapp.data.services.SnackService
import nl.inholland.healthysnackapp.models.Snack
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val service: SnackService
) : ViewModel() {

    private val _snacks = MutableStateFlow<List<Snack>>(emptyList())
    val snacks: StateFlow<List<Snack>> = _snacks

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            _snacks.value = service.getAllSnacks()
        }
    }
}
