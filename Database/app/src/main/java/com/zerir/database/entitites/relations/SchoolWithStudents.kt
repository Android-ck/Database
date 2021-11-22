package com.zerir.database.entitites.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.zerir.database.entitites.School
import com.zerir.database.entitites.Student

data class SchoolWithStudents(
    @Embedded val school: School,
    @Relation(
        parentColumn = "schoolId",
        entityColumn = "schoolId"
    ) val students: List<Student>,
)
