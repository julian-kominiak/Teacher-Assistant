package pl.juliankominiak.asystentnauczyciela.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.juliankominiak.asystentnauczyciela.model.TeacherAssistantDAO
import pl.juliankominiak.asystentnauczyciela.model.TeacherAssistantDatabase
import pl.juliankominiak.asystentnauczyciela.model.Subject

class SubjectsListViewModel(application: Application):
    AndroidViewModel(application) {

    private val dao: TeacherAssistantDAO
    init {
        dao = TeacherAssistantDatabase
            .getInstance(application).dao
    }

    fun add(subject: Subject) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertSubject(subject)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteAllSubjects()
        }
    }

    fun delete(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteSubject(name)
        }
    }

    val subjects:LiveData<List<Subject>> = dao.getAllSubjects()

}