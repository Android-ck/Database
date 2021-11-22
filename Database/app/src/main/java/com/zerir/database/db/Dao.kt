package com.zerir.database.db

import androidx.room.*
import androidx.room.Dao
import com.zerir.database.entitites.*
import com.zerir.database.entitites.relations.*

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchool(school: School)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDirector(director: Director)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: Subject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudentSubjectCrossRef(crossRef: StudentsSubjectsCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisitor(visitor: Visitor)

    @Transaction
    @Query("SELECT * FROM school WHERE schoolId = :schoolId")
    suspend fun getSchoolAndDirectorBySchoolId(schoolId: Long): SchoolAndDirector?

    @Transaction
    @Query("SELECT * FROM school WHERE schoolId = :schoolId")
    suspend fun getStudentsFromSchoolBySchoolId(schoolId: Long): SchoolWithStudents?

    @Transaction
    @Query("SELECT * FROM subject WHERE subjectId = :subjectId")
    suspend fun getStudentsOfSubject(subjectId: Long): SubjectWithStudents?

    @Transaction
    @Query("SELECT * FROM student WHERE studentId = :studentId")
    suspend fun getSubjectsOfStudent(studentId: Long): StudentWithSubjects?

    @Query("SELECT * FROM visitor")
    suspend fun getAllVisitors(): List<Visitor>

}