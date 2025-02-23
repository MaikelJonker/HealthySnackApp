package nl.inholland.healthysnackapp.ui.profile

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.inholland.healthysnackapp.models.Child
import nl.inholland.healthysnackapp.models.User
import nl.inholland.healthysnackapp.models.UserPreferences
import java.nio.file.WatchEvent

@Composable
fun ProfilePage(userId: Int, viewModel: ProfileViewModel) {

    val user by viewModel.getUser(userId).collectAsState(initial = null)

    user?.let {
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)) {
            ProfileHeader(it)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
            ) {
                item{
                    Spacer(modifier = Modifier.height(16.dp))
                    FamilySection(it.children)
                    Spacer(modifier = Modifier.height(16.dp))
                    PreferencesSection(it.preferences)
                }
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ProfileHeader(user: User) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
            .height(40.dp)
            .align(Alignment.End)
        ){
            Button(
                onClick = { },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                ),
                contentPadding = PaddingValues(2.dp),
                modifier = Modifier
                    .height(40.dp)
                    .width(100.dp)
                    .padding(5.dp)
            ) {
                Text(
                    text = "Uitloggen",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 12.sp
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.ic_menu_gallery),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(user.name, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
        Text(user.email, fontSize = 14.sp, color = Color.LightGray)
        Button(
            onClick = { /* Edit profile */ },
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Gray
            ),
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Text("Wijzig profiel")
        }
    }
}

@Composable
fun FamilySection(children: List<Child>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Mijn familie", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        children.forEach { child ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardColors(
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                )
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_menu_gallery),
                        contentDescription = "Child Image",
                        modifier = Modifier.size(40.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(child.name, fontWeight = FontWeight.Bold)
                        Text("Allergieën: ${child.allergies.joinToString()}", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
        Button(
            onClick = { /* Add family member */ },
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Familielid toevoegen")
        }
    }
}

@Composable
fun PreferencesSection(preferences: UserPreferences) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Voorkeuren", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Taal: ${preferences.language}", fontSize = 14.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = preferences.halal,
                onCheckedChange = {}
            )
            Text("Toon enkel halal producten")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = preferences.vegan,
                onCheckedChange = {}
            )
            Text("Toon enkel vegan producten")
        }
    }
}