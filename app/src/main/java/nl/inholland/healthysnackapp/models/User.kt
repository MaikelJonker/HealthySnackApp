package nl.inholland.healthysnackapp.models

data class User (
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val preferences: UserPreferences,
    val children: List<Child>,
    val pictureUrl: String
)

data class UserPreferences(
    val language: String,
    var halal: Boolean,
    var vegan: Boolean,
    var vegetarian: Boolean
)

data class Child(
    val name: String,
    val allergies: List<String>
)