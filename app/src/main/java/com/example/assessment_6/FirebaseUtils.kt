package com.example.assessment_6

import com.google.firebase.database.FirebaseDatabase

class FirebaseUtils {
    private val database = FirebaseDatabase.getInstance()
    val categoriesRef = database.getReference("Categories")
    val quotesRef = database.getReference("Quotes")
}