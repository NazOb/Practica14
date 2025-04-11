package mx.edu.itesca.practica14

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class TaskViewModel: ViewModel() {
    private val db = Firebase.firestore
    private var _taskList = MutableLiveData<List<Task>>(emptyList())

    val taskList : LiveData<List<Task>> = _taskList

    init {
        getTasks()
    }

    fun getTasks(){
        println(db.toString())
        viewModelScope.launch(Dispatchers.IO){
            try {
                val result = db.collection("tasks").get().await()
                val tasks = result.documents.mapNotNull { it.toObject(Task :: class.java) }
                _taskList.postValue(tasks)
            }catch (e : Exception){

            }
        }
    }

    fun addTasks(task : Task){
        task.id = UUID.randomUUID().toString()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("tasks").document(task.id).set(task).await()
                _taskList.postValue(_taskList.value?.plus(task))
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun updateTask(task : Task){
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("tasks").document(task.id).update(task.toMap()).await()
            _taskList.postValue(_taskList.value?.map { if(it.id == task.id) task else it })
        }
    }

    fun deleteTask(id : String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("tasks").document(id).delete().await()
                _taskList.postValue(_taskList.value?.filter { it.id != id })
            }catch (e : Exception){

            }
        }
    }
}