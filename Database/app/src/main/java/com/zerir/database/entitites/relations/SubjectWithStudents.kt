package com.zerir.database.entitites.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.zerir.database.entitites.Student
import com.zerir.database.entitites.Subject

data class SubjectWithStudents(
    @Embedded val subject: Subject,
    @Relation(
        parentColumn = "subjectId",
        entityColumn = "studentId",
        associateBy = Junction(StudentsSubjectsCrossRef::class),
    ) val students: List<Student>,
)
