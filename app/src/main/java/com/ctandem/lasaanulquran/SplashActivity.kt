package com.ctandem.lasaanulquran

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.ctandem.lasaanulquran.activities.BooksActivity
import com.ctandem.lasaanulquran.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    private var runnable: Runnable = Runnable {
        val intent = Intent(this, BooksActivity::class.java)
//        val intent = Intent(this, MeaningsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.enter, R.anim.exit)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_splash)

        val mFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_splash)
        splash!!.startAnimation(mFadeInAnimation)

        mHandler = Handler()
        mHandler!!.postDelayed(runnable, 3000)
    }

    override fun onBackPressed() {
        try {
            mHandler!!.removeCallbacks(runnable)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        super.onBackPressed()
    }

    companion object {
        private var mHandler: Handler? = null
    }

}