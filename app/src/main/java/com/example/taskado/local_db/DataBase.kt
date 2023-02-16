package com.example.taskado.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskado.Models.MiniTaskR

import com.example.taskado.Models.TaskR
import com.example.taskado.Models.User

@Database(entities = [User::class,TaskR::class, MiniTaskR::class], version = 3, exportSchema = false)
abstract class DataBase:RoomDatabase(){
    abstract fun userDao():UserDao
    abstract fun taskRDao(): TaskRDao
    abstract fun MiniTaskRDao(): MiniTaskRDao

    companion object{
        @Volatile
        private var instance : DataBase? =null
        fun getDataBase(context: Context) :DataBase {
            return instance ?: synchronized(this){
                Room.databaseBuilder(context.applicationContext,DataBase::class.java,"TaskxDB")
                    .fallbackToDestructiveMigration().build().also {
                        instance=it
                    }

            }
        }
    }
}

