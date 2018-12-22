package com.lisanulquranapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import com.lisanulquranapp.R
import com.lisanulquranapp.R.id.tabs
import com.lisanulquranapp.R.id.toolbar
import com.lisanulquranapp.adapters.SupportPagerAdapter
import com.lisanulquranapp.base.BaseActivity
import com.lisanulquranapp.fragments.FlashCardFragment
import com.lisanulquranapp.fragments.QuestionAnswerFragment
import com.lisanulquranapp.fragments.WordMeanFragment
import kotlinx.android.synthetic.main.activity_meanings.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class MeaningsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowDirection()
        setContentView(R.layout.activity_meanings)
        init()
    }

    private fun init() {
        setSupportActionBar(toolbar)
//        supportActionBar?.title = mSessionController.contentModel.contentTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val fragments = ArrayList<Fragment>()
        val titles = ArrayList<String>()
        if(mSessionController.qaList!=null && mSessionController.qaList.size>0 ){
            fragments.add(QuestionAnswerFragment.getInstance())
            titles.add(getString(R.string.question_answer))
        }

        if(mSessionController.wordMeaningList !=null && mSessionController.wordMeaningList.size>0){
            fragments.add(WordMeanFragment.getInstance())
            titles.add(getString(R.string.word_mean))
        }

        if(mSessionController.fashCardList !=null && mSessionController.fashCardList.size>0){
            fragments.add(FlashCardFragment.getInstance())
            titles.add(getString(R.string.flash_card))
        }

        val adapter = SupportPagerAdapter(supportFragmentManager, fragments, titles)
        container.adapter = adapter
        tabs.setupWithViewPager(container)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.chapter_detail_menu, menu)
        menu.getItem(0).setVisible(false)
        menu.getItem(1).setVisible(false)
        menu.getItem(2).setVisible(true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> {

                val intent = Intent(this@MeaningsActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
