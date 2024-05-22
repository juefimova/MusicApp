package com.example.musicapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.FragmentPageViewBinding

class ViewPagerAdapterFragment(
    private var list: kotlin.collections.List<Int>
) : RecyclerView.Adapter<PageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PageViewHolder(
            binding = FragmentPageViewBinding.inflate(
                layoutInflater, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class PageViewHolder(
    private val binding: FragmentPageViewBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Int) {
        binding.runCatching {
            image.setImageResource(item)
        }
    }
}