package com.example.hpvtest


class HpvProtocol { // Το ονομάζουμε HpvProtocol για να ταιριάζει με το MainActivity σου

    fun calculateGuideline(age: Int, testType: String, isPositive: Boolean): String {
        // Αν το αποτέλεσμα είναι θετικό, εφαρμόζουμε τη λογική Triage/Θεραπείας
        if (isPositive) {
            return "Απαιτείται περαιτέρω έλεγχος (Triage/Κολποσκόπηση) ή άμεση θεραπεία βάσει ASCCP και WHO."
        }

        // Αν είναι αρνητικό, εφαρμόζουμε τα ηλικιακά όρια για πρόληψη
        return when {
            // Πρωτόκολλο ΕΟΔΥ για νεαρές ηλικίες
            age in 21..29 -> "Συνιστάται Pap-test κάθε 3 έτη."

            // Πρωτόκολλο ΕΟΔΥ/WHO για ηλικίες 30+
            age >= 30 && testType == "HPV DNA" -> "Συνιστάται HPV DNA test κάθε 5 έτη."

            age >= 30 && testType == "Pap Test" -> "Ο ΕΟΔΥ προκρίνει το HPV DNA test για την ηλικία σας, αλλά το Pap Test επαναλαμβάνεται ανά 3ετία."

            else -> "Ο προληπτικός έλεγχος ξεκινά από τα 21 έτη. Συμβουλευτείτε γιατρό."
        }
    }
}