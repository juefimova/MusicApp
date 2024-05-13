package com.example.musicapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ItemNoteBinding
import com.example.musicapp.model.RapType
import com.example.musicapp.model.RockType
import com.example.musicapp.model.RockViewHolder


class MusicRapListAdapter(
    private var items: MutableList<RapType>,
    private val itemClick: (RapType) -> Unit
) : RecyclerView.Adapter<RapViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RapViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RapViewHolder(
            binding = ItemNoteBinding.inflate(layoutInflater, parent, false),
            itemClick
        )

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RapViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    fun filterList(filteredList: ArrayList<RapType>) {
        items = filteredList
        notifyDataSetChanged()
    }


}

class RapViewHolder(
    private val binding: ItemNoteBinding,
    private val itemClick: (RapType) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RapType, position: Int) {
        binding.run {
            song.text = item.name
            pic.setImageResource(item.image)
            root.setOnClickListener {
                itemClick(item)
            }


        }



    }
}
