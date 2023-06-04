package com.littlelemon.littlelemon

import android.content.Context
import android.preference.PreferenceActivity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


//fun Onboarding(context: Context, navHostController: NavHostController) {
@Composable
fun Onboarding(navController: NavHostController) {

    Column(modifier = Modifier.fillMaxSize()) {
        Header()
        PersonalDetailsSection(navController)
    }
}
@Composable
fun Header() {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = stringResource(R.string.logo),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentScale = ContentScale.FillWidth
    )
}
@Composable
//fun PersonalDetailsSection(navController: NavHostController, context: Context) {
fun PersonalDetailsSection(navController: NavHostController) {
    val context = LocalContext.current

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .background(Color(0xFF485E57))
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = "Let's get to know you",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
                    .padding(16.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                    // Display registration unsuccessful message
                    Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_LONG).show()
                } else {
                    // Save user data to SharedPreferences
                    saveUserData(context, firstName, lastName, email)
                    // Display registration successful message
                    Toast.makeText(context, "Registration successful.", Toast.LENGTH_LONG).show()
                    // Navigate to the Home screen
                    navController.navigate("Home") // Navigate to the Home screen after successful registration
                }
            },
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .background(color = Color(0xf4ce14))
        ) {
            Text(text = "Register")
        }
    }
}

fun saveUserData(context: Context, firstName: String, lastName: String, email: String) {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("firstName", firstName)
    editor.putString("lastName", lastName)
    editor.putString("email", email)
    editor.apply()
}
