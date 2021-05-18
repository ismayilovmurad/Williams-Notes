package com.martiandeveloper.williamsnotes.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
import java.util.*

class EditFragment : Fragment() {

    private lateinit var viewModel: EditViewModel

    private lateinit var binding: FragmentEditBinding

    private val args: EditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this).get(EditViewModel::class.java)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)

        observe(savedInstanceState)

        setActionBar()

        setHasOptionsMenu(true)

        with(requireActivity()) {
            title =
                getString(if (args.noteId == NEW_NOTE_ID) R.string.new_note else R.string.edit_note)

            onBackPressedDispatcher.addCallback(viewLifecycleOwner,
                object : OnBackPressedCallback(true) {

                    override fun handleOnBackPressed() {
                        saveAndReturn()
                    }

                })
        }

        viewModel.getNoteById(args.noteId)

        return binding.root

    }

    private fun observe(savedInstanceState: Bundle?) {

        viewModel.currentNote.observe(viewLifecycleOwner, {

            with(binding.fragmentEditEditET) {
                val savedString = savedInstanceState?.getString(NOTE_TEXT_KEY)
                val cursorPosition = savedInstanceState?.getInt(CURSOR_POSITION_KEY) ?: 0
                setText(savedString ?: it.text)
                setSelection(cursorPosition)
            }

        })

    }

    private fun setActionBar() {

        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_check)
        }

    }

    private fun saveAndReturn(): Boolean {

        val imm = requireActivity()
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

        with(viewModel) {

            if (currentNote.value?.text != binding.fragmentEditEditET.text.toString()) {
                currentNote.value?.text = binding.fragmentEditEditET.text.toString()
                currentNote.value?.date = Date()
                updateNote()

                Toast.makeText(
                    binding.root.context,
                    getString(R.string.note_saved),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        findNavController().navigateUp()

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {

        with(binding.fragmentEditEditET) {
            outState.putString(NOTE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }

        super.onSaveInstanceState(outState)

    }

}
