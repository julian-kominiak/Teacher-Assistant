package pl.juliankominiak.asystentnauczyciela.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long,
    @ColumnInfo(name = "parent_subject_name") var parentSubjectName: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "surname") var surname: String
)