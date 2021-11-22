package com.zerir.database.entitites.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.zerir.database.entitites.Director
import com.zerir.database.entitites.School

data class SchoolAndDirector(
    @Embedded val school: School,
    @Relation(
        parentColumn = "schoolId",
        entityColumn = "schoolId",
    ) val director: Director
)
