package com.example.taskado.di

import android.content.Context
import com.example.taskado.local_db.DataBase
import com.example.taskado.local_db.MiniTaskRDao
import com.example.taskado.local_db.TaskRDao
import com.example.taskado.local_db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppMoudle {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context) :DataBase = DataBase.getDataBase(appContext)


    @Provides
    @Singleton
    fun provideUserDao(dataBase: DataBase) : UserDao = dataBase.userDao()


    @Provides
    @Singleton
    fun provideTaskRDao(dataBase: DataBase) : TaskRDao = dataBase.taskRDao()

    @Provides
    @Singleton
    fun MiniTaskRDao(dataBase: DataBase) : MiniTaskRDao = dataBase.MiniTaskRDao()

}