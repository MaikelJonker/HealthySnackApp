package nl.inholland.healthysnackapp.ui.login

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

@Composable
fun LoginPage(
    //viewModel: LoginViewModel
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // App Logo
        Image(
            painter = painterResource(id = R.drawable.ic_menu_gallery), // Replace with actual image
            contentDescription = "App Logo",
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(30.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Welkom terug, Ahmet!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Leer nieuwe recepten kennen, bekijk wat je familieleden en vrienden delen en behoud een gezonde levensstijl.",
            fontSize = 14.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Username field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Gebruikersnaam") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Wachtwoord") },
            leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) },
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(image, contentDescription = null)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = { /* Handle login */ },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Log in", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Heb je nog geen account?", fontSize = 14.sp, color = Color.DarkGray)

        Spacer(modifier = Modifier.height(8.dp))

        // Create New Account Button
        Button(
            onClick = { /* Handle new account creation */ },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Creëer nieuw account", color = Color.Black, fontSize = 16.sp)
        }
    }
}
