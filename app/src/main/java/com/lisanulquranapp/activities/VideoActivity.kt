package com.lisanulquranapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.MediaController
import android.widget.VideoView
import com.lisanulquranapp.R
import com.lisanulquranapp.base.BaseActivity
import kotlinx.android.synthetic.main.toolbar_layout.*

class VideoActivity : BaseActivity() {


    var simpleVideoView: VideoView? = null
    var mediaControls: MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowDirection()
        setContentView(R.layout.activity_video)

        init()
    }

    private fun init() {

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.video)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        // create an object of media controller
//        val mediaController = MediaController(this)
//// initiate a video view
//        val simpleVideoView = findViewById<View>(R.id.simpleVideoView) as VideoView
//// set media controller object for a video view
//        simpleVideoView.setMediaController(mediaController)
//
////        val uri = Uri.parse("http://abhiandroid-8fb4.kxcdn.com/ui/wp-content/uploads/2016/04/videoviewtestingvideo.mp4")
//        val uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
//        simpleVideoView.setVideoURI(uri)
//                simpleVideoView.start()
//        progressBar.visibility= View.VISIBLE
//        simpleVideoView.visibility = View.GONE

        // perform set on prepared listener event on video view
//        simpleVideoView.setOnPreparedListener()
//        {
//            progressBar.visibility= View.GONE
//            simpleVideoView.visibility = View.VISIBLE
//            // do something when video is ready to play
//        }

//        simpleVideoView.setOnPreparedListener(object : MediaPlayer.OnPreparedListener{
//            override fun onPrepared(p0: MediaPlayer?) {
//             //   TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                progressBar.visibility= View.GONE
////                simpleVideoView.visibility = View.VISIBLE
//                simpleVideoView.start()
//            }
//        })

//        simpleVideoView.setOnPreparedListener {
////            progressBar.visibility= View.GONE
////            simpleVideoView.start()
//        }

        // implement on completion listener on video view
//        simpleVideoView.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
//            override fun onCompletion(mp: MediaPlayer) {
////                Toast.makeText(applicationContext, "Thank You...!!!", Toast.LENGTH_LONG).show() // display a toast when an video is completed
//            }
//        })
//        simpleVideoView.setOnErrorListener(object : MediaPlayer.OnErrorListener {
//            override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
//                Toast.makeText(applicationContext, "Oops An Error Occur While Playing Video...!!!", Toast.LENGTH_LONG).show() // display a toast when an error is occured while playing an video
//                return false
//            }
//        })

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

                val intent = Intent(this@VideoActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
