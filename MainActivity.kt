package com.example.hpvtest



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight

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
    var isHivPositive by remember{mutableStateOf(false)}
    var resultText by remember { mutableStateOf("Συμπληρώστε τα στοιχεία σας") }
    val protocol = HpvProtocol()

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text("Εφαρμογή Πρωτοκόλλου HPV (WHO 2021)", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))
        //Tο checkbox hiv
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically){
            Checkbox(
                checked =isHivPositive,
                onCheckedChange = {isHivPositive=it}
            )
            Text("Ιστορικό HIV(Ειδικό Πρωτόκολλο)")
        }

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
                resultText = protocol.calculateGuideline(ageInt,"HPV TEST", isPositive,isHivPositive)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Λήψη Οδηγίας")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Εμφάνιση Αποτελέσματος
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isPositive) MaterialTheme.colorScheme.errorContainer
                else MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Text(
                text = resultText,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

//  Εμφάνιση λίστας μόνο αν είναι θετικό το αποτέλεσμα
        if (isPositive) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Προτεινόμενα Κέντρα για περαιτέρω έλεγχο (Triage/Κολποσκόπηση):",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            ContactDoctorsList(selectedCity = "Αθήνα")
        }
    }
}
@Composable
fun ContactDoctorsList(selectedCity: String) {
    val filteredCenters = healthCenters.filter { it.city == selectedCity }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Προτεινόμενες Δομές Υγείας στην $selectedCity:", style = MaterialTheme.typography.titleMedium)

        filteredCenters.forEach { center ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(center.name, fontWeight = FontWeight.Bold)
                        Text(center.phone)
                    }
                    Button(onClick = { /* Εδώ θα έμπαινε ο κώδικας για κλήση */ }) {
                        Text("Κλήση")
                    }
                }
            }
        }
    }
}

