package com.codinginflow.mvvmtodo.di

import android.app.Application
import androidx.room.Room
import com.codinginflow.mvvmtodo.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    //Provides is same as inject constructor
    //the main difference is that provides method is used for the classes that we don't own
    @Provides
    @Singleton
    fun provideDatabase(app: Application, callback: TaskDatabase.Callback) =
        Room.databaseBuilder(app, TaskDatabase::class.java, "task_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()


    @ApplicationScope //application scope is created to distinguish between two different scope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    fun provideTaskDao(db: TaskDatabase) = db.taskDao()
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope
