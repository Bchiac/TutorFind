package com.example.tutorapp.screen.register

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ClassDetail(
    val tutorName: String = "",
    val subject: String = "",
    val fee: String = "",
    val degree: String = "",
    val university: String = "",
    val major: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val schedule: List<Int> = emptyList(),
    val time: String = "",
    val address: String = "",
    val teachingForm: String = "",
    val avatarUrl: String = "" // nếu cần
)

class RegisterClassViewModel : ViewModel() {
    private val _classDetail = MutableStateFlow(ClassDetail())
    val classDetail: StateFlow<ClassDetail> = _classDetail

    fun loadClassByTutorId(tutorId: String) {
        Firebase.firestore.collection("classes")
            .whereEqualTo("tutorId", tutorId)
            .get()
            .addOnSuccessListener { result ->
                val doc = result.firstOrNull()
                val classData = doc?.toObject(ClassDetail::class.java)
                if (classData != null) {
                    _classDetail.value = classData
                }
            }
    }

    fun registerForClass(tutorId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val db = Firebase.firestore

        if (user == null) {
            onFailure("Bạn chưa đăng nhập")
            return
        }

        val registration = mapOf(
            "userId" to user.uid,
            "userName" to (user.displayName ?: user.email ?: "Không rõ"),
            "email" to user.email,
            "tutorId" to tutorId,
            "subject" to classDetail.value.subject,
            "status" to "Chờ duyệt"
        )

        db.collection("registrations")
            .whereEqualTo("userId", user.uid)
            .whereEqualTo("tutorId", tutorId)
            .get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {
                    onFailure("Bạn đã đăng ký lớp học này rồi.")
                } else {
                    // Kiểm tra xem đã đăng ký môn này chưa
                    db.collection("registrations")
                        .whereEqualTo("userId", user.uid)
                        .whereEqualTo("subject", classDetail.value.subject)
                        .get()
                        .addOnSuccessListener { subjectSnap ->
                            if (!subjectSnap.isEmpty) {
                                onFailure("Bạn chỉ có thể đăng ký 1 lớp cho mỗi môn học.")
                            } else {
                                db.collection("registrations")
                                    .add(registration)
                                    .addOnSuccessListener { onSuccess() }
                                    .addOnFailureListener { e ->
                                        onFailure(e.message ?: "Lỗi không xác định")
                                    }
                            }
                        }
                }
            }
    }
}
