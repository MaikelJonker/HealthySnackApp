package nl.inholland.healthysnackapp.data.services

import nl.inholland.healthysnackapp.data.repositories.SnackRepository
import nl.inholland.healthysnackapp.models.Snack
import javax.inject.Inject

class SnackService @Inject constructor(
    private val repository: SnackRepository
) {

    fun getAllSnacks(): List<Snack> {
        val snacks: List<Snack> = repository.getAllSnacks()
        return if(snacks.isEmpty()) {
            emptyList()
        } else{
            snacks
        }
    }
}