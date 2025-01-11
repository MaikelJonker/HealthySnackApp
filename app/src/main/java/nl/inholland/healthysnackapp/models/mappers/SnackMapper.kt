package nl.inholland.healthysnackapp.models.mappers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import nl.inholland.healthysnackapp.models.Snack
import com.fasterxml.jackson.module.kotlin.readValue
import javax.inject.Inject

class SnackMapper @Inject constructor() {
    fun map(jsonData: String): List<Snack> {
        val objectMapper = jacksonObjectMapper()
        return objectMapper.readValue(jsonData)
    }
}