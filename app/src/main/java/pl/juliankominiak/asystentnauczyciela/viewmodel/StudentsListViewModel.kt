package pl.juliankominiak.asystentnauczyciela.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.juliankominiak.asystentnauczyciela.model.TeacherAssistantDAO
import pl.juliankominiak.asystentnauczyciela.model.TeacherAssistantDatabase
import pl.juliankominiak.asystentnauczyciela.model.Student

class StudentsListViewModel(application: Application, val arguments: Bundle?):
    AndroidViewModel(application) {

    private val dao: TeacherAssistantDAO
    init {
        dao = TeacherAssistantDatabase
            .getInstance(application).dao
    }

    fun add(student: Student) {
                     viewModelScope.launch(Dispatchers.IO) {
            dao.insertStudent(student)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteAllStudents()
        }
    }

    fun delete(name: String, surname: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteStudent(name, surname)
        }
    }

    val students: LiveData<List<Student>> = dao.getAllStudents(arguments?.get("subjectName") as String)
}