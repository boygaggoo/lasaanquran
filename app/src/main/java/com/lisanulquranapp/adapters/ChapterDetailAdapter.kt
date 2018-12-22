package com.lisanulquranapp.adapters

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lisanulquranapp.R
import com.lisanulquranapp.models.ContentList
import kotlinx.android.synthetic.main.detail_item_row.view.*

class ChapterDetailAdapter(var context: Context, private var list: List<ContentList>) :
        RecyclerView.Adapter<ChapterDetailAdapter.ViewHolder>() {

    private var textSize: Int = 0

    public fun textChange(txtSize: Int) {
//        Log.e("textsize", txtSize .toString())
        textSize = txtSize
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.detail.setBackgroundColor(Color.TRANSPARENT)
//        holder.heading.setBackgroundColor(Color.TRANSPARENT)
//        holder.detail.settings.builtInZoomControls = true;
//        holder.detail.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY;
//        holder.detail.settings.setSupportZoom(true);
//        holder.detail.settings.displayZoomControls = true;
//        holder.detail.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN;
//        holder.detail.settings.loadWithOverviewMode = true;
//        holder.detail.settings.useWideViewPort = true;
//        Log.e("textxoom", (holder.detail.settings.textZoom + textSize).toString());
//        holder.detail.settings.textZoom = holder.detail.settings.textZoom + textSize
        holder.detail.settings.javaScriptEnabled = true;
//        holder.heading.settings.javaScriptEnabled = true;
//        holder.detail.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY;
//        holder.detail.isScrollbarFadingEnabled = false;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.heading.text = Html.fromHtml(list[position].arabic,Html.FROM_HTML_MODE_LEGACY)
//            holder.detail.text = Html.fromHtml(list[position].urdu,Html.FROM_HTML_MODE_LEGACY)
            Log.e("if","andoridN");
//            val pish = "<html><head><style type=\"text/css\">@font-face {font-family: Jameel Noori Nastaleeq;src: url(\"file:///android_asset/font/jameel_noori.ttf\")}body {font-family: Jameel Noori Nastaleeq;font-size: medium;text-align: justify;}</style></head><body>"
            val pish = "<html><head><style type=\"text/css\">@font-face {font-family: Jameel Noori Nastaleeq;src: url(\"file:///android_asset/font/jameel_noori.ttf\")}@font-face {font-family: Muhammadi Quranic Font;src: url(\"file:///android_asset/font/muhammadi_quranic.ttf\")}body {font-family: Jameel Noori Nastaleeq;font-size: large;text-align: center;}</style></head><body>"
            val pas = "</body></html>"
            val myHtmlString = pish + list[position].urdu + pas
            val myHtmlStringAr = pish + list[position].arabic+ pas
            Log.e("ifdata",myHtmlString)
//            holder.heading.loadDataWithBaseURL(null, myHtmlStringAr, "text/html", "UTF-8", null)
            holder.detail.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null)

        } else {
            Log.e("else","andorid");
            holder.heading.text = Html.fromHtml(list[position].arabic)
//            holder.detail.text = Html.fromHtml(list[position].urdu)
//            val pish = "<html><head><style type=\"text/css\">@font-face {font-family: Jameel Noori Nastaleeq;src: url(\"file:///android_asset/font/jameel_noori.ttf\")}body {font-family: Jameel Noori Nastaleeq;font-size: medium;text-align: justify;}</style></head><body>"
            val pish = "<html><head><style type=\"text/css\">@font-face {font-family: Jameel Noori Nastaleeq;src: url(\"file:///android_asset/font/jameel_noori.ttf\")}@font-face {font-family: Muhammadi Quranic Font;src: url(\"file:///android_asset/font/muhammadi_quranic.ttf\")}body {font-family: Jameel Noori Nastaleeq;font-size: large;text-align: center;}</style></head><body>"
            val pas = "</body></html>"
            val myHtmlString = pish + list[position].urdu + pas
            Log.e("ifdata",myHtmlString)
            val myHtmlStringAr = pish + list[position].arabic+ pas
//            holder.heading.loadDataWithBaseURL(null, myHtmlStringAr, "text/html", "UTF-8", null)
            holder.detail.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null)
        }

        Log.e("textxoom", (holder.detail.settings.textZoom + textSize).toString());
        if(list[position].urdu!=null && !list[position].urdu.isEmpty())
            holder.detail.settings.textZoom = holder.detail.settings.textZoom + textSize

//        if(list[position].arabic!=null && !list[position].arabic.isEmpty())
//            holder.heading.settings.textZoom = holder.heading.settings.textZoom + textSize
//        if(textSize > 0){
////            holder.heading.textSize= holder.heading.textSize + 5
//            holder.heading.setTextSize(TypedValue.COMPLEX_UNIT_SP,Float.Z)
//        }
//        else
//            holder.heading.textSize= holder.heading.textSize -5
//        Log.e("size",holder.heading.textSize.toString())
//        holder.detail.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
////            val i = Intent(Intent.ACTION_VIEW)
////            i.data = Uri.parse(url)
////            context.startActivity(i)
//            val request = DownloadManager.Request(
//                    Uri.parse(url))
//            request.setMimeType(mimetype)
//            val cookies = CookieManager.getInstance().getCookie(url)
//            request.addRequestHeader("cookie", cookies)
//            request.addRequestHeader("User-Agent", userAgent)
//            request.setDescription("Downloading file...")
//            request.setTitle(URLUtil.guessFileName(url, contentDisposition,
//                    mimetype))
//            request.allowScanningByMediaScanner()
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//            request.setDestinationInExternalPublicDir(
//                    Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
//                    url, contentDisposition, mimetype))
//            val dm = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
//            dm.enqueue(request)
//            Toast.makeText(context.applicationContext, "Downloading File",
//                    Toast.LENGTH_LONG).show()
//        })
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent!!.context).inflate(R.layout.detail_item_row, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val heading = itemView.heading_tv!!
        val detail = itemView.detail_tv!!
    }
}