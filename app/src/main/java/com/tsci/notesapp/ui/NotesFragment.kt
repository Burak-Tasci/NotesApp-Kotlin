package com.tsci.notesapp.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsci.notesapp.AppViewModel
import com.tsci.notesapp.AppViewModelFactory
import com.tsci.notesapp.NotesApplication
import com.tsci.notesapp.databinding.FragmentNotesBinding
import com.tsci.notesapp.ui.adapter.NoteAdapter


class NotesFragment : Fragment() {

    private val viewModel: AppViewModel by activityViewModels {
        AppViewModelFactory(
            (activity?.application as NotesApplication).database.noteDao()
        )
    }

    private var _binding: FragmentNotesBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteAdapter = NoteAdapter()
        binding.recyclerNotes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
        }
        viewModel.allNotes.observe(this.viewLifecycleOwner) { notes ->
            notes.let {
                noteAdapter.submitList(it)
            }
        }


    }
}