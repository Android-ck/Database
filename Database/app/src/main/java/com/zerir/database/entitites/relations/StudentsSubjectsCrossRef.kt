package com.zerir.database.entitites.relations

import androidx.room.Entity

@Entity(primaryKeys = ["studentId", "subjectId"])
data class StudentsSubjectsCrossRef(
    val studentId: Long,
    val subjectId: Long,
)
