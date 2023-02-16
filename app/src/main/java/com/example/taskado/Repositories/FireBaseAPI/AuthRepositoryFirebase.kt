package com.example.taskado.Repositories.FireBaseAPI

import android.provider.ContactsContract.Data
import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import com.example.taskado.Models.MiniTaskR
import com.example.taskado.Models.Task
import com.example.taskado.Models.TaskR
import com.example.taskado.Models.User
import com.example.taskado.Repositories.AuthRepository
import com.example.taskado.dataclasses.MiniTask
import com.example.taskado.local_db.DataBase
import com.example.taskado.utils.Constants
import com.example.taskado.utlis.safeCall
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import il.co.syntax.finalkotlinproject.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AuthRepositoryFirebase @Inject constructor(database: DataBase): AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userRef = FirebaseFirestore.getInstance().collection(Constants.USERSTABLE)
    private val database = database;
    override suspend fun currentUser(): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                var user = userRef.document(firebaseAuth.currentUser!!.uid).get().await()
                    .toObject(User::class.java)!!
                Resource.success(user)
            }
        }
    }

    override suspend fun login(email: String, password: String): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result: AuthResult =
                    firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val user: User =
                    userRef.document(result.user?.uid!!).get().await().toObject(User::class.java)!!
                    database.userDao().resetUsers()
                val y = database.userDao().addUser(user);
                Resource.success(user)
            }
        }
    }

    override suspend fun createUser(newUser: User): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                //add new user to fire base auth
                val registrationResult = firebaseAuth.createUserWithEmailAndPassword(
                    newUser.user_email.toString(),
                    newUser.user_password.toString()
                ).await()
                val userId = registrationResult.user?.uid!!
                newUser.user_id = userId
                //add new user to fire base firestore
                userRef.document(userId).set(newUser).await()
                Resource.success(newUser)
            }
        }
    }

    override suspend fun UpdateUser(existingUser: User): Resource<User> {
        database.userDao().updaeUser(existingUser)
        return withContext(Dispatchers.IO) {
            safeCall {
                val currentUser = FirebaseAuth.getInstance().currentUser
                currentUser?.updateEmail(existingUser.user_email.toString())?.await()
                //add new user to fire base auth
                userRef.document(existingUser.user_id).set(existingUser).await()
                updateUserRoom(existingUser)
                Resource.success(existingUser)
            }
        }
    }

    override fun RemoveAllTaskR() {
        return runBlocking {
            database.MiniTaskRDao().deleteMiniTasksR()
            database.taskRDao().deleteTasksR()
        }
    }

    override fun setTasksR(data: List<Task>) {
        return runBlocking {
            for (task in data) {
                var convertedToTaskR = TaskR(
                    task.task_id!!,
                    task.task_title,
                    task.task_start_date,
                    task.task_end_date,
                    task.task_description,
                    task.task_creator,
                    task.mini_task_done,
                    task.task_done,
                    task.task_favorite
                )
                database.taskRDao().addTaskR(convertedToTaskR)
                for (item in task.task_mini_array) {
                    var convertedToMiniTaskR =
                        MiniTaskR(
                            0,
                            task.task_id!!,
                            item.miniTaskName,
                            item.status,
                            item.taskCloser
                        )
                    database.MiniTaskRDao().addMiniTaskR(convertedToMiniTaskR)
                }
            }
        }
    }



    override suspend fun logOut() {
        database.userDao().resetUsers()
        firebaseAuth.signOut()
    }

    override fun getUser(): User {
        return runBlocking {
            withContext(Dispatchers.IO) {
                database.userDao().getAllUsers().first()
            }
        }
    }

    override fun getBuildTaskByR(PartenId: String): Task {
        return runBlocking {
            withContext(Dispatchers.IO) {

                var RoomTaskR = database.taskRDao().getTaskR(PartenId)
                var RoomMiniTaskR = database.MiniTaskRDao().getAllMiniTasksRbyId(PartenId)
                var MiniTask = mutableListOf<MiniTask>()
                for (mini in RoomMiniTaskR) {
                    var MiniT = MiniTask(mini.miniTaskName, mini.status, mini.taskCloser)
                    MiniTask.add(MiniT)
                }
                var choosenTask = Task(
                    PartenId,
                    RoomTaskR.task_title,
                    RoomTaskR.task_start_date,
                    RoomTaskR.task_end_date,
                    RoomTaskR.task_description,
                    MiniTask,
                    RoomTaskR.task_creator,
                    RoomTaskR.mini_task_done,
                    RoomTaskR.task_done,
                    RoomTaskR.task_favorite
                )
                 choosenTask
            }
        }
    }

    override fun getAllFavoriteTasks(): MutableList<TaskR> {
        return database.taskRDao().getFavoriteTaskR(1)
    }

    override suspend fun updateUserRoom(currentUser: User) {
        database.userDao().resetUsers()
        database.userDao().addUser(currentUser)
    }
}