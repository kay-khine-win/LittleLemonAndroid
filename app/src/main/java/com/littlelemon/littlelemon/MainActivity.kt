package com.littlelemon.littlelemon

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.littlelemon.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class MainActivity : ComponentActivity() {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "menu.db").build()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())
                Log.d("MainActivity", "Database Menu Items: $databaseMenuItems")
                println("Database Menu Items: $databaseMenuItems")

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    // val isLoggedIn = isUserLoggedIn()

                    //  MyNavigation(navController)
                    MyNavigation(navController, menuItems = databaseMenuItems)
                }
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                // add code here
                val menuItems = fetchMenu()
                saveMenuToDatabase(menuItems)
            }
        }

    }


    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val url =
            Url("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
        val response: HttpResponse = client.get {
            url {
                takeFrom(url)
            }
            method = HttpMethod.Get
        }
        val responseContent: String = response.bodyAsText()
        val menuNetwork = Json.decodeFromString<MenuNetwork>(responseContent)
        return menuNetwork.items
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        println("Save")
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }

}


@Composable
fun MyNavigation(navController: NavHostController, menuItems: List<MenuItemRoom>) {
    val context = LocalContext.current
    val navController = rememberNavController()
    NavigationComposable(navController, determineStartDestination(context, menuItems),menuItems)
}


@Composable
fun determineStartDestination(context: Context, menuItems: List<MenuItemRoom>): Boolean {
    val navController = rememberNavController()

    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString("firstName", null)
    val lastName = sharedPreferences.getString("lastName", null)
    val email = sharedPreferences.getString("email", null)

    return if (firstName.isNullOrBlank() || lastName.isNullOrBlank() || email.isNullOrBlank()) {
        //  "Home" // User data is not stored, start destination is Home
        false
    } else {
        // "Onboarding" // User data is stored, start destination is Onboarding
       // Home(navController = navController, menuItems = menuItems)
        true
    }
}
