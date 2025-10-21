package com.example.contactapp

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uts.ContactData
import com.example.uts.model.Contact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditContactScreen(navController: NavHostController, index: Int? = null) {
    val isEdit = index != null
    val existingContact = if (isEdit) ContactData.contacts[index!!] else null
    val context = LocalContext.current

    var name by remember { mutableStateOf(TextFieldValue(existingContact?.name ?: "")) }
    var address by remember { mutableStateOf(TextFieldValue(existingContact?.address ?: "")) }
    var phone by remember { mutableStateOf(TextFieldValue(existingContact?.phone ?: "")) }
    var email by remember { mutableStateOf(TextFieldValue(existingContact?.email ?: "")) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (isEdit) "Edit Contact" else "Add Contact") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address (min 5 words)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                val wordCount = address.text.trim().split("\\s+".toRegex()).size

                if (wordCount < 5) {
                    Toast.makeText(context, "Alamat harus minimal 5 kata", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val contact = Contact(name.text, address.text, phone.text, email.text)

                if (isEdit) {
                    ContactData.contacts[index!!] = contact
                    Toast.makeText(context, "Kontak berhasil diperbarui", Toast.LENGTH_SHORT).show()
                } else {
                    ContactData.contacts.add(contact)
                    Toast.makeText(context, "Kontak berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                }

                navController.popBackStack()
            }) {
                Text(if (isEdit) "Update" else "Add")
            }
        }
    }
}


