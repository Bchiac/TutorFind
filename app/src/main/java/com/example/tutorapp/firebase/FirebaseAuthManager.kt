package com.example.tutorapp.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseAuthManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun signup(
        email: String,
        password: String,
        fullName: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val uid = it.user?.uid ?: return@addOnSuccessListener
                val userData = mapOf(
                    "email" to email,
                    "name" to fullName,
                    "createdAt" to System.currentTimeMillis()
                )
                db.collection("users").document(uid).set(userData)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e -> onError("Lưu user thất bại: ${e.message}") }
            }
            .addOnFailureListener { e ->
                onError(e.message ?: "Đăng ký thất bại")
            }
    }

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e.message ?: "Đăng nhập thất bại") }
    }
}
