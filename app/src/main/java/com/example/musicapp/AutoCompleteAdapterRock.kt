package com.example.musicapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.musicapp.databinding.ItemSearchNoteBinding
import com.example.musicapp.model.RockType

class AutoCompleteAdapterRock (
    context: Context,
    itemsList: MutableList<RockType>
    ) : ArrayAdapter<RockType>(context, 0, itemsList) {

    private var itemsListFull: MutableList<RockType> = itemsList
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val binding: ItemSearchNoteBinding
        val view: View

        binding = ItemSearchNoteBinding.inflate(inflater, parent, false)
        view = binding.root

        val item = getItem(position)

        if(item != null) {
            binding.title.text = item.name
            binding.image.setImageResource(item.image)

        }
        return view
    }

    fun filterList(filteredList: MutableList<RockType>) {
        itemsListFull = filteredList
        notifyDataSetChanged()
        Log.d("filterList on adapter", filteredList.toString())
    }

    override fun getCount(): Int {
        return itemsListFull.size
    }

    override fun getItem(position: Int): RockType? {
        return if (position >= 0 && position < itemsListFull.size) {
            itemsListFull[position]
        } else{
            null
        }
    }


    }