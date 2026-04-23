package com.example.userdraftapp.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.userdraftapp.data.local.DraftEntity
import com.example.userdraftapp.databinding.DraftItemBinding

class DraftRecyclerViewAdapter(
    private val onclick: (Int, String) -> Unit
): RecyclerView.Adapter<DraftRecyclerViewAdapter.DraftViewHolder>() {

    val draftList = mutableListOf<DraftEntity>()

    fun submitList(list: List<DraftEntity>) {
        draftList.clear()
        draftList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DraftViewHolder {
        val binding = DraftItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return DraftViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DraftViewHolder,
        position: Int
    ) {
        val draft = draftList[position]
        holder.binding.draftTitle.text = draft.title
        holder.binding.draftDescription.text = draft.description
        holder.binding.root.setOnClickListener {
            onclick(draft.draftId, draft.userId)
        }
    }

    override fun getItemCount(): Int {
        return draftList.size
    }

    inner class DraftViewHolder(val binding: DraftItemBinding): RecyclerView.ViewHolder(binding.root)
}