package com.zerir.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PreviewAdapter(private val list: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ViewHolder -> {
                holder.bind(list[position])
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val context = parent.context
                val layoutInflater = LayoutInflater.from(context)
                val view = layoutInflater.inflate(R.layout.row_preview_item, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: String) {
            view.findViewById<TextView>(R.id.item_text).text = item
        }

    }

}