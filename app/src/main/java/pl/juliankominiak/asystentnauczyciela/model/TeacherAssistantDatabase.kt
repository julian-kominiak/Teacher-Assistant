package pl.juliankominiak.asystentnauczyciela.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.juliankominiak.asystentnauczyciela.model.Class

@Database(entities = [Subject::class, Student::class, Class::class, Mark::class], version = 13,
    exportSchema = false)
abstract class TeacherAssistantDatabase : RoomDatabase() {

    abstract val dao: TeacherAssistantDAO

    companion object {

        @Volatile
        private var INSTANCE: TeacherAssistantDatabase? = null

        fun getInstance(context: Context): TeacherAssistantDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TeacherAssistantDatabase::class.java,
                        "teacher_assistant_database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}