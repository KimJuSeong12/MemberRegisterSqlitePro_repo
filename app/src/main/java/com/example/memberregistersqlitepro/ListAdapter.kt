package com.example.memberregistersqlitepro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memberregistersqlitepro.databinding.ItemViewBinding

class ListAdapter(val dataList: MutableList<Member>) :
    RecyclerView.Adapter<ListAdapter.ListViewHoleder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHoleder {
        val itemViewBinding =
            ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHoleder(itemViewBinding)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ListViewHoleder, position: Int) {
        val itemViewBinding = holder.itemViewBinding
        itemViewBinding.tvName.text = dataList.get(position).name
        itemViewBinding.tvId.text = dataList.get(position).id
        itemViewBinding.tvEmail.text = dataList.get(position).email
    }

    inner class ListViewHoleder(val itemViewBinding: ItemViewBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)
}