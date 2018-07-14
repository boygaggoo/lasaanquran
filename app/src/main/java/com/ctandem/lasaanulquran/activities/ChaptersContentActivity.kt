package com.ctandem.lasaanulquran.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.ctandem.lasaanulquran.R
import com.ctandem.lasaanulquran.R.id.swipe_refresh
import com.ctandem.lasaanulquran.adapters.ChaptersContentAdapter
import com.ctandem.lasaanulquran.base.BaseActivity
import com.ctandem.lasaanulquran.models.ContentModel
import com.ctandem.lasaanulquran.models.ErrorModel
import com.ctandem.lasaanulquran.utils.StaticMethod
import kotlinx.android.synthetic.main.activity_chapters_content.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.json.JSONArray

class ChaptersContentActivity : BaseActivity() {

    var chapterId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowDirection()
        setContentView(R.layout.activity_chapters_content)
        init()
    }

    private fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = intent.getStringExtra("Name")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        chapter_title_tv.text = intent.getStringExtra("Name")
        chapter_number_tv.text = intent.getLongExtra("index", 0).toString()
        chapters_rv.layoutManager = LinearLayoutManager(this)
        chapters_rv.itemAnimator = DefaultItemAnimator()
        chapterId = intent.getLongExtra("id", 0)

        setupAdapter()

        swipe_refresh.setOnRefreshListener {
            setupAdapter()
        }
    }

    private fun setupAdapter() {
        swipe_refresh.isRefreshing = true
        mNetworkController.getChaptersTopic(chapterId)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    @Subscribe
    fun onError(model: ErrorModel) {
        swipe_refresh.isRefreshing = false
        StaticMethod.showMessageOk(this, "", model.message, true)
    }

    @Subscribe
    fun getData(list: List<ContentModel>) {
        chapters_rv.adapter = ChaptersContentAdapter(this, list, object : ChaptersContentAdapter.OnClick {
            override fun onClick(position: Int) {
                mSessionController.contentModel = list[position]
                when {
                    list[position].contentType == 1.toLong() -> {
                        val mediaArray = JSONArray(list[position].contentMedia)
                        var isPdf = false;
                        for (i in 0 until mediaArray.length()) {
                            val obj = mediaArray.getJSONObject(i)
                            if(obj.getString("media_type").equals("pdf")) {
                                isPdf = true
                                val intent = Intent(this@ChaptersContentActivity, WebActivity::class.java)
                                val path = obj.getString("path")
                                intent.putExtra("media", list[position].contentMedia)
                                intent.putExtra("WebTitle", list[position].contentTitle)
                                intent.putExtra("WebLink", "https://docs.google.com/gview?embedded=true&url=" + path)
                                startActivity(intent)
                                break
                            }
                        }
                        if(!isPdf){
                            val intent = Intent(this@ChaptersContentActivity, ChapterDetailActivity::class.java)
                            startActivity(intent)
                        }

                    }
                    list[position].contentType == 2.toLong() -> {
                        swipe_refresh.isRefreshing = true
                        mNetworkController.getChaptersExercise(chapterId)
                    }
                    else -> Log.e("","")
                }
            }
        })
        swipe_refresh.isRefreshing = false
    }

    @Subscribe
    fun getExcercise(status: String) {
        swipe_refresh.isRefreshing = false
        startActivity(Intent(this, MeaningsActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.chapter_detail_menu, menu)
        menu.getItem(0).isVisible = false
        menu.getItem(1).isVisible = false
        menu.getItem(2).isVisible = true
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> {
                val intent = Intent(this@ChaptersContentActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
