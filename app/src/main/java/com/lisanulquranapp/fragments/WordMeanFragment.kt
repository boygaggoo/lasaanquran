package com.lisanulquranapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lisanulquranapp.R
import com.lisanulquranapp.adapters.FlashCardMeaningAdapter
import com.lisanulquranapp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_word_mean.view.*

class WordMeanFragment : BaseFragment() {

    companion object {
        private var wmInstance: WordMeanFragment? = null
        fun getInstance(): WordMeanFragment {
            if (wmInstance == null) {
                wmInstance = WordMeanFragment()
            }
            return wmInstance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_word_mean, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
//        val cardsModels = ArrayList<CardsModel>()
//        cardsModels.add(CardsModel())
//        cardsModels.add(CardsModel())
//        cardsModels.add(CardsModel())

        view.swipe_deck.setAdapter(FlashCardMeaningAdapter(mSessionController.wordMeaningList/*cardsModels*/, activity))
    }
}