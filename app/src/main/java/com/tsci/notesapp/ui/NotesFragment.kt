package com.tsci.notesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.tsci.notesapp.AppViewModel
import com.tsci.notesapp.AppViewModelFactory
import com.tsci.notesapp.NotesApplication
import com.tsci.notesapp.databinding.FragmentNotesBinding
import com.tsci.notesapp.ui.adapter.NoteGridAdapter
import java.util.*


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

        val noteAdapter = NoteGridAdapter()

        binding.recyclerNotes.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = noteAdapter
        }

        viewModel.allNotes.observe(this.viewLifecycleOwner) { notes ->
            notes.let {
                noteAdapter.submitList(it)
            }
        }
    }


}