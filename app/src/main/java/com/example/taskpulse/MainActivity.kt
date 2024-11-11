package com.example.taskpulse



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>()
    private val sharedPreferences by lazy { getSharedPreferences("task_prefs", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView and adapter
        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskList, { position ->
            showEditTaskDialog(taskList[position], position)
        }, { position ->
            deleteTask(position)
        })
        taskRecyclerView.adapter = taskAdapter


        loadTasksFromPreferences()

        //  FloatingActionButton
        val addTaskButton: FloatingActionButton = findViewById(R.id.addTaskButton)
        addTaskButton.setOnClickListener {
            showAddTaskDialog()
        }


        val navTasks: ImageButton = findViewById(R.id.nav_tasks)
        val navTimer: ImageButton = findViewById(R.id.nav_timer)
        val navReminders: ImageButton = findViewById(R.id.nav_reminders)
        val navProfile: ImageButton = findViewById(R.id.nav_profile)

        navTasks.setOnClickListener {
            // Stay on MainActivity
        }

        navTimer.setOnClickListener {
            startActivity(Intent(this, TimerActivity::class.java))
        }

        navReminders.setOnClickListener {
            startActivity(Intent(this, ReminderActivity::class.java))
        }

        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null)
        val taskEditText = dialogView.findViewById<EditText>(R.id.taskEditText)
        val addTaskButton = dialogView.findViewById<Button>(R.id.addTaskButton)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Add New Task")
            .setView(dialogView)
            .setNegativeButton("Cancel", null)
            .create()

        addTaskButton.setOnClickListener {
            val taskName = taskEditText.text.toString()
            if (taskName.isNotEmpty()) {
                addTask(taskName)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun addTask(taskName: String) {
        val newTask = Task(taskName)
        taskList.add(newTask)
        taskAdapter.notifyItemInserted(taskList.size - 1)
        saveTasksToPreferences()
    }

    private fun showEditTaskDialog(task: Task, position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null)
        val taskEditText = dialogView.findViewById<EditText>(R.id.taskEditText)
        taskEditText.setText(task.taskName)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Edit Task")
            .setView(dialogView)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Save") { _, _ ->
                val newTaskName = taskEditText.text.toString()
                if (newTaskName.isNotEmpty()) {
                    task.taskName = newTaskName
                    taskAdapter.notifyItemChanged(position)
                    saveTasksToPreferences()
                }
            }
            .create()

        dialog.show()
    }

    private fun deleteTask(position: Int) {
        taskList.removeAt(position)
        taskAdapter.notifyItemRemoved(position)
        saveTasksToPreferences()
    }

    private fun saveTasksToPreferences() {
        val taskJsonArray = JSONArray()
        taskList.forEach { task ->
            val jsonObject = JSONObject().apply {
                put("taskName", task.taskName)
                put("isCompleted", task.isCompleted)
            }
            taskJsonArray.put(jsonObject)
        }
        sharedPreferences.edit().putString("task_list", taskJsonArray.toString()).apply()
    }

    private fun loadTasksFromPreferences() {
        val taskJsonString = sharedPreferences.getString("task_list", null) ?: return
        val taskJsonArray = JSONArray(taskJsonString)
        for (i in 0 until taskJsonArray.length()) {
            val taskJsonObject = taskJsonArray.getJSONObject(i)
            val taskName = taskJsonObject.getString("taskName")
            val isCompleted = taskJsonObject.getBoolean("isCompleted")
            val task = Task(taskName, isCompleted)
            taskList.add(task)
        }
        taskAdapter.notifyDataSetChanged()
    }
}
