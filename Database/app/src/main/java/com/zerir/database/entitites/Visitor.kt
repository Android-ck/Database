package com.zerir.database.entitites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Visitor(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val visitorName: String,
)
