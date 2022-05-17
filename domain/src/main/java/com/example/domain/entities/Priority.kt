package com.example.domain.entities

enum class Priority(val intValue: Int, val res: String) {
    Low(2, "Low"),
    Medium(1, "Medium"),
    High(0, "High")
}