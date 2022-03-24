package com.tsci.notesapp.ui.adapter

import android.util.Log
import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsci.notesapp.R
import com.tsci.notesapp.data.Note
import com.tsci.notesapp.databinding.NoteItemBinding
import java.util.*


class NoteAdapter(private val onItemClicked: (Note) -> Unit) : ListAdapter<Note,
        NoteAdapter.NoteViewHolder>(DiffCallback) {

    lateinit var selectedNote: Note


    class NoteViewHolder(
        private var binding: NoteItemBinding

    ) : RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        init {
            binding.root.setOnCreateContextMenuListener(this)
        }

        fun bind(note: Note) {
            Log.d("NoteAdapter", Date(note.noteDate.time).toString())
            binding.note = note
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {

            menu?.add(
                Menu.NONE, R.id.menu_delete,
                Menu.NONE, R.string.delete_note);
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

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder,  position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                selectedNote = current
                return false
            }
        })
    }

    override fun onViewRecycled(holder: NoteViewHolder) {
        holder.itemView.setOnLongClickListener(null)
        super.onViewRecycled(holder)
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