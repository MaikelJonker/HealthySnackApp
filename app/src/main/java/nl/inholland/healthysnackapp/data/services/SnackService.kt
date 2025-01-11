package nl.inholland.healthysnackapp.data.services

import nl.inholland.healthysnackapp.data.repositories.SnackRepository
import nl.inholland.healthysnackapp.models.Snack
import javax.inject.Inject

class SnackService @Inject constructor(
    private val repository: SnackRepository
) {

    fun getAllSnacks(): List<Snack> {
        return repository.getAllSnacks()
    }
}