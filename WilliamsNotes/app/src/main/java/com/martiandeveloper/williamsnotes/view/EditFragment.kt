package com.martiandeveloper.williamsnotes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
        fragmentEditBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
        return fragmentEditBinding.root
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

}
