package com.lisanulquranapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lisanulquranapp.R
import com.lisanulquranapp.activities.WrongAnswerActivity
import com.lisanulquranapp.adapters.FlashCardAdapter
import com.lisanulquranapp.base.BaseFragment
import com.lisanulquranapp.models.CardsModel
import com.daprlabs.aaron.swipedeck.SwipeDeck
import kotlinx.android.synthetic.main.fragment_flash_card.*
import kotlinx.android.synthetic.main.fragment_flash_card.view.*
import java.util.*

class FlashCardFragment : BaseFragment() {

    var wrongCardList = ArrayList<CardsModel>()

    companion object {
        private var fcInstance: FlashCardFragment? = null
        fun getInstance(): FlashCardFragment {
//            if (fcInstance == null) {
                fcInstance = FlashCardFragment()
//            }
            return fcInstance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_flash_card, container, false)
        init(view)
        return view
    }

    public fun showHideStatsButton(stableId: Int){
        if(stableId == mSessionController.fashCardList.size - 1 && mSessionController.fashCardList.size > 0)
            stats_tv.visibility = View.VISIBLE
        else
            stats_tv.visibility = View.GONE
    }
    private fun init(view: View) {

        val swipeDeckCallback = object : SwipeDeck.SwipeDeckCallback {
            override fun cardSwipedLeft(stableId: Long) {
                Log.e("stableIdleft", stableId.toString())
                wrongCardList.add(mSessionController.fashCardList.get(stableId.toInt()))
                showHideStatsButton(stableId.toInt())
            }

            override fun cardSwipedRight(stableId: Long) {
                Log.e("stableIdright", stableId.toString())
                showHideStatsButton(stableId.toInt())
            }
        }

        view.swipe_deck.setCallback(swipeDeckCallback)

        view.right_btn.setOnClickListener{
//            Toast.makeText(activity, swipe_deck.adapterIndex.toString() , Toast.LENGTH_SHORT).show()
            view.swipe_deck.swipeTopCardRight(400)
        }

        view.wrong_tv.setOnClickListener{
//            Toast.makeText(activity, swipe_deck.adapterIndex.toString() , Toast.LENGTH_SHORT).show()
            view.swipe_deck.swipeTopCardLeft(400)
        }


        Log.e("stats", view.swipe_deck.adapterIndex.toString())

        view.stats_tv.setOnClickListener {
            Log.e("stats_tv", "clikced")
            val intent = Intent(activity, WrongAnswerActivity::class.java)
            intent.putExtra("list", wrongCardList)
            startActivity(intent)
        }
//        val cardsModels = ArrayList<CardsModel>()
//        cardsModels.add(CardsModel())
//        cardsModels.add(CardsModel())
//        cardsModels.add(CardsModel())

        view.swipe_deck.setAdapter(FlashCardAdapter(mSessionController.fashCardList, activity))
    }
}
