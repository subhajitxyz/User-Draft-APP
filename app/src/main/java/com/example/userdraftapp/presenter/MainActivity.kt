package com.example.userdraftapp.presenter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userdraftapp.R
import com.example.userdraftapp.adapter.DraftRecyclerViewAdapter
import com.example.userdraftapp.databinding.ActivityMainBinding
import com.example.userdraftapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


////
// user is selected by spinner ->


// room database
// userid ->  lis<drafts>


const val DRAFT_ID_INTENT_KEY = "draft_id_intent_key"
const val CURRENT_USERID_INTENT_KEY = "current_user_id_intent"
const val ADD_DRAFT_INTENT_KEY = "add_draft_intent_key"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewmodel: MainViewModel by viewModels()

    private val adapter: DraftRecyclerViewAdapter = DraftRecyclerViewAdapter() { draftId, currentUserId ->
        startActivity(Intent(this, DraftViewActivity::class.java).apply {
            putExtra(DRAFT_ID_INTENT_KEY, draftId)
            putExtra(CURRENT_USERID_INTENT_KEY, currentUserId)
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.userDraftRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.userDraftRecyclerView.adapter = adapter



        binding.addDraftIcon.setOnClickListener {
            startActivity(Intent(this, DraftViewActivity::class.java).apply {
                putExtra("add_draft_intent_key", true)
                putExtra(CURRENT_USERID_INTENT_KEY, viewmodel.currentUserId.value)
            })
        }



        val usernameList = viewmodel.userList.map { it ->
            it.name
        }



        val spinneradapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, usernameList)
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.usersSpinner.adapter = spinneradapter

        binding.usersSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                // call load draft for "user_{position+1}
                viewmodel.loginUser("user_${position+1}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Optional: code to handle cases where nothing is selected
            }
        }



//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewmodel.userDraftList.collect { it ->
//                    // update adapter list
//                    adapter.submitList(it)
//
//                }
//            }
//        }

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewmodel.userDrafts.collect { it ->
//                    // update adapter list
//                    adapter.submitList(it)
//
//                }
//            }
//        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.drafts.collect { it ->
                    // update adapter list
                    adapter.submitList(it)

                }
            }
        }

    }
}