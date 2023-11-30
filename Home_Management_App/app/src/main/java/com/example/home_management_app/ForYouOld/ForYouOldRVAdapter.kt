package com.example.home_management_app.ForYouOld

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.home_management_app.databinding.FragmentForYouOldNewsRowBinding
import java.util.ArrayList

class ForYouOldRVAdapter(val newsList: ArrayList<ForYouOldNewsData>) : RecyclerView.Adapter<ForYouOldRVAdapter.ViewHolder>(){
    interface OnItemClickListener {
        fun OnItemClick(data: ForYouOldNewsData, pos:Int, binding: FragmentForYouOldNewsRowBinding);
    }

    var itemClickListener: OnItemClickListener?=null

    inner class ViewHolder(val binding: FragmentForYouOldNewsRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.title.setOnClickListener {
                itemClickListener?.OnItemClick(newsList[adapterPosition], adapterPosition, binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FragmentForYouOldNewsRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = newsList[position]

        holder.binding.title.text = currentItem.title
        holder.binding.news.text = currentItem.news
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}
