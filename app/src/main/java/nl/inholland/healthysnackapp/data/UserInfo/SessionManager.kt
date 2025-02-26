package nl.inholland.healthysnackapp.data.UserInfo

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SessionManager(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveUserCredentials(userId: Int, email: String, password: String) {
        sharedPreferences.edit().apply {
            putInt("user_id", userId)
            putString("email", email)
            putString("password", password)
            putBoolean("is_logged_in", true)
            apply()
        }
    }

    fun getUserId(): Int? {
        val userId = sharedPreferences.getInt("user_id", -1)
        return if (userId != -1) userId else null
    }

    fun getEmail(): String? = sharedPreferences.getString("email", null)

    fun getPassword(): String? = sharedPreferences.getString("password", null)

    fun isLoggedIn(): Boolean = sharedPreferences.getBoolean("is_logged_in", false)

    fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }
}
