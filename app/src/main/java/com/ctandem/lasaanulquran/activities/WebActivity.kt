package com.ctandem.lasaanulquran.activities

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.SeekBar
import com.ctandem.lasaanulquran.R
import com.ctandem.lasaanulquran.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.progressbar.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.json.JSONArray

/**
 * Created by Nauman Ashraf on 6/9/2018.
 */

class WebActivity : BaseActivity() , View.OnTouchListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    private var headerTitle: String? = ""
    private var url: String? = ""
    private var media: String? = ""

    private lateinit var seekbarAudioPlayer: SeekBar

    private var mediaPlayer: MediaPlayer? = null
    private var mediaFileLengthInMilliseconds: Int = 0 // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class

    private val handler = Handler()
    private var audioUrl:String? = ""

    private lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowDirection()
        setContentView(R.layout.activity_web)
        try {
            headerTitle = intent.extras!!.getString("WebTitle")
            url = intent.extras!!.getString("WebLink")
            media = intent.extras!!.getString("media")
            Log.e("webact", media!! + "")
        } catch (e: Exception) {
            Log.e("WebActivity", "Error getting extra: $e")
        }

        initViews()
        init()
        handleEvents()
    }

    private fun init() {
//        toolbar.title = headerTitle
//        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setDisplayShowHomeEnabled(true)

        setSupportActionBar(toolbar)
        supportActionBar?.title = headerTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        player_constraint.visibility = View.GONE

        seekbarAudioPlayer = findViewById(R.id.seekbar_audio_player)
        seekbarAudioPlayer.max = 99 // It means 100% .0-99
        seekbarAudioPlayer.setOnTouchListener(this)

        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setOnBufferingUpdateListener(this)
        mediaPlayer!!.setOnCompletionListener(this)

        val settings = webView.settings
        settings.javaScriptEnabled = true

        settings.setSupportZoom(true)
        settings.builtInZoomControls = false
        webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webView.loadUrl(url)

        webView!!.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }

