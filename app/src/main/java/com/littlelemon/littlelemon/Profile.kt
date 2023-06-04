package com.littlelemon.littlelemon

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val  sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    val firstName = remember {
        mutableStateOf(sharedPreferences.getString("firstName", ""))
    }

    val lastName = remember {
        mutableStateOf(sharedPreferences.getString("lastName", ""))
    }

    val email = remember {
        mutableStateOf(sharedPreferences.getString("email", ""))
    }


    Column {
        // Profile information
        Header()
        Text("Profile information:",  textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(8.dp))

        // Display user data
        val userData = retrieveUserData(context)

        Column {
           // Text("First Name: ${userData.firstName}")
            OutlinedTextField(
                enabled = false,
                readOnly = true,
                value = firstName.value!!,
                onValueChange ={},
                label = { Text(text = "${userData.firstName}")},
                singleLine = true,
                placeholder = { Text(text = "John")},
                modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                enabled = false,
                readOnly = true,
                value = lastName.value!!,
                onValueChange ={},
                label = { Text(text = "${userData.lastName}")},
                singleLine = true,
                placeholder = { Text(text = "Doe")},
                modifier = Modifier.fillMaxWidth())

            OutlinedTextField(
                enabled = false,
                readOnly = true,
                value = email.value!!,
                onValueChange ={},
                label = { Text(text = "${userData.email}")},
                singleLine = true,
                placeholder = { Text(text = "johndoe@gmail.com")},
                modifier = Modifier.fillMaxWidth())
//            Text("Last Name: ${userData.lastName}")
//            Text("Email: ${userData.email}")
        }

        Button(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .background(color = Color(0xf4ce14)),
            onClick = {
                navController.navigate("Home") // Navigate back to the Home screen
            }
        ) {
            Text("Go back to Home")
        }

        Button(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .background(color = Color(0xf4ce14)),
            onClick = {
                navController.navigate("Onboarding") // Navigate from Profile to Onboarding
            }
        ) {
            Text("Logout")
        }
    }

}

fun retrieveUserData(context: Context): com.littlelemon.littlelemon.UserData {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString("firstName", "") ?: ""
    val lastName = sharedPreferences.getString("lastName", "") ?: ""
    val email = sharedPreferences.getString("email", "") ?: ""
    return UserData(firstName, lastName, email)
}

data class UserData(
    val firstName: String,
    val lastName: String,
    val email: String
)
