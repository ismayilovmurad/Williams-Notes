package com.martiandeveloper.williamsnotes.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.martiandeveloper.williamsnotes.R
import com.martiandeveloper.williamsnotes.adapter.NoteAdapter
import com.martiandeveloper.williamsnotes.databinding.FragmentMainBinding
import com.martiandeveloper.williamsnotes.model.Note
import com.martiandeveloper.williamsnotes.utils.NEW_NOTE_ID
import com.martiandeveloper.williamsnotes.utils.SELECTED_NOTES_KEY
import com.martiandeveloper.williamsnotes.viewmodel.MainViewModel

class MainFragment : Fragment(), NoteAdapter.ItemListener {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var fragmentMainBinding: FragmentMainBinding

    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        setHasOptionsMenu(true)

        requireActivity().title = getString(R.string.app_name)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        fragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        with(fragmentMainBinding.fragmentMainMainRV) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
            addItemDecoration(divider)
        }

        mainViewModel.noteList?.observe(viewLifecycleOwner, {
            noteAdapter = NoteAdapter(it, this@MainFragment)
            fragmentMainBinding.fragmentMainMainRV.adapter = noteAdapter
            fragmentMainBinding.fragmentMainMainRV.layoutManager = LinearLayoutManager(activity)

            val selectedNotes = savedInstanceState?.getParcelableArrayList<Note>(SELECTED_NOTES_KEY)
            noteAdapter.selectedNotes.addAll(selectedNotes ?: emptyList())
        })

        fragmentMainBinding.fragmentMainAddFAB.setOnClickListener {
            onItemClick(NEW_NOTE_ID)
        }

        return fragmentMainBinding.root
    }

    override fun onItemClick(noteId: Int) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToEditFragment(noteId))
    }

    override fun onItemSelectionChange() {
        requireActivity().invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        val menuId =
            if (this::noteAdapter.isInitialized && noteAdapter.selectedNotes.isNotEmpty()) {
                R.menu.menu_main_selected
            } else {
                R.menu.menu_main
            }

        inflater.inflate(menuId, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_delete -> deleteSelectedNotes()
            R.id.action_delete_all -> deleteAllNotes()
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun deleteAllNotes(): Boolean {
        mainViewModel.deleteAllNotes()
        return true
    }

    private fun deleteSelectedNotes(): Boolean {

        mainViewModel.deleteSelectedNotes(noteAdapter.selectedNotes)

        Handler(Looper.getMainLooper()).postDelayed({
            noteAdapter.selectedNotes.clear()
            requireActivity().invalidateOptionsMenu()
        }, 100)

        return true

    }

    override fun onSaveInstanceState(outState: Bundle) {

        if (this::noteAdapter.isInitialized) {
            outState.putParcelableArrayList(SELECTED_NOTES_KEY, noteAdapter.selectedNotes)
        }

        super.onSaveInstanceState(outState)

    }

}
