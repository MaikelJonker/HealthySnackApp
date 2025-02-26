package nl.inholland.healthysnackapp.data.services

import nl.inholland.healthysnackapp.data.modules.Api
import nl.inholland.healthysnackapp.data.repositories.ApiRepository
import nl.inholland.healthysnackapp.models.FaqEntry
import nl.inholland.healthysnackapp.models.User
import javax.inject.Inject

class FaqService @Inject constructor(
    @Api private val apiRepository: ApiRepository
) {

    suspend fun getFaq(): List<FaqEntry> {
        return apiRepository.getFaq()
    }
}
