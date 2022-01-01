package pl.juliankominiak.asystentnauczyciela.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.time.DayOfWeek

@Entity(tableName = "classes_table")
data class Class(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long,
    @ColumnInfo(name = "parent_subject_name") var parentSubjectName: String,
    @ColumnInfo(name = "day_of_week") var dayOfWeek: String,
    @ColumnInfo(name = "start_time") var startTime: String,
    @ColumnInfo(name = "end_time") var endTime: String
)