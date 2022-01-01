package pl.juliankominiak.asystentnauczyciela.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects_table")
data class Subject(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "subject_name") var subjectName: String,
)