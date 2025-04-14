package com.example.tutorapp.screen.register


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ClassDetail(
    val tutorName: String,
    val subject: String,
    val fee: String,
    val degree: String,
    val university: String,
    val major: String,
    val startDate: String,
    val endDate: String,
    val schedule: List<Int>,
    val time: String,
    val address: String,
    val teachingForm: String
)

class RegisterClassViewModel : ViewModel() {
    private val _classDetail = MutableStateFlow(
        ClassDetail(
            tutorName = "Thanh Mai",
            subject = "Tiếng Anh - Lớp Ngoại Ngữ",
            fee = "1,200,000đ",
            degree = "Sinh viên",
            university = "Đại học Kinh tế TP HCM",
            major = "Ngôn ngữ Anh",
            startDate = "02-10-2019",
            endDate = "02-10-2020",
            schedule = listOf(2,3,5),
            time = "19:00 - 20:30",
            address = "38/420 Hẻm 514 Lê Đức Thọ, Gò Vấp, TP.HCM",
            teachingForm = "DẠY TẠI NHÀ"
        )
    )
    val classDetail: StateFlow<ClassDetail> = _classDetail
}
