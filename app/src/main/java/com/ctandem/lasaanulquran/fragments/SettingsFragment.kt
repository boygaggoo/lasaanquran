package com.ctandem.lasaanulquran.fragments

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctandem.lasaanulquran.R
import com.ctandem.lasaanulquran.activities.WebActivity
import com.ctandem.lasaanulquran.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings.view.*




class SettingsFragment : BaseFragment() {

    companion object {
        private var settingsInstance: SettingsFragment? = null

        fun getInstance(): SettingsFragment {
            if (settingsInstance == null) {
                settingsInstance = SettingsFragment()
            }
            return settingsInstance!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_settings, container, false)

        init(view)
        retainInstance = true

        return view
    }

    private fun init(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.arrow1.drawable.isAutoMirrored = true
            view.arrow2.drawable.isAutoMirrored = true
            view.arrow3.drawable.isAutoMirrored = true
        }

        view.about_us_relative.setOnClickListener {
            val intent = Intent(activity, WebActivity::class.java)
            intent.putExtra("WebTitle", getString(R.string.settings_about_us))
            intent.putExtra("WebLink",
                    "https://www.google.com")
//             "https://www.youtube.com/watch?v=HUuElo1N8Kc&index=12&list=PLuY-_qJ5ZfmzVjVj4-dAI2MNMfWDecwaN")
            startActivity(intent)
//            val videoId = "vl2kbc8ARwU"
            //StaticMethod.showToast(mContext,"Nothing " + position + " " + videoId);
            //intent = YouTubeIntents.createPlayVideoIntent(getActivity(), "I4IMcjtPoI8");
//            val intent = Intent(activity, YoutubeActivity::class.java)
//            intent.putExtra("videoId", videoId)
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(mContext as Activity,
//                    videoHolder.video_constraint, ViewCompat.getTransitionName(videoHolder.video_constraint))
//            mContext.startActivity(intent, options.toBundle())
//            startActivity(intent)
        }
//        view.notification_switch.setOnCheckedChangeListener { _, isChecked ->
//            Log.e("Check", isChecked.toString())
//        }

        view.terms_relative.setOnClickListener {
            val intent = Intent(activity, WebActivity::class.java)
            intent.putExtra("WebTitle", getString(R.string.settings_terms_text))
            intent.putExtra("WebLink", "http://google.com")
            startActivity(intent)
        }

        view.privacy_relative.setOnClickListener {
            val intent = Intent(activity, WebActivity::class.java)
            intent.putExtra("WebTitle", getString(R.string.settings_privacy_policy))
            intent.putExtra("WebLink", "http://google.com")
            startActivity(intent)
        }


        view.share_relative.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out Lisan Ul Quran app at: https://play.google.com/store/apps/details?id=com.lisanulquranapp")
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }

        view.feedback_relative.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "lisanulquran.in.urdu@gmail.com", null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }

    }

}