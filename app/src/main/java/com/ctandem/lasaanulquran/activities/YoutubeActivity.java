package com.ctandem.lasaanulquran.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ctandem.lasaanulquran.R;
import com.ctandem.lasaanulquran.utils.StaticFlavor;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    public static final String TAG = YoutubeActivity.class.getSimpleName();

    private YouTubePlayer YPlayer;
    private String videoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        videoId = getIntent().getStringExtra("videoId");
        initUI();
    }

    private void initUI() {
        try {

            ImageView ivBackButton = findViewById(R.id.ivBackButton);
            ivBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    YoutubeActivity.super.onBackPressed();
                }
            });
//            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
//
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
//            // mTitle.setTextSize(15);
//
//            //mTitle.setText(Constant.image + " " + Constant.selected_level + " - Quiz");
//            if(!SessionController.getInstance().getTestMode()){
//                mTitle.setText(Constant.image + " - Preparation");
//            }else {
//                mTitle.setText(Constant.image + " - Test");
//            }
//
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//            settingsIB = (ImageButton) toolbar.findViewById(R.id.settingsIB);
//            settingsIB.setVisibility(View.VISIBLE);
//            settingsIB.setOnClickListener(PlayQuizPrepActivity.this);
//
//            homeIB = (ImageButton) toolbar.findViewById(R.id.homeIB);
//            homeIB.setVisibility(View.VISIBLE);
//            homeIB.setOnClickListener(PlayQuizPrepActivity.this);


            YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
            youTubeView.initialize(StaticFlavor.YOUTUBE_API_KEY, this);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(StaticFlavor.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        YPlayer = youTubePlayer;
		/*
		 * Now that this variable YPlayer is global you can access it
		 * throughout the activity, and perform all the player actions like
		 * play, pause and seeking to a position by code.
		 */
        if (!b) {
           // YPlayer.cueVideo(videoId/*"I4IMcjtPoI8"*/);
            YPlayer.loadVideo(videoId,1);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer",
                    youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chapter_detail_menu,menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        menu.getItem(2).setVisible(true);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_home:
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
