package com.zerir.database.entitites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey(autoGenerate = true) val studentId: Long = 0,
    val studentName: String,
    val semester: Int,
    val schoolId: Long,
)
