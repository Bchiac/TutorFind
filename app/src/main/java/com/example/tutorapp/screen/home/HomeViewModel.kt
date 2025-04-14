package com.example.tutorapp.screen.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Model class cho dữ liệu lớp học
data class ClassItem(
    val subject: String,
    val tutorName: String,
    val fee: String
)

class HomeViewModel : ViewModel() {

    // StateFlow để quản lý danh sách lớp học
    private val _classList = MutableStateFlow(
        listOf(
            ClassItem("Tiếng Anh", "Thanh Mai", "1,200,000đ"),
            ClassItem("Toán Học", "Nguyễn An", "1,000,000đ"),
            ClassItem("Vật Lý", "Trần Bình", "1,100,000đ")
        )
    )

    val classList: StateFlow<List<ClassItem>> = _classList.asStateFlow()

    // Hàm giả lập cập nhật dữ liệu từ API (nếu muốn sau này)
    fun updateClassList(newList: List<ClassItem>) {
        _classList.value = newList
    }
}
