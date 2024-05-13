package com.example.musicapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemNoteBinding
import com.example.musicapp.model.PopType


class MusicPopListAdapter(
    private var items: MutableList<PopType>,
    private val itemClick: (PopType) -> Unit
) : RecyclerView.Adapter<PopViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PopViewHolder(
            binding = ItemNoteBinding.inflate(layoutInflater, parent, false),
            itemClick
        )

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PopViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    fun filterList(filteredList: ArrayList<PopType>) {
        items = filteredList
        notifyDataSetChanged()
    }


}

class PopViewHolder(
    private val binding: ItemNoteBinding,
    private val itemClick: (PopType) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: PopType, position: Int) {
        binding.run {
            song.text = item.name
            pic.setImageResource(item.image)
            root.setOnClickListener {
                itemClick(item)
            }


        }



    }
}
