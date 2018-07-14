package com.ctandem.lasaanulquran.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctandem.lasaanulquran.R
import com.ctandem.lasaanulquran.activities.ChapterDetailActivity
import com.ctandem.lasaanulquran.adapters.BookMarkAdapter
import com.ctandem.lasaanulquran.adapters.BooksAdapter
import com.ctandem.lasaanulquran.base.BaseFragment
import com.ctandem.lasaanulquran.db.AppDatabase
import kotlinx.android.synthetic.main.fragment_book_mark.view.*

class BookMarkFragment : BaseFragment() {

    companion object {
        private var bookMark: BookMarkFragment? = null
        fun getInstance(): BookMarkFragment {
            if (bookMark == null) {
                bookMark = BookMarkFragment()
            }
            return bookMark as BookMarkFragment
        }
    }

    lateinit var fragView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragView = inflater.inflate(R.layout.fragment_book_mark, container, false)
        init()
        return fragView
    }

    private fun init() {
        fragView.bookmark_rv.layoutManager = GridLayoutManager(activity, 3)
        fragView.bookmark_rv.itemAnimator = DefaultItemAnimator()

        fragView.swipe_refresh.setOnRefreshListener {
            fragView.swipe_refresh.isRefreshing = true
            setUpAdapter()
        }
        setUpAdapter()
    }

    private fun setUpAdapter() {

        val dbModelList = AppDatabase.getInstance(activity).appDao().allContents
        if (dbModelList != null) {
            val adapter = BookMarkAdapter(activity, object : BookMarkAdapter.onItemClick {
                override fun onClick(position: Int) {
                    mSessionController.contentModel = dbModelList[position]
                    val intent = Intent(activity, ChapterDetailActivity::class.java)
                    startActivity(intent)
                }
            })
            fragView.bookmark_rv.adapter = adapter
            adapter.setData(dbModelList)
        }
        fragView.swipe_refresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        setUpAdapter()
    }

}
