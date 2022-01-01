package pl.juliankominiak.asystentnauczyciela.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.juliankominiak.asystentnauczyciela.model.Student
import pl.juliankominiak.asystentnauczyciela.model.Subject
import java.time.DayOfWeek

@Dao
interface TeacherAssistantDAO {

    //Subject
    @Insert
    fun insertSubject(subject: Subject)

    @Query("SELECT * FROM subjects_table")
    fun getAllSubjects(): LiveData<List<Subject>>

    @Query("DELETE FROM subjects_table")
    fun deleteAllSubjects()

    @Query("DELETE FROM subjects_table WHERE subject_name = :subjectName")
    fun deleteSubject(subjectName: String)


    //Student
    @Insert
    fun insertStudent(student: Student)

    @Query("SELECT * FROM students_table WHERE parent_subject_name = :parentSubject")
    fun getAllStudents(parentSubject: String): LiveData<List<Student>>

    @Query("DELETE FROM students_table")
    fun deleteAllStudents()

    @Query("DELETE FROM students_table WHERE " +
            "name = :name AND " +
            "surname = :surname")
    fun deleteStudent(name: String, surname: String)

    //Class
    @Insert
    fun insertClass(myClass: Class)

    @Query("SELECT * FROM classes_table " +
            "WHERE parent_subject_name = :parentSubject " +
            "ORDER BY CASE " +
            "WHEN day_of_week = 'Poniedziałek' THEN 1 " +
            "WHEN day_of_week = 'Wtorek' THEN 2 " +
            "WHEN day_of_week = 'Środa' THEN 3 " +
            "WHEN day_of_week = 'Czwartek' THEN 4 " +
            "WHEN day_of_week = 'Piątek' THEN 5 " +
            "WHEN day_of_week = 'Sobota' THEN 6 " +
            "WHEN day_of_week = 'Niedziela' THEN 7 " +
            "END ASC")
    fun getAllClasses(parentSubject: String): LiveData<List<Class>>

    @Query("DELETE FROM classes_table WHERE id = :id")
    fun deleteClass(id: Long)

    @Query("SELECT * FROM classes_table WHERE id = :id")
    fun getClass(id: Long): Class

    //Mark
    @Insert
    fun insertMark(mark: Mark)

    @Query("SELECT * FROM marks_table " +
            "WHERE parent_student_id = :parentStudent")
    fun getAllMarks(parentStudent: Long): LiveData<List<Mark>>

    @Query("DELETE FROM marks_table WHERE id = :id")
    fun deleteMark(id: Long)

    @Query("SELECT COUNT(*) FROM marks_table " +
            "WHERE parent_student_id = :parentStudent")
    fun countAllMarks(parentStudent: Long): Int

    @Query("SELECT SUM(value) FROM marks_table " +
            "WHERE parent_student_id = :parentStudent")
    fun sumAllMarks(parentStudent: Long): Float

}