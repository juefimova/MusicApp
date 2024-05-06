package com.example.musicapp

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemNoteBinding
import com.example.musicapp.model.RoomMusic
import kotlinx.coroutines.NonDisposableHandle.parent

class MusicListAdapter(
    private var items: MutableList<RoomMusic>,
    private val itemClick: (RoomMusic) -> Unit
) : RecyclerView.Adapter<MusicViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MusicViewHolder(
            binding = ItemNoteBinding.inflate(layoutInflater, parent, false),
            itemClick
        )

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    fun filterList(filteredList: ArrayList<RoomMusic>) {
        items = filteredList
        notifyDataSetChanged()
    }


}

class MusicViewHolder(
    private val binding: ItemNoteBinding,
    private val itemClick: (RoomMusic) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RoomMusic, position: Int) {
        binding.run {
            song.text = item.name
            pic.setImageResource(item.image)
            root.setOnClickListener {
                itemClick(item)
            }


        }



    }
}
