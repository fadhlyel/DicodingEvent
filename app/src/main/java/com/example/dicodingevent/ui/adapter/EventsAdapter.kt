package com.example.dicodingevent.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.databinding.ItemEventBinding
import com.example.dicodingevent.ui.DetailActivity

class EventsAdapter(private var listEventsUpComing: List<ListEventsItem>): RecyclerView.Adapter<EventsAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemEventBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val event = listEventsUpComing[position]
        holder.binding.tvTitleItemEvent.text = event.name
        Glide.with(holder.itemView.context)
            .load(event.imageLogo)
            .into(holder.binding.imgItemPhoto)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EVENT_DETAIL, event.id)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listEventsUpComing.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<ListEventsItem>) {
        listEventsUpComing = newList
        notifyDataSetChanged()
    }
}