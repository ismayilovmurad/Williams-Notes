package com.martiandeveloper.williamsnotes.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.martiandeveloper.williamsnotes.R
import com.martiandeveloper.williamsnotes.databinding.RecyclerviewNoteItemBinding
import com.martiandeveloper.williamsnotes.model.Note
import com.martiandeveloper.williamsnotes.utils.DateConverter
import java.sql.Timestamp
import java.text.ParseException

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
            recyclerviewNoteItemBinding.date = getPrettyTime(
                Timestamp(DateConverter().dateToTimestamp(note.date)),
                recyclerviewNoteItemBinding
            )

            recyclerviewNoteItemBinding.recyclerviewNoteItemSelectFAB.setOnClickListener {

                with(recyclerviewNoteItemBinding.recyclerviewNoteItemSelectFAB) {

                    if (selectedNotes.contains(note)) {
                        selectedNotes.remove(note)
                        setImageResource(R.drawable.ic_note)
                    } else {
                        selectedNotes.add(note)
                        setImageResource(R.drawable.ic_check)
                    }

                }

                itemListener.onItemSelectionChange()

            }

            recyclerviewNoteItemBinding.recyclerviewNoteItemSelectFAB.setImageResource(
                if (selectedNotes.contains(
                        note
                    )
                ) R.drawable.ic_check else R.drawable.ic_note
            )

            recyclerviewNoteItemBinding.executePendingBindings()

            itemView.setOnClickListener {
                itemListener.onItemClick(note.id)
            }

        }

    }

    private fun getPrettyTime(time: Timestamp, binding: RecyclerviewNoteItemBinding): String {

        return try {
            val currentTime = System.currentTimeMillis()
            val prettyTime = DateUtils.getRelativeTimeSpanString(
                time.time,
                currentTime,
                DateUtils.MINUTE_IN_MILLIS
            )
            prettyTime.toString()
        } catch (e: ParseException) {
            binding.root.context.resources.getString(R.string.unknown)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            DataBindingUtil
                .inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.recyclerview_note_item,
                    parent,
                    false
                )
        )
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
