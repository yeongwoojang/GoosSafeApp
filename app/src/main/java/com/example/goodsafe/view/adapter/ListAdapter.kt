package com.example.goodsafe.view.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goodsafe.R
import com.example.goodsafe.databinding.ItemRecyclerviewBinding
import com.example.goodsafe.model.vo.EmergencyRoom
import com.example.goodsafe.model.vo.EmergencyRoomInfo
import com.example.goodsafe.model.vo.NearHospital

class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding = ItemRecyclerviewBinding.bind(itemView)
}

class ListAdapter(context: Context) : RecyclerView.Adapter<ListViewHolder>() {

    private val mContext = context
    private var mItems: List<NearHospital> = ArrayList<NearHospital>()
    fun updateItems(items: List<NearHospital>) {
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.hospital = mItems[position]
        holder.binding.callBt.setOnClickListener {
            val tell = mItems[position].tell

            mContext.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${tell.replace("-","").trim()}")))
        }

    }
}

