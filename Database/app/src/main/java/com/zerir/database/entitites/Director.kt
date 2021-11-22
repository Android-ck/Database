package com.zerir.database.entitites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Director(
    @PrimaryKey(autoGenerate = true) val directorId: Long = 0,
    val directorName: String,
    val schoolId: Long,
)
