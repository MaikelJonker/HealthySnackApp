package nl.inholland.healthysnackapp.data.repositories

import android.content.Context
import nl.inholland.healthysnackapp.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.inholland.healthysnackapp.models.Snack
import nl.inholland.healthysnackapp.models.mappers.SnackMapper
import javax.inject.Inject
import javax.inject.Singleton

class SnackRepository @Inject constructor(
    private val context: Context,
    private val mapper: SnackMapper
) {

    fun getAllSnacks(): List<Snack> {
        // Access the JSON file from res/raw
        val inputStream = context.resources.openRawResource(R.raw.recipes)
        val jsonData = inputStream.bufferedReader().use { it.readText() }
        return mapper.map(jsonData)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSnackRepository(
        @ApplicationContext context: Context,
        mapper: SnackMapper
    ): SnackRepository {
        return SnackRepository(context, mapper)
    }
}