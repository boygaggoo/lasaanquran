package com.ctandem.lasaanulquran.activities

import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import com.ctandem.lasaanulquran.R
import com.ctandem.lasaanulquran.base.BaseActivity
import kotlinx.android.synthetic.main.activity_audio.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class AudioActivity : BaseActivity(), View.OnTouchListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    private lateinit var seekbarAudioPlayer: SeekBar

    private var mediaPlayer: MediaPlayer? = null
    private var mediaFileLengthInMilliseconds: Int = 0 // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class

    private val handler = Handler()
    private var audioUrl:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowDirection()
        setContentView(R.layout.activity_audio)
        audioUrl = intent.getStringExtra("audioUrl")
        init()
    }

    private fun init() {

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.audio)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        seekbarAudioPlayer = findViewById(R.id.seekbar_audio_player)
        seekbarAudioPlayer.max = 99 // It means 100% .0-99
        seekbarAudioPlayer.setOnTouchListener(this)

        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setOnBufferingUpdateListener(this)
        mediaPlayer!!.setOnCompletionListener(this)

        progress_bar.visibility = View.VISIBLE
        play_iv.visibility = View.INVISIBLE
        Log.e("audiourl",audioUrl)
        mediaPlayer!!.setDataSource(audioUrl)
        mediaPlayer!!.setOnPreparedListener(OnPreparedListener {
            mp -> mp.start()
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

            var totalDuration = String.format("%d:%02d", mediaFileLengthInMilliseconds / 60000, mediaFileLengthInMilliseconds/1000 % 60)
            Log.e("totalDuration", totalDuration)
            total_duration_text.text = totalDuration

            primarySeekBarProgressUpdater()
        })
        mediaPlayer!!.prepareAsync()

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
//            Log.e("AudioActivity", totalDuration)
            total_duration_text.text = totalDuration

            primarySeekBarProgressUpdater()

        }

        next_iv.setOnClickListener {
//            Toast.makeText(this,"next", Toast.LENGTH_SHORT).show()
        }

        previous_iv.setOnClickListener {
//            Toast.makeText(this,"previous", Toast.LENGTH_SHORT).show()
        }
    }


    /** Method which updates the SeekBar primary progress by current song playing position */
    private fun primarySeekBarProgressUpdater() {
        try {
            var currentDuration = String.format("%d:%02d", mediaPlayer!!.currentPosition / 60000, mediaPlayer!!.currentPosition/1000 % 60)
            current_duration_text.text = currentDuration

            Log.e("CurrentDuration",currentDuration)
            Log.e("MediaCurrent",mediaPlayer!!.currentPosition.toString())
//         var duration = ((mediaPlayer!!.currentPosition / mediaFileLengthInMilliseconds) * 100) as Int // This math construction give a percentage of "was playing"/"song length"
            seekbarAudioPlayer.progress = mediaPlayer!!.currentPosition/1000 % 60
            if (mediaPlayer!!.isPlaying) {
                val notification = Runnable { primarySeekBarProgressUpdater() }
                handler.postDelayed(notification, 1000)
            }
        }catch (e: Exception){

        }
    }

    override fun onBufferingUpdate(p0: MediaPlayer?, p1: Int) {
        seekbarAudioPlayer.secondaryProgress = p1
    }

    override fun onCompletion(p0: MediaPlayer?) {
        play_iv.setImageResource(R.drawable.ic_playfab)
//        killMediaPlayer()
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        if (p0!!.id === R.id.seekbar_audio_player) {
            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position */
            if (mediaPlayer!!.isPlaying) {
                val sb = p0 as SeekBar
                val playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.progress
                mediaPlayer!!.seekTo(playPositionInMillisecconds)
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

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return super.onSupportNavigateUp()
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
                val intent = Intent(this@AudioActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
