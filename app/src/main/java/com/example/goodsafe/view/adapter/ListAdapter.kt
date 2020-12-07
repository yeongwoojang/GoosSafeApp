package com.example.goodsafe.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goodsafe.R
import com.example.goodsafe.databinding.ItemRecyclerviewBinding
import com.example.goodsafe.model.vo.EmergencyRoom
import com.example.goodsafe.model.vo.EmergencyRoomInfo

class ListViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){
    val binding = ItemRecyclerviewBinding.bind(itemView)
}

class ListAdapter : RecyclerView.Adapter<ListViewHolder>(){

    private var mItems :List<EmergencyRoom> = ArrayList<EmergencyRoom>()
    fun updateItems(items : List<EmergencyRoom>){
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview,parent,false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.hospital = mItems[position]
    }
}