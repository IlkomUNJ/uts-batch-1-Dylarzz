package com.example.uts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.contactapp.AddEditContactScreen
import com.example.uts.model.Contact

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ContactApp() }
    }
}

class ContactData {
    companion object {
        var contacts = mutableStateListOf<Contact>()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list") {
        composable("list") { ListContactScreen(navController) }
        composable("add") { AddEditContactScreen(navController) }
        composable("edit/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull()
            AddEditContactScreen(navController, index)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListContactScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Dashboard") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Contact")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (ContactData.contacts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No contacts yet.")
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(ContactData.contacts.indices.toList()) { index ->
                        val contact = ContactData.contacts[index]

                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .combinedClickable(
                                    onClick = {},
                                    onLongClick = {
                                        navController.navigate("edit/$index")
                                    }
                                ),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = contact.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = contact.address,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "${contact.phone}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "${contact.email}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactAppPreview() {
    ContactData.contacts.clear()
    ContactData.contacts.addAll(
        listOf(
            Contact(
                name = "Adhitya Dava",
                address = "Jl. Merdeka No. 10 Kota Bandung Indonesia",
                phone = "0899999999",
                email = "adhitya,dava@gmail.com"
            ),
            Contact(
                name = "Jane Smith",
                address = "Jl. pulo gadung No. 21 Jakarta Selatan Indonesia",
                phone = "082112223333",
                email = "ryan.basmah@gmail.com"
            )
        )
    )

    MaterialTheme {
        ContactApp()
    }
}

