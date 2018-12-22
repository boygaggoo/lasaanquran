package com.lisanulquranapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.lisanulquranapp.R
import com.lisanulquranapp.adapters.BooksAdapter
import com.lisanulquranapp.base.BaseActivity
import com.lisanulquranapp.models.BooksModel
import com.lisanulquranapp.models.ErrorModel
import com.lisanulquranapp.utils.StaticMethod
import com.michael.easydialog.EasyDialog
import kotlinx.android.synthetic.main.activity_books.*
import kotlinx.android.synthetic.main.exit_dialog_layout.view.*
import kotlinx.android.synthetic.main.filter_layout.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class BooksActivity : BaseActivity() {

    lateinit var list: List<BooksModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowDirection()
        setContentView(R.layout.activity_books)

        list = ArrayList()
        initUI()
    }

    private fun initUI() {

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.books)

        filter_toolbar_iv.setOnClickListener {
            easyDialog()
        }

        share_toolbar_iv.setOnClickListener {
            shareApp()
        }

        feedback_toolbar_iv.setOnClickListener {
            sendFeedback()
        }

        swipe_refresh.setOnRefreshListener {
            setUpAdapter()
        }
        setUpAdapter()
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

        val view = this.layoutInflater.inflate(R.layout.book_filter_layout, null)

        var easyDialog = EasyDialog(this)
                // .setLayoutResourceId(R.layout.layout_tip_content_horizontal)//layout resource id
                .setLayout(view)

                .setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                // .setLocation(new location[])//point in screen
                .setLocationByAttachedView(filter_toolbar_iv)
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
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
            val bookList = ArrayList<BooksModel>()
            val bookName = view.title_et.text.toString().trim()
            if (bookName.isEmpty()) {
                setUpAdapter()
            } else if (list.isNotEmpty()) {
                (0 until list.size)
                        .map { list[it] }
                        .filterTo(bookList) { it.bookName.contains(bookName, true) }
                listData(bookList)
            }
        }
        view.cancel_tv.setOnClickListener { easyDialog.dismiss() }
    }

    private fun setUpAdapter() {

        recycler_view.layoutManager = GridLayoutManager(this, 3,
                GridLayoutManager.VERTICAL, false)
        recycler_view.itemAnimator = DefaultItemAnimator()

        no_internet_tv.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
        swipe_refresh.isRefreshing = true
        mNetworkController.getBooks()

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
    fun onError(event: ErrorModel) {
        swipe_refresh.isRefreshing = false
        no_internet_tv.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
        StaticMethod.showMessageOk(this, "", event.message, false)
    }

    @Subscribe
    fun getData(list: List<BooksModel>) {
        if (list.isEmpty()) {
            no_internet_tv.visibility = View.VISIBLE
            recycler_view.visibility = View.GONE
        } else {
            this.list = list
            no_internet_tv.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
            listData(list)
        }
        swipe_refresh.isRefreshing = false
    }

    private fun listData(list: List<BooksModel>) {
        val adapter = BooksAdapter(this, object : BooksAdapter.OnClick {
            override fun onClick(position: Int) {
                val intent = Intent(this@BooksActivity, HomeActivity::class.java)
                intent.putExtra("id", list[position].bookId)
                intent.putExtra("name", list[position].bookName)
                startActivity(intent)
            }
        })
        recycler_view.adapter = adapter
        adapter.setData(list)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        exitDialog()
    }

    private fun exitDialog() {
        val view = this.layoutInflater.inflate(R.layout.exit_dialog_layout, null)
        val builder = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
        val dialog = builder.create()
        dialog.show()
        view.yes_tv.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        view.no_tv.setOnClickListener {
            dialog.dismiss()
        }
    }
}
