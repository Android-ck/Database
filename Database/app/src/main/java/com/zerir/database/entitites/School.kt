package com.zerir.database.entitites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class School(
    @PrimaryKey(autoGenerate = true) val schoolId: Long = 0,
    val schoolName: String,
)