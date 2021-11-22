package com.zerir.database

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.zerir.database.entitites.*
import com.zerir.database.entitites.relations.StudentsSubjectsCrossRef
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.DividerItemDecoration
import com.zerir.database.db.AppDatabase
import com.zerir.database.db.Dao

class MainActivity : AppCompatActivity() {

    private lateinit var dao: Dao

    private val itemsToPreview: MutableList<String> = mutableListOf()
    private val adapter by lazy {  PreviewAdapter(itemsToPreview) }

    private var lastSchoolClicked = 1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dao = AppDatabase.getInstance(applicationContext).dao

        val sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

        fillDbForOnce(sharedPreferences)

        setupUi()
    }

    private fun fillDbForOnce(sharedPreferences: SharedPreferences) {

        val isDataFilled = sharedPreferences.getBoolean(IS_FILLED_KEY, false)

        if(isDataFilled) return
        else {
            sharedPreferences.edit {
                putBoolean(IS_FILLED_KEY, true)
                apply()
            }
        }

        val visitors: List<Visitor> = generateVisitors()
        val schools: List<School> = generateSchools()
        val directors: List<Director> = generateDirectors(schools.size)
        val students: List<Student> = generateStudents(schools.size)
        val subjects: List<Subject> = generateSubjects()
        val studentsSubjectsRelation: List<StudentsSubjectsCrossRef> = generateStudentsSubjectsRelation(
            studentsSize = students.size,
            subjectsSize = subjects.size,
        )

        lifecycleScope.launch {
            visitors.forEach { dao.insertVisitor(it) }
            schools.forEach { dao.insertSchool(it) }
            directors.forEach { dao.insertDirector(it) }
            students.forEach { dao.insertStudent(it) }
            subjects.forEach { dao.insertSubject(it) }
            studentsSubjectsRelation.forEach { dao.insertStudentSubjectCrossRef(it) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupUi() {
        findViewById<Button>(R.id.schoolDirector_bt).setOnClickListener {
            lifecycleScope.launch {
                val mixed = dao.getSchoolAndDirectorBySchoolId(schoolId = lastSchoolClicked)
                lastSchoolClicked = if (lastSchoolClicked == 1L) 2L else 1L
                mixed?.let {
                    itemsToPreview.clear()
                    itemsToPreview.add("* School Name: ${mixed.school.schoolName}")
                    itemsToPreview.add("* Director Name: ${mixed.director.directorName}")
                }
                adapter.notifyDataSetChanged()
            }
        }
        findViewById<Button>(R.id.studentOfSchool_bt).setOnClickListener {
            lifecycleScope.launch {
                val mixed = dao.getStudentsFromSchoolBySchoolId(schoolId = lastSchoolClicked)
                lastSchoolClicked = if (lastSchoolClicked == 1L) 2L else 1L
                mixed?.let {
                    itemsToPreview.clear()
                    itemsToPreview.add("* School Name: ${mixed.school.schoolName}")
                    itemsToPreview.add("* Students: ${mixed.students.size} ->")
                    itemsToPreview.addAll(mixed.students.map { it.studentName })
                }
                adapter.notifyDataSetChanged()
            }
        }
        findViewById<Button>(R.id.students_bt).setOnClickListener {
            lifecycleScope.launch {
                val mixed = dao.getStudentsOfSubject(subjectId = (1..NUMBER_OF_SUBJECTS).random().toLong())
                mixed?.let {
                    itemsToPreview.clear()
                    itemsToPreview.add("* Subject Name: ${mixed.subject.subjectName}")
                    itemsToPreview.add("* Students: ${mixed.students.size} ->")
                    itemsToPreview.addAll(mixed.students.map { it.studentName })
                }
                adapter.notifyDataSetChanged()
            }
        }
        findViewById<Button>(R.id.subjects_bt).setOnClickListener {
            lifecycleScope.launch {
                val mixed = dao.getSubjectsOfStudent(studentId = (1..NUMBER_OF_STUDENTS).random().toLong())
                mixed?.let {
                    itemsToPreview.clear()
                    itemsToPreview.add("* Student Name: ${mixed.student.studentName}")
                    itemsToPreview.add("* Subjects: ${mixed.subjects.size} ->")
                    itemsToPreview.addAll(mixed.subjects.map { it.subjectName })
                }
                adapter.notifyDataSetChanged()
            }
        }
        findViewById<Button>(R.id.visitors_bt).setOnClickListener {
            lifecycleScope.launch {
                itemsToPreview.clear()
                val visitors = dao.getAllVisitors()
                itemsToPreview.add("* Visitors: ${visitors.size} ->")
                itemsToPreview.addAll(visitors.map{ it.visitorName })
                adapter.notifyDataSetChanged()
            }
        }

        findViewById<RecyclerView>(R.id.list_rv).also {
            it.setHasFixedSize(true)
            it.isNestedScrollingEnabled = false
            val dividerItemDecoration = DividerItemDecoration(
                applicationContext, LinearLayout.VERTICAL
            )
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    private fun generateVisitors(): List<Visitor> {
        val list = mutableListOf<Visitor>()
        for (n in 1..NUMBER_OF_VISITORS) {
            list.add(Visitor(visitorName = "Visitor $n"))
        }
        return list
    }

    private fun generateSchools(): List<School> {
        val list = mutableListOf<School>()
        for (n in 1..NUMBER_OF_SCHOOLS_AND_DIRECTORS) {
            list.add(School(schoolName = "School $n"))
        }
        return list
    }

    private fun generateDirectors(schoolsSize: Int): List<Director> {
        val list = mutableListOf<Director>()
        for (n in 1..schoolsSize) {
            list.add(Director(directorName = "Director $n", schoolId = n.toLong()))
        }
        return list
    }

    private fun generateStudents(schoolsSize: Int): List<Student> {
        val list = mutableListOf<Student>()
        for (n in 1..NUMBER_OF_STUDENTS) {
            list.add(
                Student(
                    studentName = "Student $n",
                    semester = (1..4).random(),
                    schoolId = (1..schoolsSize).random().toLong(),
                ),
            )
        }
        return list
    }

    private fun generateSubjects(): List<Subject> {
        val list = mutableListOf<Subject>()
        for (n in 1..NUMBER_OF_SUBJECTS) {
            list.add(Subject(subjectName = "Subject $n"))
        }
        return list
    }

    private fun generateStudentsSubjectsRelation(
        studentsSize: Int,
        subjectsSize: Int,
    ): List<StudentsSubjectsCrossRef> {
        val list = mutableListOf<StudentsSubjectsCrossRef>()
        for (n in 1..200) {
            list.add(
                StudentsSubjectsCrossRef(
                    studentId = (1..studentsSize).random().toLong(),
                    subjectId = (1..subjectsSize).random().toLong(),
                )
            )
        }
        return list
    }

    companion object {
        const val NUMBER_OF_SCHOOLS_AND_DIRECTORS = 2
        const val NUMBER_OF_STUDENTS = 100
        const val NUMBER_OF_SUBJECTS = 25
        const val NUMBER_OF_VISITORS = 5

        const val SHARED_PREF = "app-db-shared-pref"
        const val IS_FILLED_KEY = "is-filled"
    }
}