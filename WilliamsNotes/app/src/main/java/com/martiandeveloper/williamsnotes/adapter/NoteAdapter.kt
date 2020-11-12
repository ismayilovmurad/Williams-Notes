package com.martiandeveloper.williamsnotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.martiandeveloper.williamsnotes.R
import com.martiandeveloper.williamsnotes.databinding.RecyclerviewNoteItemBinding
import com.martiandeveloper.williamsnotes.model.Note

class NoteAdapter(private val noteList: ArrayList<Note>) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(private val recyclerviewNoteItemBinding: RecyclerviewNoteItemBinding) :
        RecyclerView.ViewHolder(recyclerviewNoteItemBinding.root) {

        fun bind(note: Note) {
            recyclerviewNoteItemBinding.note = note.text
            recyclerviewNoteItemBinding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        val recyclerviewNoteItemBinding: RecyclerviewNoteItemBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_note_item,
                parent,
                false
            )

        return NoteViewHolder(recyclerviewNoteItemBinding)

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

}
