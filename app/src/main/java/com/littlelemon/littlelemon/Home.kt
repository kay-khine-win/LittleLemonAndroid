package com.littlelemon.littlelemon

import android.view.MenuItem
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room

@Composable
fun Home(navController: NavHostController, menuItems: List<MenuItemRoom>) {
    val context = LocalContext.current

    val database by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "menu.db").build()
    }

    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())
    val searchPhrase = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize()) {

        ProfileHeader(navController)
       // HeroSection()
        Hero()
        MenuItems(menuItems = menuItems)
        LowerPanel(databaseMenuItems, searchPhrase)


    }
}
@Composable
fun ProfileHeader(navController: NavHostController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            // .padding(horizontal = 16.dp)
            .padding(top = 0.dp, bottom = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(80.dp)
        )
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "Profile",
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    navController.navigate(Profile.route)
                }
        )

    }
}
@Composable
fun Hero() {
    val heroImage = painterResource(R.drawable.hero_image)
    val searchPhrase = remember { mutableStateOf(TextFieldValue()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
           // .background(Color.Gray)
            .background(Color(0xFF485E57))
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.weight(1f)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Little Lemon",
                        style = TextStyle(fontSize = 24.sp, color = Color.Yellow),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Chicago",
                        style = TextStyle(fontSize = 16.sp, color = Color.White),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                        style = TextStyle(fontSize = 12.sp, color = Color.White)
                    )
                }

                Image(
                    painter = heroImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            TextField(
                value = searchPhrase.value.text,
                onValueChange = { searchPhrase.value = TextFieldValue(it) },
                placeholder = { Text("Enter Search Phrase") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()

            )
        }
    }
}

@Composable
fun HeroSection() {
    val heroImage = painterResource(R.drawable.hero_image)
    val searchPhrase = remember { mutableStateOf(TextFieldValue()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Gray)
    ) {
        Column(Modifier.fillMaxSize()) {
            Text(
                text = "Little Lemon",
                style = TextStyle(fontSize = 16.sp, color = Color.White),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Chicago",
                style = TextStyle(fontSize = 16.sp, color = Color.White),fontWeight = FontWeight.Bold
            )

            Row(Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                        style = TextStyle(fontSize = 16.sp, color = Color.White)
                    )
                }
                Image(
                    painter = heroImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                )


            }//Row


            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                label = { Text(text = "Search") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Gray,
                    textColor = Color.White
                )
            )

        }//Column End
    }
}
//
//@Preview
//@Composable
//fun HeroSectionPreview() {
//    HeroSection()
//}

@Composable
fun MenuItems(menuItems: List<MenuItemRoom>) {
    Column(modifier = Modifier.padding(16.dp)) {
        for (item in menuItems) {
            MenuItem(item)
        }
    }
}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemRoom) {

    val itemDescription = if (item.description.length > 100) {
        item.description.substring(0, 100) + ". . ."
    } else {
        item.description
    }

//    Card(elevation = 4.dp,
//        modifier = Modifier
//            .clickable {
//
//            }) {
    Row(Modifier.fillMaxWidth(),) {
        Column(Modifier.fillMaxWidth(0.7f),
            verticalArrangement = Arrangement.SpaceBetween) {
            Text(text = item.title, fontWeight = FontWeight.Bold)
            Text(text = itemDescription, modifier = Modifier.padding(bottom = 10.dp))
            Text(text = "$ ${item.price}", fontWeight = FontWeight.Bold)        }
        GlideImage(
            model = item.imageUrl,
            contentDescription = "",
            Modifier.size(100.dp, 70.dp),
            contentScale = ContentScale.Crop
        )
    }

    // }

}



@Composable
fun LowerPanel(databaseMenuItems: List<MenuItemRoom>, search: MutableState<String>) {
    val categories = databaseMenuItems.map {
        it.category.replaceFirstChar {character ->
            character.uppercase()
        }
    }.toSet()


    val selectedCategory = remember {
        mutableStateOf("")
    }


    val items = if(search.value == ""){
        databaseMenuItems

    }
    else{
        databaseMenuItems.filter {
            it.title.contains(search.value, ignoreCase = true)

        }


    }



    val filteredItems = if(selectedCategory.value == "" || selectedCategory.value == "all"){
        items
    }
    else{
        items.filter {
            it.category.contains(selectedCategory.value, ignoreCase = true)
        }
    }



}

