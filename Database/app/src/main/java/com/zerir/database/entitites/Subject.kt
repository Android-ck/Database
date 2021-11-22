package com.zerir.database.entitites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [ Index(value = ["subjectName"], unique = true) ])
data class Subject(
    @PrimaryKey(autoGenerate = true) val subjectId: Long = 0,
    @ColumnInfo(name = "subjectName") val subjectName: String,
)
