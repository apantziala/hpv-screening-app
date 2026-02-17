package com.example.hpvtest



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HpvAppScreen()
        }
    }
}

@Composable
fun HpvAppScreen() {
    var age by remember { mutableStateOf("") }
    var isPositive by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("Συμπληρώστε τα στοιχεία σας") }
    val protocol = HpvProtocol()

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text("Εφαρμογή Πρωτοκόλλου HPV (WHO 2021)", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Πεδίο Ηλικίας
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Ηλικία") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Επιλογή Αποτελέσματος
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(checked = isPositive, onCheckedChange = { isPositive = it })
            Text("Το τεστ ήταν θετικό;")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Κουμπί Υπολογισμού
        Button(
            onClick = {
                val ageInt = age.toIntOrNull() ?: 0
                resultText = protocol.calculateGuideline(ageInt,"HPV TEST", isPositive)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Λήψη Οδηγίας")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Εμφάνιση Αποτελέσματος
        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = resultText,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}