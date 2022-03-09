package com.tsci.notesapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsci.notesapp.data.Note
import com.tsci.notesapp.databinding.NoteItemBinding
import java.util.*

class NoteAdapter : ListAdapter<Note,
        NoteAdapter.NoteViewHolder>(DiffCallback) {

    class NoteViewHolder(
        private var binding: NoteItemBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note){
            Log.d("NoteAdapter", Date(note.noteDate.time).toString())
            binding.noteDate.text =  note.noteDate.toString()
            binding.noteTitle.text = note.noteTitle
            binding.noteBody.text = note.noteText
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteAdapter.NoteViewHolder {
        return NoteViewHolder(
            NoteItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)

    }
    companion object DiffCallback : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }
    }

}