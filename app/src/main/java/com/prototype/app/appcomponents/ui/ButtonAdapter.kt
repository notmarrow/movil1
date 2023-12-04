package com.prototype.app.appcomponents.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.prototype.app.R

class ButtonAdapter(private val itemList: List<Item>) :
    RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_button, parent, false)
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) {
            val button = itemView as Button
            button.text = item.name
            val context = itemView.context

            button.setOnClickListener {
                val intent = Intent(context, InfoActivity::class.java)
                intent.putExtra("itemId", item.itemId)
                itemView.context.startActivity(intent)
            }
        }
    }
}
