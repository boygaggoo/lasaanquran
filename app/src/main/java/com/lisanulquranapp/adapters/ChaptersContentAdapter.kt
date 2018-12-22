package com.lisanulquranapp.adapters

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lisanulquranapp.R
import com.lisanulquranapp.models.ContentModel
import kotlinx.android.synthetic.main.chapters_content_item_rows.view.*


class ChaptersContentAdapter(var context: Context, private var list: List<ContentModel>, var onClick: OnClick) :
        RecyclerView.Adapter<ChaptersContentAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, onClick)
        var typeface: Typeface
        if(list[position].font == 0)
            typeface = Typeface.createFromAsset(context.getAssets(), "font/jameel_noori.ttf")
        else
            typeface = Typeface.createFromAsset(context.getAssets(), "font/muhammadi_quranic.ttf")

        holder.itemTV.typeface = typeface
        holder.itemTV.text = list[position].contentTitle
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent!!.context).
                inflate(R.layout.chapters_content_item_rows, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTV = itemView.chapters_tv!!
        private val tempIV = itemView.temp_iv!!

        init {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                tempIV.drawable.isAutoMirrored = true
            }
        }

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