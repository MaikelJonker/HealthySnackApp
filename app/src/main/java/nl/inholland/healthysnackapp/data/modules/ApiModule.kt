package nl.inholland.healthysnackapp.data.modules

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.inholland.healthysnackapp.data.repositories.ProductRepository
import nl.inholland.healthysnackapp.data.repositories.RecipeRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProductApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RecipeApi

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val productUrl = "https://world.openfoodfacts.org/api/"
    private const val recipeUrl = "https://healthysnackapp-default-rtdb.europe-west1.firebasedatabase.app/"

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .add(KotlinJsonAdapterFactory())
        .build()

    @ProductApi
    @Provides
    fun provideProductRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(productUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @RecipeApi
    @Provides
    fun provideRecipeRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(recipeUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @ProductApi
    @Provides
    fun provideProductRepository(@ProductApi retrofit: Retrofit): ProductRepository =
        retrofit.create(ProductRepository::class.java)

    @RecipeApi
    @Provides
    fun provideRecipeRepository(@RecipeApi retrofit: Retrofit): RecipeRepository =
        retrofit.create(RecipeRepository::class.java)
}
