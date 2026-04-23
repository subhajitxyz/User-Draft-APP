package com.example.userdraftapp.presenter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.userdraftapp.R
import com.example.userdraftapp.databinding.ActivityDraftViewBinding
import com.example.userdraftapp.viewmodels.DraftViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DraftViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDraftViewBinding
    private val viewModel: DraftViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDraftViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var draftId = intent.getIntExtra(DRAFT_ID_INTENT_KEY, -1)
        val currentUserId = intent.getStringExtra(CURRENT_USERID_INTENT_KEY)

        var isAddDraft = intent.getBooleanExtra(ADD_DRAFT_INTENT_KEY, false)

        if(isAddDraft) {
            // user wants to add draft
            binding.draftSaveButton.visibility = View.VISIBLE
            binding.draftTitleEdittext.visibility = View.VISIBLE
            binding.draftTitleEdittext.isEnabled = true
            binding.draftDescriptionEdittext.visibility = View.VISIBLE
            binding.draftDescriptionEdittext.isEnabled = true
        }
        binding.draftSaveButton.setOnClickListener {
            Log.d("testuser", "called saved not")
            Log.d("testuser", currentUserId.toString())
            currentUserId?.let { userId ->
                viewModel.addUserDraft(
                    draftId = draftId,
                    currentUserId = userId,
                    binding.draftTitleEdittext.text.toString(),
                    binding.draftDescriptionEdittext.text.toString()
                )

                Log.d("testuser", "called saved")
            }

            finish()
        }

        if(draftId != -1) {
            //actual draft for view purpose
            binding.draftTitle.visibility = View.VISIBLE
            binding.draftDescriptionView.visibility = View.VISIBLE
            viewModel.loadDrafts(draftId)

        } else {
            binding.draftDescriptionView.visibility = View.GONE
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentDraft.collect { state ->
                    updateUi(state)
                }
            }
        }
    }

    private fun updateUi(state: UiState) {

        when(state) {
            is UiState.Error -> {}
            is UiState.Loading -> {

            }
            is UiState.Success -> {
                binding.draftTitle.text = state.data.title
                binding.draftDescriptionView.text = state.data.description
            }
        }

    }
}