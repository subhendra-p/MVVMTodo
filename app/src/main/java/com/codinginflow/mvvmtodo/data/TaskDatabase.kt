package com.codinginflow.mvvmtodo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codinginflow.mvvmtodo.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


//Dependency injection means that classes that use other classes should not be responsible for
// creating or searching them
@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    //Provider initialises classes when we use it
    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            //db operations
            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(
                    task = Task(
                        name = "Wash The Dishes"
                    )
                )
                dao.insert(
                    task = Task(
                        name = "Do the laundry"
                    )
                )
                dao.insert(
                    task = Task(
                        name = "Buy groceries", important = true
                    )
                )
                dao.insert(
                    task = Task(
                        name = "Prepare food", completed = true
                    )
                )
                dao.insert(
                    task = Task(
                        name = "Call Mom"
                    )
                )
                dao.insert(
                    task = Task(
                        name = "Visit Grandma", completed = true
                    )
                )
                dao.insert(
                    task = Task(
                        name = "Repair my bike"
                    )
                )
                dao.insert(
                    task = Task(
                        name = "Call Elon Musk"
                    )
                )
            }
        }
    }
}