package com.example.noteapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.ItemNoteBinding
import com.example.noteapp.interfaces.OnClickItem


class NoteAdapter(private val onLongClick: OnClickItem, private val onClick: OnClickItem) : ListAdapter<NoteModel, NoteAdapter.ViewHolder>(DiffCallback()) {
    class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteModel) = with(binding) {
            tvItemTitle.text = item.title
            tvItemDescription.text = item.description
            tvDate.text = item.date
            tvTime.text = item.time
            tvItemTitle.setTextColor(ContextCompat.getColor(binding.root.context, item.textColor))
            tvItemDescription.setTextColor(ContextCompat.getColor(binding.root.context, item.textColor))
            tvDate.setTextColor(ContextCompat.getColor(binding.root.context, item.textColor))
            tvTime.setTextColor(ContextCompat.getColor(binding.root.context, item.textColor))
            root.setBackgroundColor(ContextCompat.getColor(binding.root.context, item.selectedColor))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnLongClickListener{
            onLongClick.onLongClick(getItem(position))
            true
        }
        holder.itemView.setOnClickListener{
            onClick.onClick(getItem(position))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
}