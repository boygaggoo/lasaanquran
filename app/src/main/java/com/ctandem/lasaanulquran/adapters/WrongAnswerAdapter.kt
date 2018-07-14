package com.ctandem.lasaanulquran.adapters

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctandem.lasaanulquran.R
import com.ctandem.lasaanulquran.models.CardsModel
import kotlinx.android.synthetic.main.item_wrong_answer.view.*

/**
 * Created by Nauman on 5/13/2017.
 */
class WrongAnswerAdapter(var context: Context, private var list: List<CardsModel>) :
        RecyclerView.Adapter<WrongAnswerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var typefaceQuestion: Typeface
        if(list[position].fontQuestion== 0)
            typefaceQuestion = Typeface.createFromAsset(context.getAssets(), "font/jameel_noori.ttf")
        else
            typefaceQuestion = Typeface.createFromAsset(context.getAssets(), "font/muhammadi_quranic.ttf")
        holder.heading.typeface = typefaceQuestion

        var typefaceAnswer: Typeface
        if(list[position].fontAnswer== 0)
            typefaceAnswer = Typeface.createFromAsset(context.getAssets(), "font/jameel_noori.ttf")
        else
            typefaceAnswer = Typeface.createFromAsset(context.getAssets(), "font/muhammadi_quranic.ttf")
        holder.detail.typeface = typefaceAnswer



//        val custom_font = Typeface.createFromAsset(context.assets, "font/jameel_noori.ttf")
        holder.heading.typeface = typefaceQuestion
        holder.heading.text = list[position].words

//        val customMuhammadi = Typeface.createFromAsset(context.assets, "font/muhammadi_quranic.ttf")
        holder.detail.typeface = typefaceAnswer
        holder.detail.text = list[position].meanings
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent!!.context).inflate(R.layout.item_wrong_answer, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val heading = itemView.heading_tv!!
        val detail = itemView.detail_tv!!
    }
}