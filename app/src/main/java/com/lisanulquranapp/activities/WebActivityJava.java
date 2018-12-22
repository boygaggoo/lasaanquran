package com.lisanulquranapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lisanulquranapp.R;
import com.lisanulquranapp.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;


public class WebActivityJava extends BaseActivity {

    WebView webView;
    String headerTitle = "";
    String url = "";
    String media = "";
    Toolbar toolbar;
    LinearLayout progressBar;
    ImageView audioIv, videoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowDirection();
        setContentView(R.layout.activity_web);
        try {
            headerTitle = getIntent().getExtras().getString("WebTitle");
            url = getIntent().getExtras().getString("WebLink");
            media = getIntent().getExtras().getString("media");
            Log.e("webact", media + "");
        } catch (Exception e) {
            Log.e("WebActivity", "Error getting extra: " + e);
        }
        initViews();
        init();
        handleEvents();
    }

    private void init() {
        toolbar.setTitle(headerTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(media!=null && !media.isEmpty()){
            try{
                JSONArray mediaArray = new JSONArray(media);

                for (int i=0; i<mediaArray.length();i++) {
                    JSONObject obj = mediaArray.getJSONObject(i);
                    final String path = obj.getString("path");
//                    if(obj.getString("media_type").equals("audio")) {
//                        audioIv.setVisibility(View.VISIBLE);
//                        audioIv.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent intent = new Intent(WebActivity.this, AudioActivity.class);
//                                intent.putExtra("audioUrl", path);
//                                startActivity(intent);
//                            }
//                        });
//                    }

                    if(obj.getString("media_type").equals("youtube_link")) {
                        videoIv.setVisibility(View.VISIBLE);
                        videoIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(WebActivityJava.this, YoutubeActivity.class);
                                intent.putExtra("videoId", path);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }catch (Exception e){

            }
        }

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageStarted(WebView webView, String link, Bitmap icon) {
                super.onPageStarted(webView, link, icon);
                progressBar.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progressBar);
        audioIv = findViewById(R.id.play_iv);
        videoIv = findViewById(R.id.video_iv);

    }

    private void handleEvents() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  true;
        //return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
