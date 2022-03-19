package com.tsci.notesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsci.notesapp.NotesApplication
import com.tsci.notesapp.databinding.FragmentNotesBinding
import com.tsci.notesapp.ui.adapter.NoteAdapter
import com.tsci.notesapp.ui.viewmodel.AppViewModel
import com.tsci.notesapp.ui.viewmodel.AppViewModelFactory


class NotesFragment : Fragment(), SearchView.OnQueryTextListener {

    private val viewModel: AppViewModel by activityViewModels {
        AppViewModelFactory(
            (activity?.application as NotesApplication).database.noteDao()
        )
    }

    private var _binding: FragmentNotesBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // Keeps track of which LayoutManager is in use for the [RecyclerView]

    val noteAdapter: NoteAdapter by lazy {
        NoteAdapter{
            val action = NotesFragmentDirections.actionNotesFragmentToEditNoteFragment(it.id)
            this.findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerNotes.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
        }
        viewModel.allNotes.observe(this.viewLifecycleOwner) { notes ->
            notes.let { it ->
                noteAdapter.submitList(it.sortedByDescending { it.noteDate })
            }
        }
        binding.addNoteFab.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToEditNoteFragment(-1L)
            findNavController().navigate(action)
        }

        binding.search.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchNotes(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchNotes(query)
        }
        return true
    }

    private fun searchNotes(query: String){
        val searchQuery = "%$query%"
        viewModel.searchNotes(searchQuery).observe(this, {
            list ->
                list.let {
                    noteAdapter.submitList(it.sortedByDescending { it.noteDate })
                }
        })

    }
}