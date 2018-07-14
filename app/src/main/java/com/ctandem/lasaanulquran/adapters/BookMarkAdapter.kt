package com.ctandem.lasaanulquran.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctandem.lasaanulquran.R
import com.ctandem.lasaanulquran.models.ContentModel
import kotlinx.android.synthetic.main.item_chapter_row.view.*

class BookMarkAdapter(var context: Context, private var onClick: onItemClick) :
        RecyclerView.Adapter<BookMarkAdapter.ViewHolder>() {

    private lateinit var list: List<ContentModel>
    fun setData(list: List<ContentModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, onClick)
        holder.chapterName.text = list[position].contentTitle
        holder.chapterIndex.text = list[position].contentIndex.toString()
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).
                inflate(R.layout.item_chapter_row, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chapterName = itemView.chapter_name_tv!!
        val chapterIndex = itemView.chapter_number_tv!!

        fun bind(position: Int, onItemClick: onItemClick) {
            itemView.setOnClickListener {
                onItemClick.onClick(position)
            }
        }
    }

    interface onItemClick {
        fun onClick(position: Int)
    }
}