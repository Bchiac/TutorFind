package com.example.tutorapp.screen.home

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Model class cho dữ liệu lớp học từ Firebase
data class ClassItem(
    val tutorId: String = "",
    val subject: String = "",
    val tutorName: String = "",
    val fee: String = ""
)

class HomeViewModel : ViewModel() {

    private val _classList = MutableStateFlow<List<ClassItem>>(emptyList())
    val classList: StateFlow<List<ClassItem>> = _classList.asStateFlow()

    val searchQuery = MutableStateFlow("")
    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    init {
        loadClassList()
    }

    fun loadClassList() {
        Firebase.firestore.collection("tutors")
            .get()
            .addOnSuccessListener { result ->
                val list = result.mapNotNull { doc ->
                    val data = doc.data
                    ClassItem(
                        tutorId = doc.id,
                        subject = data["subject"] as? String ?: "",
                        tutorName = data["name"] as? String ?: "",
                        fee = data["fee"] as? String ?: ""
                    )
                }
                _classList.value = list
            }
    }
}
