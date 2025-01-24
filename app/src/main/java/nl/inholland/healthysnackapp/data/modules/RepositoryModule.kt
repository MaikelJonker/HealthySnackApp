package nl.inholland.healthysnackapp.data.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.inholland.healthysnackapp.data.repositories.FavoriteProductsRepository
import nl.inholland.healthysnackapp.data.repositories.FavoriteRecipesRepository
import nl.inholland.healthysnackapp.data.repositories.ShoppingListRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideShoppingListRepository(@ApplicationContext context: Context): ShoppingListRepository {
        return ShoppingListRepository(context)
    }

    @Singleton
    @Provides
    fun provideRecipeRepository(@ApplicationContext context: Context): FavoriteRecipesRepository {
        return FavoriteRecipesRepository(context)
    }

    @Singleton
    @Provides
    fun provideProductRepository(@ApplicationContext context: Context): FavoriteProductsRepository {
        return FavoriteProductsRepository(context)
    }
}