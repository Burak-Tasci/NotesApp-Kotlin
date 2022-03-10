package com.tsci.notesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tsci.notesapp.NotesApplication
import com.tsci.notesapp.data.Note
import com.tsci.notesapp.databinding.EditNoteFragmentBinding
import com.tsci.notesapp.ui.viewmodel.AppViewModel
import com.tsci.notesapp.ui.viewmodel.AppViewModelFactory
import java.util.*


class EditNoteFragment : Fragment() {

    private val navigationArgs: EditNoteFragmentArgs by navArgs()

    private val viewModel: AppViewModel by activityViewModels {
        AppViewModelFactory(
            (activity?.application as NotesApplication).database.noteDao()
        )
    }

    lateinit var note: Note

    private var _binding: EditNoteFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = EditNoteFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.itemId
        when(id){
            -1L -> null
            else ->
                viewModel.retrieveNote(id).observe(this.viewLifecycleOwner) { selectedNote ->
                note = selectedNote
                bind(note)
            }
        }

        binding.saveFab.setOnClickListener {
            if (id == -1L){
                viewModel.addNewNote(
                    noteTitle = binding.titleEditText.text.toString(),
                    noteText =  binding.bodyEditText.text.toString(),
                    noteDate = Date(Date().time / 1000L)
                )
            }
            else{
                viewModel.updateNote(
                    Id = note.id,
                    noteTitle = binding.titleEditText.text.toString(),
                    noteText =  binding.bodyEditText.text.toString(),
                    noteDate = Date(Date().time / 1000L)
                )
            }
            val action = EditNoteFragmentDirections.actionEditNoteFragmentToNotesFragment()
            findNavController().navigate(action)
        }
    }
    private fun bind(note: Note): Unit {

        binding.apply {
            bodyEditText.text.apply {
                clear()
                insert(0, note.noteText)
            }
            titleEditText.text.apply {
                clear()
                insert(0, note.noteTitle)
            }
        }
    }
}