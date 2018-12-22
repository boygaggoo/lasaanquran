package com.lisanulquranapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import com.lisanulquranapp.R
import com.lisanulquranapp.adapters.WrongAnswerAdapter
import com.lisanulquranapp.models.CardsModel
import kotlinx.android.synthetic.main.activity_wrong_answer.*

class WrongAnswerActivity : AppCompatActivity() {

    var list: List<CardsModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wrong_answer)
//        this.setFinishOnTouchOutside(false);
        list = intent.getParcelableArrayListExtra<CardsModel>("list")

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.wrong_answer)

        close_toolbar_iv.setOnClickListener {
            this.finish()
        }

        setUpAdapter()
    }

    private fun setUpAdapter() {

        recycler_view.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recycler_view.itemAnimator = DefaultItemAnimator()

        val adapter = WrongAnswerAdapter(this, list)

        recycler_view.adapter = adapter

    }
}
