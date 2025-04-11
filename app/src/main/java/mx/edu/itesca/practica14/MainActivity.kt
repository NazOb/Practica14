package mx.edu.itesca.practica14

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.firestore.FirebaseFirestore
import mx.edu.itesca.practica14.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : TaskAdapter
    private lateinit var viewModel : TaskViewModel

    var editTask = Task()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        FirebaseApp.initializeApp(this)
        // ---- new ----
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        viewModel.taskList.observe(this){
            tasks -> setupRecylerView(tasks)
        }

        binding.btnAddTask.setOnClickListener {
            val task = Task(
                title = binding.etTitle.text.toString(),
                description = binding.etDescription.text.toString()
            )

            viewModel.addTasks(task)

            binding.etTitle.setText("")
            binding.etDescription.setText("")
        }

        binding.btnUpdateTask.setOnClickListener {
            editTask.title = ""
            editTask.title = ""

            editTask.title = binding.etTitle.text.toString()
            editTask.description = binding.etDescription.text.toString()

            viewModel.updateTask(editTask)
        }
        // -------------


    }

    fun setupRecylerView(tasksList: List<Task>){
        adapter = TaskAdapter(tasksList, ::deleteTask, ::updateTask)
        binding.rvTasks.adapter = adapter
    }

    fun deleteTask(id: String){
        viewModel.deleteTask(id)
    }

    fun updateTask(task: Task){
        editTask = task

        binding.etTitle.setText(editTask.title)
        binding.etDescription.setText(editTask.description)
    }
}