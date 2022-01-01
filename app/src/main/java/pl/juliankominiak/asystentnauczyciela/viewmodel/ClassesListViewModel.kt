package pl.juliankominiak.asystentnauczyciela.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.juliankominiak.asystentnauczyciela.model.Class
import pl.juliankominiak.asystentnauczyciela.model.TeacherAssistantDAO
import pl.juliankominiak.asystentnauczyciela.model.TeacherAssistantDatabase
import java.time.DayOfWeek
class ClassesListViewModel(application: Application, val arguments: Bundle?) :
    AndroidViewModel(application) {

    private val dao: TeacherAssistantDAO

    init {
        dao = TeacherAssistantDatabase
            .getInstance(application).dao
    }

    fun add(myClass: Class) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertClass(myClass)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteClass(id)
        }
    }

    fun get(id: Long): Class {
        return dao.getClass(id)
    }

    val classes: LiveData<List<Class>> = dao.getAllClasses(arguments?.get("subjectName") as String)

}