//        webView.webViewClient = object : WebViewClient() {
//
//            override fun onPageStarted(webView: WebView, link: String, icon: Bitmap) {
////                super.onPageStarted(webView, link, icon)
//                progressBar.visibility = View.VISIBLE
//            }
//
//            override fun onPageFinished(view: WebView, url: String) {
//                progressBar.visibility = View.GONE
//            }
//        }

        if (media != null && !media!!.isEmpty()) {
            try {
                val mediaArray = JSONArray(media)

                for (i in 0 until mediaArray.length()) {
                    val obj = mediaArray.getJSONObject(i)
                    val path = obj.getString("path")
                    if(obj.getString("media_type").equals("audio")) {
                        audioUrl = obj.getString("path")
                        player_constraint.visibility = View.VISIBLE
//                play_iv.setOnClickListener {
//                    val intent = Intent(this, AudioActivity::class.java)
//                    intent.putExtra("audioUrl", path)
//                    startActivity(intent)
//                }

                        progress_bar.visibility = View.VISIBLE
                        play_iv.visibility = View.INVISIBLE
                        Log.e("audiourl",audioUrl)
                        mediaPlayer!!.setDataSource(audioUrl)
                        mediaPlayer!!.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
                            mp.start()
                            progress_bar.visibility = View.GONE
                            play_iv.visibility = View.VISIBLE
                            play_iv.setImageResource(R.drawable.ic_pause)
                            mediaFileLengthInMilliseconds = mediaPlayer!!.duration // gets the song length in milliseconds from URL

                            if (!mediaPlayer!!.isPlaying) {
                                mediaPlayer!!.start()
                                play_iv.setImageResource(R.drawable.ic_pause)
                            } else {
                                mediaPlayer!!.pause()
                                play_iv.setImageResource(R.drawable.ic_playfab)
                            }

                            seekbarAudioPlayer.max =  mediaFileLengthInMilliseconds
                            var totalDuration = String.format("%d:%02d", mediaFileLengthInMilliseconds / 60000, mediaFileLengthInMilliseconds / 1000 % 60)
                            Log.e("totalDuration", totalDuration)
                            total_duration_text.text = totalDuration

                            primarySeekBarProgressUpdater()
                        })
                        mediaPlayer!!.prepareAsync()
                    }
                    if (obj.getString("media_type") == "youtube_link") {
                        video_iv.visibility = View.VISIBLE
                        video_iv.setOnClickListener {
                            val intent = Intent(this@WebActivity, YoutubeActivity::class.java)
                            intent.putExtra("videoId", path)
                            startActivity(intent)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        play_iv.setOnClickListener {
            //  Toast.makeText(this,"play", Toast.LENGTH_SHORT).show()

            try {
//                mediaPlayer!!.setDataSource("http://songs.apniisp.com/Coke%20Studio%2010/02%20-%20Chaa%20Rahi%20Kaali%20Ghata%20-%20Hina%20Nasrullah%20&%20Amanat%20Ali%20(ApniISP.Com).mp3")
//                mediaPlayer!!.setDataSource(audioUrl)
//                val filename = "android.resource://" + this.packageName + "/raw/audio"
//                mediaPlayer!!.setDataSource(this, Uri.parse(filename))
//                mediaPlayer!!.prepare()
                mediaPlayer!!.prepareAsync()

            } catch (e: Exception) {
                e.printStackTrace()
            }


            mediaFileLengthInMilliseconds = mediaPlayer!!.duration // gets the song length in milliseconds from URL

            if (!mediaPlayer!!.isPlaying) {
                mediaPlayer!!.start()
                play_iv.setImageResource(R.drawable.ic_pause)
            } else {
                mediaPlayer!!.pause()
                play_iv.setImageResource(R.drawable.ic_playfab)
            }

            var totalDuration = String.format("%d:%02d", mediaFileLengthInMilliseconds / 60000, mediaFileLengthInMilliseconds/1000 % 60)
            Log.e("totalDuration", totalDuration)
            total_duration_text.text = totalDuration

            primarySeekBarProgressUpdater()

        }

    }

    private fun initViews() {
//        toolbar = findViewById(R.id.toolbar)
        webView = findViewById(R.id.web_view)
//        progressBar = findViewById(R.id.progressBar)
//        audioIv = findViewById(R.id.play_iv)
//        videoIv = findViewById(R.id.video_iv)

    }

    private fun handleEvents() {}

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
        //return super.onSupportNavigateUp();
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.chapter_detail_menu, menu)
        menu.getItem(0).isVisible = false
        menu.getItem(1).isVisible = false
        menu.getItem(2).isVisible = true
        return true
        //        return super.onCreateOptionsMenu(menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                this.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /** Method which updates the SeekBar primary progress by current song playing position */
    private fun primarySeekBarProgressUpdater() {
        try {
            var currentDuration = String.format("%d:%02d", mediaPlayer!!.currentPosition / 60000, mediaPlayer!!.currentPosition/1000 % 60)
//            current_duration_text.text = currentDuration
            Log.e("CurrentDuration",currentDuration)
            Log.e("MediaCurrent",mediaPlayer!!.currentPosition.toString())
//         var duration = ((mediaPlayer!!.currentPosition / mediaFileLengthInMilliseconds) * 100) as Int // This math construction give a percentage of "was playing"/"song length"
            seekbarAudioPlayer.progress = mediaPlayer!!.currentPosition.toInt()/*/1000 % 60*/
            if (mediaPlayer!!.isPlaying) {
                val notification = Runnable { primarySeekBarProgressUpdater() }
                handler.postDelayed(notification, 100)
            }
        }catch (e: Exception){

            e.printStackTrace()
        }
    }

    override fun onBufferingUpdate(p0: MediaPlayer?, p1: Int) {
        seekbarAudioPlayer.secondaryProgress = p1
    }

    override fun onCompletion(p0: MediaPlayer?) {
        play_iv.setImageResource(R.drawable.ic_playfab)
        seekbarAudioPlayer.progress = 0;
//        killMediaPlayer()
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        if (p0!!.id === R.id.seekbar_audio_player) {
            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position */
            if (mediaPlayer!!.isPlaying) {
//                val sb = p0 as SeekBar
//                val playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * seekbarAudioPlayer.progress
                Log.e("progress",  seekbarAudioPlayer.progress.toString())
                mediaPlayer!!.seekTo(/*playPositionInMillisecconds*/seekbarAudioPlayer.progress)
            }
        }
        return false
    }

    private fun killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer!!.reset()
                mediaPlayer!!.release()
//                mediaPlayer = null
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        killMediaPlayer()
    }
}