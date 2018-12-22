package com.lisanulquranapp.activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import com.lisanulquranapp.R
import com.lisanulquranapp.adapters.ChapterDetailAdapter
import com.lisanulquranapp.base.BaseActivity
import com.lisanulquranapp.db.AppDatabase
import com.lisanulquranapp.models.ContentList
import com.lisanulquranapp.models.ContentModel
import kotlinx.android.synthetic.main.activity_chapter_detail.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.json.JSONArray




class ChapterDetailActivity : BaseActivity() , View.OnTouchListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    lateinit var model: ContentModel
    var dbModel: ContentModel? = null
    lateinit var adapter: ChapterDetailAdapter

    private lateinit var seekbarAudioPlayer: SeekBar

    private var mediaPlayer: MediaPlayer? = null
    private var mediaFileLengthInMilliseconds: Int = 0 // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class

    private val handler = Handler()
    private var audioUrl:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowDirection()
        setContentView(R.layout.activity_chapter_detail)
        model = mSessionController.contentModel
        dbModel = AppDatabase.getInstance(this).appDao().getContent(model.contentId)

        init()
    }

    private fun init() {

        setSupportActionBar(toolbar)
        supportActionBar?.title = model.contentTitle
        supportActionBar?.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        player_constraint.visibility = View.GONE

        seekbarAudioPlayer = findViewById(R.id.seekbar_audio_player)
        seekbarAudioPlayer.max = 99 // It means 100% .0-99
        seekbarAudioPlayer.setOnTouchListener(this)

        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setOnBufferingUpdateListener(this)
        mediaPlayer!!.setOnCompletionListener(this)

        detail_rv.layoutManager = LinearLayoutManager(this)
//        detail_rv.itemAnimator = DefaultItemAnimator()

        val array = JSONArray(model.contentArray)
        val contentsList = ArrayList<ContentList>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val content = ContentList()
            content.arabic = obj.getString("content_ar")
            content.urdu = obj.getString("content_ur")
            contentsList.add(content)
        }
        adapter = ChapterDetailAdapter(this, contentsList)
        detail_rv.adapter = adapter
//        detail_rv.scrollToPosition(0);

        val mediaArray = JSONArray(model.contentMedia)
        for (i in 0 until mediaArray.length()) {
            val obj = mediaArray.getJSONObject(i)
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

            if(obj.getString("media_type").equals("youtube_link")) {
                video_iv.visibility = View.VISIBLE
                video_iv.setOnClickListener {
                    val intent = Intent(this, YoutubeActivity::class.java)
                    intent.putExtra("videoId", obj.getString("path"))
                    startActivity(intent)
                }
            }
        }

        detail_rv.smoothScrollToPosition(0);
        detail_rv.getLayoutManager().smoothScrollToPosition(
                detail_rv,null,0)

        minus_size_iv.setOnClickListener {
            adapter.textChange(-15)
        }

        plus_size_iv.setOnClickListener {
            adapter.textChange(15)
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

//    fun mediaTypeDialog() {
//
//        val items = arrayOf<CharSequence>("Audio", "Video")
//
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Select")
//        builder.setItems(items, DialogInterface.OnClickListener { dialog, item ->
//            // Do something with the selection
//            if (item == 0) {
//                val mediaArray = JSONArray(model.contentMedia)
//                var found:Int = 0
//                for (i in 0 until mediaArray.length()) {
//                    val obj = mediaArray.getJSONObject(i)
//                    if(obj.getString("media_type").equals("audio")) {
//                        found = 1
//                        val intent = Intent(this, AudioActivity::class.java)
//                        val path = obj.getString("path")
//                        intent.putExtra("audioUrl", path)
//                        startActivity(intent)
//                    }
//                }
//                if(found == 0)
//                    Toast.makeText(this, "No  Audio Found!", Toast.LENGTH_SHORT).show()
////                val intent = Intent(this, AudioActivity::class.java)
////                startActivity(intent)
//            } else if (item == 1) {
//                var found:Int = 0
//                val mediaArray = JSONArray(model.contentMedia)
//                for (i in 0 until mediaArray.length()) {
//                    val obj = mediaArray.getJSONObject(i)
//                    if(obj.getString("media_type").equals("youtube_link")) {
//                        found = 1
//                        val intent = Intent(this, YoutubeActivity::class.java)
//                        intent.putExtra("videoId", obj.getString("path"))
//                        startActivity(intent)
//                    }
//                }
//                if(found == 0)
//                    Toast.makeText(this, "No  Video Found!", Toast.LENGTH_SHORT).show()
//            }
//            dialog.dismiss()
//        })
//        builder.show()
//
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.chapter_detail_menu, menu)
        if (dbModel != null && dbModel!!.isSaved) {
            menu.getItem(1).icon = ContextCompat.getDrawable(this,
                    R.drawable.ic_bookmark_unselected)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.sent_info) + ":\n" +
                                model.contentTitle)
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
            R.id.action_bookmark -> {
                dbModel = AppDatabase.getInstance(this).appDao().getContent(model.contentId)
                if (dbModel != null && dbModel!!.isSaved) {
                    AppDatabase.getInstance(this).appDao().delete(dbModel)
                    item.icon = ContextCompat.getDrawable(this,
                            R.drawable.ic_bookmar_new)
                } else {
                    model.isSaved = true
                    AppDatabase.getInstance(this).appDao().insertContent(model)
                    item.icon = ContextCompat.getDrawable(this,
                            R.drawable.ic_bookmark_unselected)
                }
            }
            R.id.action_home -> {

                val intent = Intent(this@ChapterDetailActivity, HomeActivity::class.java)
//                intent.putExtra("id", list[position].bookId)
//                intent.putExtra("name", list[position].bookName)
                intent.flags = FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        finish()
    }

}
