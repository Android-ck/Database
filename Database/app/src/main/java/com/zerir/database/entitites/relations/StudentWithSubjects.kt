package com.zerir.database.entitites.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.zerir.database.entitites.Student
import com.zerir.database.entitites.Subject

data class StudentWithSubjects(
    @Embedded val student: Student,
    @Relation(
        parentColumn = "studentId",
        entityColumn = "subjectId",
        associateBy = Junction(StudentsSubjectsCrossRef::class),
    ) val subjects: List<Subject>,
)
