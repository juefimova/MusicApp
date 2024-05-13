package com.example.musicapp.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemNoteBinding

class MusicRockListAdapter(
    private var items: MutableList<RockType>,
    private val itemClick: (RockType) -> Unit
) : RecyclerView.Adapter<RockViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RockViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RockViewHolder(
            binding = ItemNoteBinding.inflate(layoutInflater, parent, false),
            itemClick
        )

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RockViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    fun filterList(filteredList: ArrayList<RockType>) {
        items = filteredList
        notifyDataSetChanged()
    }


}

class RockViewHolder(
    private val binding: ItemNoteBinding,
    private val itemClick: (RockType) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RockType, position: Int) {
        binding.run {
            song.text = item.name
            pic.setImageResource(item.image)
            root.setOnClickListener {
                itemClick(item)
            }


        }



    }
}
