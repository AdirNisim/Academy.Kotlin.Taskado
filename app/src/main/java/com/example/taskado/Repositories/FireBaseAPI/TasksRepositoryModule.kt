package com.example.taskado.Repositories.FireBaseAPI

import com.example.taskado.Repositories.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TasksRepositoryModule {
    @Binds
    abstract fun TasksRepositoryFirebaseImpl(eventsRepositoryFirebase: TasksRepositoryFirebase):TasksRepository
}