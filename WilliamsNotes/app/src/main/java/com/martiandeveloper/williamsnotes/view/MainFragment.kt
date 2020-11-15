package com.martiandeveloper.williamsnotes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.martiandeveloper.williamsnotes.R
import com.martiandeveloper.williamsnotes.adapter.NoteAdapter
import com.martiandeveloper.williamsnotes.databinding.FragmentMainBinding
import com.martiandeveloper.williamsnotes.model.Note
import com.martiandeveloper.williamsnotes.viewmodel.MainViewModel
import java.util.*

class MainFragment : Fragment(), NoteAdapter.ItemClickListener {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var fragmentMainBinding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        fragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return fragmentMainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        mainViewModel = getViewModel()

        val noteList = arrayListOf<Note>()
        noteList.add(Note(0, Date(), "sakdljaskldaadasdsdsdsdsss"))
        noteList.add(
            Note(
                1,
                Date(),
                "sakdljaskldaadasdsdsdsdssssakdljaskldaadasdsdsdsdssssakdljaskldaadasdsdsdsdsss"
            )
        )
        noteList.add(Note(2, Date(), "sakdljaskldaadasdsdsdsdssssakdljaskldaadasdsdsdsdsss"))

        val noteAdapter = NoteAdapter(noteList, this)

        with(fragmentMainBinding.fragmentMainMainRV) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
            addItemDecoration(divider)
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    private fun getViewModel(): MainViewModel {

        return ViewModelProvider(this, object : ViewModelProvider.Factory {

            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel() as T
            }

        })[MainViewModel::class.java]

    }

    override fun onItemClick(noteId: Int) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToEditFragment(noteId))
    }

}
