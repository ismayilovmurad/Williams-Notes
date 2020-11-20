package com.martiandeveloper.williamsnotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.martiandeveloper.williamsnotes.R
import com.martiandeveloper.williamsnotes.databinding.RecyclerviewNoteItemBinding
import com.martiandeveloper.williamsnotes.model.Note

class NoteAdapter(
    private val noteList: List<Note>,
    private val itemListener: ItemListener
) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    val selectedNotes = arrayListOf<Note>()

    inner class NoteViewHolder(private val recyclerviewNoteItemBinding: RecyclerviewNoteItemBinding) :
        RecyclerView.ViewHolder(recyclerviewNoteItemBinding.root) {

        fun bind(note: Note, itemListener: ItemListener) {

            recyclerviewNoteItemBinding.note = note.text
            recyclerviewNoteItemBinding.executePendingBindings()

            recyclerviewNoteItemBinding.recyclerviewNoteItemAddFAB.setOnClickListener {

                if (selectedNotes.contains(note)) {
                    selectedNotes.remove(note)
                    recyclerviewNoteItemBinding.recyclerviewNoteItemAddFAB.setImageResource(R.drawable.ic_note)
                } else {
                    selectedNotes.add(note)
                    recyclerviewNoteItemBinding.recyclerviewNoteItemAddFAB.setImageResource(R.drawable.ic_check)
                }

                itemListener.onItemSelectionChange()

            }

            recyclerviewNoteItemBinding.recyclerviewNoteItemAddFAB.setImageResource(
                if (selectedNotes.contains(note)) {
                    R.drawable.ic_check
                } else {
                    R.drawable.ic_note
                }
            )

            itemView.setOnClickListener {
                itemListener.onItemClick(note.id)
            }

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
        holder.bind(noteList[position], itemListener)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    interface ItemListener {
        fun onItemClick(noteId: Int)
        fun onItemSelectionChange()
    }

}
