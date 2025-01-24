package nl.inholland.healthysnackapp.data.repositories

import android.content.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import nl.inholland.healthysnackapp.models.ShoppingListItem

class ShoppingListRepository(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("shopping_list", Context.MODE_PRIVATE)

    private val objectMapper = jacksonObjectMapper()
    private val shoppingListKey = "shopping_list"

    // Retrieve the saved shopping list
    fun getShoppingList(): List<ShoppingListItem> {
        val json = sharedPreferences.getString(shoppingListKey, null)
        return if (json != null) {
            objectMapper.readValue(json)
        } else {
            emptyList()
        }
    }

    // Add a product to the shopping list
    fun addProduct(barcode: String) {
        val currentList = getShoppingList().toMutableList()
        val existingItem = currentList.find { it.barcode == barcode }
        if (existingItem != null) {
            existingItem.quantity += 1 // Increment quantity if the item already exists
        } else {
            currentList.add(ShoppingListItem(barcode, 1))
        }
        saveShoppingList(currentList)
    }

    // Remove a product or decrement its quantity
    fun removeProduct(barcode: String) {
        val currentList = getShoppingList().toMutableList()
        val existingItem = currentList.find { it.barcode == barcode }
        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                existingItem.quantity -= 1 //
            } else {
                currentList.remove(existingItem)
            }
        }
        saveShoppingList(currentList)
    }

    // Save the shopping list
    private fun saveShoppingList(shoppingList: List<ShoppingListItem>) {
        val json = objectMapper.writeValueAsString(shoppingList)
        sharedPreferences.edit()
            .putString(shoppingListKey, json)
            .apply()
    }
}