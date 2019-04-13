package com.lisanulquranapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import com.lisanulquranapp.AppController
import com.lisanulquranapp.R
import com.lisanulquranapp.adapters.SupportPagerAdapter
import com.lisanulquranapp.base.BaseActivity
import com.lisanulquranapp.fragments.BookMarkFragment
import com.lisanulquranapp.fragments.ChaptersFragment
import com.lisanulquranapp.fragments.SettingsFragment
import com.lisanulquranapp.models.ChaptersModel
import com.michael.easydialog.EasyDialog
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.filter_layout.view.*
import org.greenrobot.eventbus.EventBus

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowDirection()
        setContentView(R.layout.activity_home)
        initUI()
    }

    private fun initUI() {

        setSupportActionBar(toolbar)
        supportActionBar?.title = intent.getStringExtra("name")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        filter_toolbar_iv.setOnClickListener {
            easyDialog()
        }

        share_toolbar_iv.setOnClickListener {
            shareApp()
        }

        feedback_toolbar_iv.setOnClickListener {
            sendFeedback()
        }

        val fragments = ArrayList<Fragment>()
        fragments.add(ChaptersFragment.getInstance(intent.getLongExtra("id", 0)))
        fragments.add(BookMarkFragment.getInstance())
        fragments.add(SettingsFragment.getInstance())
        val titles = ArrayList<String>()
        for (i in 0 until fragments.size) {
            titles.add("")
        }

        container.adapter = SupportPagerAdapter(supportFragmentManager, fragments, titles)

        container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {


            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        if (supportFragmentManager.backStackEntryCount == 1)
                            supportFragmentManager.popBackStackImmediate()
                        supportActionBar?.title = intent.getStringExtra("name")
                        filter_toolbar_iv.visibility = View.VISIBLE
                    }
                    1 -> {
                        supportActionBar?.title = getString(R.string.bookmarks)
                        filter_toolbar_iv.visibility = View.GONE
                    }
//                    2 -> {
//                        supportActionBar?.title = getString(R.string.settings)
//                        filter_toolbar_iv.visibility = View.GONE
//                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        val tabLayout: TabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(container)
        for (i in 0 until tabLayout.tabCount) {
            when (i) {
                0 -> tabLayout.getTabAt(i)!!.setIcon(R.drawable.ic_chapters_selected)
                1 -> tabLayout.getTabAt(i)!!.setIcon(R.drawable.ic_bookmark)
//                2 -> tabLayout.getTabAt(i)!!.setIcon(R.drawable.ic_settings_unselected)
            }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                when (position) {
                    0 -> {
                        if (supportFragmentManager.backStackEntryCount == 1)
                            supportFragmentManager.popBackStackImmediate()

                        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_chapters_selected)
                        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_bookmark)
//                        tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_settings_unselected)
                    }
                    1 -> {
                        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_chapters_unselected)
                        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_bookmark_selected)
//                        tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_settings_unselected)
                    }
//                    2 -> {
//                        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_chapters_unselected)
//                        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_bookmark)
//                        tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_settings_selected)
//                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    if (supportFragmentManager.backStackEntryCount == 1)
                        supportFragmentManager.popBackStackImmediate()
                }
            }
        })
    }

    private fun shareApp() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out Lisan Ul Quran app at: https://play.google.com/store/apps/details?id=com.lisanulquranapp")
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    private fun sendFeedback() {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "lisanulquran.in.urdu@gmail.com", null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "")
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    fun easyDialog() {

        val view = this.layoutInflater.inflate(R.layout.filter_layout, null)

        val easyDialog = EasyDialog(this)
                // .setLayoutResourceId(R.layout.layout_tip_content_horizontal)//layout resource id
                .setLayout(view)

                .setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                // .setLocation(new location[])//point in screen
                .setLocationByAttachedView(filter_toolbar_iv)
//                .setGravity(EasyDialog.GRAVITY_BOTTOM)
//                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
//                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
//                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
//                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(false)
                .setMatchParent(true)
                .setMarginLeftAndRight(40, 40)
//                .setOutsideColor(MainActivity.this.getResources().getColor(R.color.outside_color_trans))
//                .show()

        easyDialog.show()

        view.ok_tv.setOnClickListener {
            easyDialog.dismiss()
            val chapterInstance = ChaptersFragment.getInstance(intent.getLongExtra("id", 0))
            val list = chapterInstance.getList()
            val chaptersList = ArrayList<ChaptersModel>()
            val title = view.title_et.text.toString().trim()
            if (title.isEmpty()) {
                chapterInstance.setUpAdapter()
            } else if (list.isNotEmpty()) {
                (0 until list.size)
                        .map { list[it] }
                        .filterTo(chaptersList) { it.chapterName.contains(title, true) }
                chapterInstance.setList(chaptersList)
            }

        }
        view.cancel_tv.setOnClickListener { easyDialog.dismiss() }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (container.currentItem != 0) {
            container.currentItem = 0
            if (supportFragmentManager.backStackEntryCount == 1)
                supportFragmentManager.popBackStackImmediate()
        } else {
            EventBus.getDefault().unregister(this)
            AppController.mInstance.cancelPendingRequests("getChapters")
            finish()
        }
    }

}
