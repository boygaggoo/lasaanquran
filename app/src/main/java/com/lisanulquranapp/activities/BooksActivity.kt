package com.lisanulquranapp.activities

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.lisanulquranapp.R
import com.lisanulquranapp.adapters.BooksAdapter
import com.lisanulquranapp.base.BaseActivity
import com.lisanulquranapp.models.BooksModel
import com.lisanulquranapp.models.ErrorModel
import com.lisanulquranapp.permissions.PermissionUtility
import com.lisanulquranapp.permissions.PermissionsConstants
import com.lisanulquranapp.preferences.PreferenceHandler
import com.lisanulquranapp.retrofit.CallingWebServices
import com.lisanulquranapp.retrofit.ResponseModel
import com.lisanulquranapp.retrofit.ServiceResponse
import com.lisanulquranapp.utils.StaticMethod
import com.michael.easydialog.EasyDialog
import kotlinx.android.synthetic.main.activity_books.*
import kotlinx.android.synthetic.main.exit_dialog_layout.view.*
import kotlinx.android.synthetic.main.filter_layout.view.*
import kotlinx.android.synthetic.main.layout_notification_dialog.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class BooksActivity : ServiceResponse, BaseActivity() {
    override fun onSuccess(`object`: Any?) {
        val responseModel = `object` as ResponseModel
        Log.d("success", responseModel.message)
    }

    override fun onFail(`object`: Any?) {
        val stringOnFail = `object` as String
        Log.d("success", stringOnFail)
    }

    override fun onError(`object`: Any?) {
        val stringOnError = `object` as String
        Log.d("success", stringOnError)
    }

    lateinit var list: List<BooksModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowDirection()
        setContentView(R.layout.activity_books)
        get()
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

        notification_toolbar_iv.setOnClickListener {
            if (TextUtils.isEmpty(PreferenceHandler.getInstance(this).getString("title", "no title")) || TextUtils.equals(PreferenceHandler.getInstance(this).getString("title", "no title"), "no title")) {
                Toast.makeText(this, "No notification to show", Toast.LENGTH_SHORT).show()
            } else {
                showNotificationDialog()
            }
        }

        swipe_refresh.setOnRefreshListener {
            setUpAdapter()
        }
        setUpAdapter()
    }

    private fun showNotificationDialog() {
        val dialogView = LayoutInflater.from(this@BooksActivity).inflate(R.layout.layout_notification_dialog, null)
        val tvTitle = dialogView.tvTitle
        val tvMessage = dialogView.tvMessage
        tvTitle.setText(PreferenceHandler.getInstance(this).getString("title", "no title"))
        tvMessage.setText(PreferenceHandler.getInstance(this).getString("message", "no message"))
        val builder = AlertDialog.Builder(this@BooksActivity)
        builder.setView(dialogView)
        builder.setPositiveButton("OK") { dialogInterface, i ->
            dialogInterface.dismiss()
        }
        builder.create().show()
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

    private fun get() {
        if (PermissionUtility.isPermissionGranted(Manifest.permission.READ_PHONE_STATE, this)) {

            sendFirebaseToken()
        } else {
            PermissionUtility.askForStateReadPermission(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PermissionsConstants.READ_PHONE_STATE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendFirebaseToken()
            }
        }
    }


    private fun sendFirebaseToken() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        val tel = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        CallingWebServices.getInstance().sendFirebaseToken(tel.deviceId, refreshedToken, this);
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
