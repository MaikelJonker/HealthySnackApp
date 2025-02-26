package nl.inholland.healthysnackapp.ui.profile

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.inholland.healthysnackapp.models.Child
import nl.inholland.healthysnackapp.models.User
import nl.inholland.healthysnackapp.models.UserPreferences

@Composable
fun ProfilePage(viewModel: ProfileViewModel, toHome: () -> Unit, toLogin: () -> Unit, toFaq: () -> Unit) {

    val user by viewModel.getUser().collectAsState(initial = null)

    if(user == null){
        NotLoggedIn(toLogin)
    }
    else {
        user?.let {
            Column(
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)
            ) {
                ProfileHeader(it, viewModel, toHome)
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        FamilySection(it.children)
                        Spacer(modifier = Modifier.height(16.dp))
                        PreferencesSection(it.preferences, toFaq)
                    }
                }
            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ProfileHeader(user: User, viewModel: ProfileViewModel, toHome :() -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
            .height(40.dp)
            .align(Alignment.End)
        ){
            Button(
                onClick = { viewModel.logout(toHome) },
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
fun PreferencesSection(preferences: UserPreferences, toFaq: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Voorkeuren", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // Language preference
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Taal:", fontSize = 14.sp)
            Text(preferences.language, fontSize = 14.sp)
        }

        // Halal preference
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Toon enkel halal producten", fontSize = 14.sp)
            Switch(
                checked = preferences.halal,
                onCheckedChange = {  }
            )
        }

        // Vegan preference
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Toon enkel vegan producten", fontSize = 14.sp)
            Switch(
                checked = preferences.vegan,
                onCheckedChange = {  }
            )
        }

        // Vegetarian preference
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Toon enkel vegetarische producten", fontSize = 14.sp)
            Switch(
                checked = preferences.vegetarian,
                onCheckedChange = {  }
            )
        }

        Spacer(Modifier.height(10.dp))
        Text("Veelgestelde vragen", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Text(
            text = "FAQ>",
            color = Color.Blue,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(5.dp)
                .clickable { toFaq() }
        )
    }
}

@Composable
fun NotLoggedIn(toLogin: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
        ){
            Text(
                text = "Profile",
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
        Spacer(Modifier.weight(1f))
        Text(
            text = "Je bent momenteel niet ingelogd"
        )
        Button(
            onClick = { toLogin() },
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Gray
            )
        ) {
            Text(
                text = "Inloggen"
            )
        }
        Spacer(Modifier.weight(1f))
    }
}