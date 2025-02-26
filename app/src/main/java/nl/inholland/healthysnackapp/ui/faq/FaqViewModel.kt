package nl.inholland.healthysnackapp.ui.faq

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import nl.inholland.healthysnackapp.data.services.FaqService
import nl.inholland.healthysnackapp.models.FaqEntry
import nl.inholland.healthysnackapp.models.User
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor(
    val faqService: FaqService
) : ViewModel() {

    fun getFaq(): Flow<List<FaqEntry>> = flow {
            emit(faqService.getFaq())
    }.flowOn(Dispatchers.IO)
}