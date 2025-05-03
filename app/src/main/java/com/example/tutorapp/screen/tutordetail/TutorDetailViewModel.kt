package com.example.tutorapp.screen.tutordetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
data class TutorDetail(
    val name: String = "",
    val subject: String = "",
    val fee: String = "",
    val level: String = "",
    val university: String = "",
    val major: String = "",
    val avatarUrl: String = "",
    val phoneNumber: String = ""
)




class TutorDetailViewModel : ViewModel() {

    private val _tutor = MutableStateFlow(TutorDetail())
    val tutor: StateFlow<TutorDetail> = _tutor

    fun loadTutorById(id: String) {
        Firebase.firestore.collection("tutors").document(id)
            .get()
            .addOnSuccessListener { document ->
                val tutorData = document.toObject(TutorDetail::class.java)
                if (tutorData != null) {
                    _tutor.value = tutorData
                }
            }
            .addOnFailureListener {
                // TODO: handle error
            }
    }
}

