package com.martiandeveloper.williamsnotes.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.martiandeveloper.williamsnotes.R
import com.martiandeveloper.williamsnotes.databinding.FragmentEditBinding
import com.martiandeveloper.williamsnotes.utils.CURSOR_POSITION_KEY
import com.martiandeveloper.williamsnotes.utils.NEW_NOTE_ID
import com.martiandeveloper.williamsnotes.utils.NOTE_TEXT_KEY
import com.martiandeveloper.williamsnotes.viewmodel.EditViewModel

class EditFragment : Fragment() {

    private lateinit var editViewModel: EditViewModel

    private lateinit var fragmentEditBinding: FragmentEditBinding

    private val args: EditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setUpButton()

        setHasOptionsMenu(true)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {
                    saveAndReturn()
                }

            })

        requireActivity().title =
            if (args.noteId == NEW_NOTE_ID) {
                getString(R.string.new_note)
            } else {
                getString(R.string.edit_note)
            }

        editViewModel = ViewModelProvider(this).get(EditViewModel::class.java)

        fragmentEditBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)

        editViewModel.currentNote.observe(viewLifecycleOwner, {
            val savedString = savedInstanceState?.getString(NOTE_TEXT_KEY)
            val cursorPosition = savedInstanceState?.getInt(CURSOR_POSITION_KEY) ?: 0
            fragmentEditBinding.fragmentEditEditET.setText(savedString ?: it.text)
            fragmentEditBinding.fragmentEditEditET.setSelection(cursorPosition)
        })

        editViewModel.getNoteById(args.noteId)

        return fragmentEditBinding.root

    }

    private fun setUpButton() {

        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_check)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun saveAndReturn(): Boolean {
        val imm = requireActivity()
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(fragmentEditBinding.root.windowToken, 0)

        editViewModel.currentNote.value?.text =
            fragmentEditBinding.fragmentEditEditET.text.toString()
        editViewModel.updateNote()

        findNavController().navigateUp()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {

        with(fragmentEditBinding.fragmentEditEditET) {
            outState.putString(NOTE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }

        super.onSaveInstanceState(outState)

    }

}
