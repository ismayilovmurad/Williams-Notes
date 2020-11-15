package com.martiandeveloper.williamsnotes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.martiandeveloper.williamsnotes.R
import com.martiandeveloper.williamsnotes.databinding.FragmentEditBinding
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

        fragmentEditBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editViewModel = getViewModel()

        Toast.makeText(context, "${args.noteId}", Toast.LENGTH_SHORT).show()
    }

    private fun getViewModel(): EditViewModel {

        return ViewModelProvider(this, object : ViewModelProvider.Factory {

            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return EditViewModel() as T
            }

        })[EditViewModel::class.java]

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun saveAndReturn(): Boolean {
        findNavController().navigateUp()
        return true
    }

}
