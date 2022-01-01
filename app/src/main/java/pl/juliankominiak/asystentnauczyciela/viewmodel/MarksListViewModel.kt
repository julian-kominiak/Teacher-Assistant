package pl.juliankominiak.asystentnauczyciela.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.juliankominiak.asystentnauczyciela.model.Mark
import pl.juliankominiak.asystentnauczyciela.model.TeacherAssistantDAO
import pl.juliankominiak.asystentnauczyciela.model.TeacherAssistantDatabase
import pl.juliankominiak.asystentnauczyciela.model.Student
import kotlin.math.round

class MarksListViewModel(application: Application, val arguments: Bundle?):
    AndroidViewModel(application) {

    private val dao: TeacherAssistantDAO
    init {
        dao = TeacherAssistantDatabase
            .getInstance(application).dao
    }

    fun add(mark: Mark) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertMark(mark)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteMark(id)
        }
    }

    fun getAverage(): String {
        val count = dao.countAllMarks(arguments?.get("studentId") as Long)
        val sum = dao.sumAllMarks(arguments.get("studentId") as Long)
        return "%.2f".format(sum / count)
    }



    val marks: LiveData<List<Mark>> = dao.getAllMarks(arguments?.get("studentId") as Long)
}