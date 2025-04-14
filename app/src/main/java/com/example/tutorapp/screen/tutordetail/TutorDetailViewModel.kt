package com.example.tutorapp.screen.tutordetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TutorDetail(
    val name: String = "",
    val subject: String = "",
    val fee: String = "",
    val level: String = "",
    val university: String = "",
    val major: String = "",
    val degreeImageUrl: String = "", // Đường dẫn ảnh bằng cấp

)

class TutorDetailViewModel : ViewModel() {

    private val _tutor = MutableStateFlow(
        TutorDetail(
            name = "Thanh Mai",
            subject = "Tiếng Anh",
            fee = "1,200,000đ",
            level = "Sinh viên",
            university = "Đại học Kinh Tế",
            major = "Ngôn ngữ Anh",
            degreeImageUrl = " ", // Có thể dùng ảnh mẫu

        )
    )

    val tutor: StateFlow<TutorDetail> = _tutor.asStateFlow()
}
