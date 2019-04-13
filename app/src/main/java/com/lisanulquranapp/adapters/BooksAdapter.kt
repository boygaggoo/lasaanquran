package com.lisanulquranapp.adapters

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lisanulquranapp.R
import com.lisanulquranapp.models.BooksModel
import kotlinx.android.synthetic.main.books_item_row.view.*

class BooksAdapter(var context: Context, var onClick: OnClick) :
        RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    lateinit var list: List<BooksModel>
    fun setData(list: List<BooksModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, onClick)
        var typeface: Typeface
        if(list[position].font == 0)
            typeface = Typeface.createFromAsset(context.getAssets(), "font/jameel_noori.ttf")
        else
            typeface = Typeface.createFromAsset(context.getAssets(), "font/muhammadi_quranic.ttf")
        holder.bookName.typeface = typeface

        holder.bookName.text = list[position].bookName
        Glide.with(context).load(list[position].icon).asBitmap()
                .error(R.drawable.ic_chapter_bg)
                .placeholder(R.drawable.ic_chapter_bg)
                .into(holder.bookIcon)
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent!!.context).
                inflate(R.layout.books_item_row, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookName = itemView.book_tv!!
        val bookIcon = itemView.book_icon!!
        fun bind(position: Int, onClick: OnClick) {
            itemView.setOnClickListener {
                onClick.onClick(position)
            }
        }
    }

    interface OnClick {
        fun onClick(position: Int)
    }

}