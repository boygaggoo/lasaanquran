package com.ctandem.lasaanulquran.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctandem.lasaanulquran.R
import com.ctandem.lasaanulquran.activities.ChaptersContentActivity
import com.ctandem.lasaanulquran.adapters.ChaptersAdapter
import com.ctandem.lasaanulquran.base.BaseFragment
import com.ctandem.lasaanulquran.models.ChaptersModel
import com.ctandem.lasaanulquran.models.ErrorModel
import com.ctandem.lasaanulquran.utils.StaticMethod
import com.ctandem.lasaanulquran.utils.Utils
import kotlinx.android.synthetic.main.fragment_chapters.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class ChaptersFragment : BaseFragment() {

    private lateinit var fragView: View
    private lateinit var list: List<ChaptersModel>

    companion object {
        private var bookId: Long = 0
        private var chaptersInstance: ChaptersFragment? = null
        fun getInstance(longExtra: Long): ChaptersFragment {
            if (chaptersInstance == null) {
                chaptersInstance = ChaptersFragment()
            }
            bookId = longExtra
            return chaptersInstance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragView = inflater.inflate(R.layout.fragment_chapters, container, false)
        list = ArrayList()
        init()
        retainInstance = true
        return fragView
    }

    private fun init() {
        setUpAdapter()
        fragView.chapter_refresh_layout.setOnRefreshListener {
            setUpAdapter()
        }
    }

    fun setUpAdapter() {
        fragView.chapter_recycler_view.layoutManager = GridLayoutManager(activity, 3,
                GridLayoutManager.VERTICAL, false)
        fragView.chapter_recycler_view.itemAnimator = DefaultItemAnimator()

        fragView.no_internet_tv.visibility = View.GONE
        fragView.chapter_recycler_view.visibility = View.VISIBLE

        fragView.chapter_refresh_layout.isRefreshing = true
        mNetworkController.getChapters(bookId)
    }

    fun getList(): List<ChaptersModel> {
        return list
    }

    fun setList(list: List<ChaptersModel>) {
        listData(list)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onError(model: ErrorModel) {
        fragView.chapter_refresh_layout.isRefreshing = false
        StaticMethod.showMessageOk(activity, "", model.message, true)
        fragView.no_internet_tv.visibility = View.VISIBLE
        fragView.chapter_recycler_view.visibility = View.GONE
    }

    @Subscribe
    fun getData(list: List<ChaptersModel>) {

        if (list.isNotEmpty()) {
            listData(list)
        } else {
            fragView.no_internet_tv.visibility = View.VISIBLE
            fragView.chapter_recycler_view.visibility = View.GONE
        }
        fragView.chapter_refresh_layout.isRefreshing = false
    }

    private fun listData(list: List<ChaptersModel>) {
        this.list = list
        val adapter = ChaptersAdapter(activity, object : ChaptersAdapter.onItemClick {
            override fun onClick(position: Int) {
                val chapterModel = list[position]
                val intent = Intent(activity, ChaptersContentActivity::class.java)
                intent.putExtra("Name", chapterModel.chapterName)
                intent.putExtra("id", chapterModel.chapterId)
                intent.putExtra("index", chapterModel.chapterIndex)
                startActivity(intent)
            }
        })
        fragView.chapter_recycler_view.adapter = adapter
        adapter.setData(list)
    }

}