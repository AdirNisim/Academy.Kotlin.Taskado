package com.example.taskado.Repositories.FireBaseAPI

import androidx.lifecycle.MutableLiveData
import com.example.taskado.Models.Task
import com.example.taskado.Repositories.TasksRepository
import com.example.taskado.utils.Constants
import com.example.taskado.utlis.safeCall
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.gson.Gson
import il.co.syntax.finalkotlinproject.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject




class TasksRepositoryFirebase @Inject constructor():TasksRepository {
    private val taskRef = FirebaseFirestore.getInstance()

    override suspend fun addTask(newTask: Task, Organization: String): Resource<Void> =
        withContext(Dispatchers.IO) {
            safeCall {
                var insert =
                    taskRef.collection(Constants.GENERALORGANIZATION).document(Organization)
                        .collection(Constants.TASKSTABLE)
                newTask.task_id = insert.document().id
                val addition = insert.document(newTask.task_id.toString()).set(newTask).await()
                Resource.success(addition)
            }
        }

    override suspend fun deleteTask(existTask: Task, Organization: String) {
        withContext(Dispatchers.IO) {
                var delete =
                    taskRef.collection(Constants.GENERALORGANIZATION).document(Organization)
                        .collection(Constants.TASKSTABLE)
                val addition = delete.document(existTask.task_id!!).delete().await()
            }
    }

    override fun getTask(task_id: String, Organization: String,callback: (Task?) -> Unit) {

            taskRef.collection(Constants.GENERALORGANIZATION).document(Organization)
                .collection(Constants.TASKSTABLE).document(task_id).get().addOnSuccessListener { documentSnapshot ->
                    val currentTask = documentSnapshot.toObject<Task>()
                    callback(currentTask)
                }
        }



    override suspend fun UpdateTask(existingTask: Task, Organization: String) {
        withContext(Dispatchers.IO) {
            var update =
                taskRef.collection(Constants.GENERALORGANIZATION).document(Organization)
                    .collection(Constants.TASKSTABLE)
            val addition = update.document(existingTask.task_id!!).set(existingTask).await()
        }
    }

    override fun getAllFavoritesTasks(
        data: MutableLiveData<Resource<List<Task>>>,
        Organization: String
    ) {
        data.postValue(Resource.loading())
        taskRef.collection(Constants.GENERALORGANIZATION).document(Organization)
            .collection(Constants.TASKSTABLE).whereEqualTo("task_favorite", true)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    data.postValue(Resource.error(e.localizedMessage))
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    data.postValue(Resource.success(snapshot.toObjects(Task::class.java)))
                } else {
                    data.postValue(Resource.error("No Data"))
                }
            }
    }


    override fun getAllTasksLiveData(
        data: MutableLiveData<Resource<List<Task>>>,
        Organization: String) {
        data.postValue(Resource.loading())
        taskRef.collection(Constants.GENERALORGANIZATION).document(Organization)
            .collection(Constants.TASKSTABLE)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    data.postValue(Resource.error(e.localizedMessage))
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    data.postValue(Resource.success(snapshot.toObjects(Task::class.java)))
                } else {
                    data.postValue(Resource.error("No Data"))
                }
            }
    }
}