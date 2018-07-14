package com.ctandem.lasaanulquran.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.ctandem.lasaanulquran.AppController;
import com.ctandem.lasaanulquran.R;
import com.ctandem.lasaanulquran.models.BooksModel;
import com.ctandem.lasaanulquran.models.CardsModel;
import com.ctandem.lasaanulquran.models.ChaptersModel;
import com.ctandem.lasaanulquran.models.ContentList;
import com.ctandem.lasaanulquran.models.ContentModel;
import com.ctandem.lasaanulquran.models.ErrorModel;
import com.ctandem.lasaanulquran.utils.SessionController;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nauman Ashraf.
 */
public class NetworkController implements Runnable {

    private final static String TAG = NetworkController.class.getCanonicalName();

    public static NetworkController instance;

    private Thread mThread = null;

    public NetworkController() {

        try {
            mThread = new Thread(NetworkController.this);
            mThread.setName("NetWorkController");
            mThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static NetworkController getInstance() {
        if (instance == null) {
            instance = new NetworkController();
        }
        return instance;
    }

    @Override
    public void run() {
        try {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        } catch (Exception e) {
            Log.e(TAG, "Error NetWorkController run  and error message " + e.getMessage());
        }
    }

    public void getBooks() {
        String url = UrlConstants.baseUrl + "book";
        final Request.Priority priority = Request.Priority.IMMEDIATE;
        StringRequest request = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String utfString = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    JSONObject reJsonObject = new JSONObject(utfString);
                    String status = reJsonObject.getString("status");
                    if (status.equalsIgnoreCase("success") ||
                            status.equalsIgnoreCase("1")) {
                        JSONArray jsonArray = reJsonObject.getJSONArray("data");
                        List<BooksModel> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            BooksModel model = new BooksModel();
                            model.setBookId(object.getLong("id"));
                            model.setBookName(object.getString("title"));
                            model.setBookNumber(object.getLong("sort_no"));
                            model.setIcon(object.getString("icon"));
                            model.setAvatar(object.getString("avatar"));
                            model.setFont(object.optInt("font"));
                            list.add(model);
                        }
                        EventBus.getDefault().post(list);
                    } else {
                        EventBus.getDefault().post(new ErrorModel("0", reJsonObject.getString("message")));
                    }
                } catch (Exception e) {
                    EventBus.getDefault().post(new ErrorModel("0", e.toString()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppController.getInstance().cancelPendingRequests("getBooks");
                try {
                    NetworkController.this.onErrorResponse(error);
                } catch (Exception e) {
                    EventBus.getDefault().post(new ErrorModel("0", e.toString()));
                }
            }
        }) {
            @Override
            public Request.Priority getPriority() {
                return priority;
            }
        };
        setTimeOut(request, 90000);
        AppController.getInstance().addToRequestQueue(request, "getBooks");
    }

    public void getChapters(long bookId) {
        String url = UrlConstants.baseUrl + "chapter/" + bookId;
        final Request.Priority priority = Request.Priority.IMMEDIATE;
        StringRequest request = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String utfString = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    JSONObject reJsonObject = new JSONObject(utfString);
                    String status = reJsonObject.getString("status");
                    if (status.equalsIgnoreCase("success") ||
                            status.equalsIgnoreCase("1")) {
                        JSONArray jsonArray = reJsonObject.getJSONArray("data");
                        List<ChaptersModel> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ChaptersModel model = new ChaptersModel();
                            model.setBookId(object.getLong("book_id"));
                            model.setChapterName(object.getString("title"));
                            model.setChapterIndex(object.getLong("sort_no"));
                            model.setChapterId(object.getLong("id"));
                            model.setFont(object.optInt("font"));
                            list.add(model);
                        }
                        EventBus.getDefault().post(list);
                    } else {
                        EventBus.getDefault().post(new ErrorModel("0", reJsonObject.getString("message")));
                    }
                } catch (Exception e) {
                    EventBus.getDefault().post(new ErrorModel("0", e.toString()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppController.getInstance().cancelPendingRequests("getChapters");
                try {
                    NetworkController.this.onErrorResponse(error);
                } catch (Exception e) {
                    EventBus.getDefault().post(new ErrorModel("0", e.toString()));
                }
            }
        }) {
            @Override
            public Request.Priority getPriority() {
                return priority;
            }
        };
        setTimeOut(request, 90000);
        AppController.getInstance().addToRequestQueue(request, "getChapters");
    }

    public void getChaptersTopic(long chapterId) {
        String url = UrlConstants.baseUrl + "chapter/topic/" + chapterId;

        final Request.Priority priority = Request.Priority.IMMEDIATE;
        StringRequest request = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String utfString = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    JSONObject reJsonObject = new JSONObject(utfString);
                    String status = reJsonObject.getString("status");
                    if (status.equalsIgnoreCase("success") ||
                            status.equalsIgnoreCase("1")) {
                        JSONArray jsonArray = reJsonObject.getJSONArray("data");
                        List<ContentModel> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ContentModel model = new ContentModel();
                            model.setContentId(object.getLong("id"));
                            model.setContentTitle(object.getString("title"));
                            model.setContentIndex(object.getLong("sort_no"));
                            model.setContentType(object.getLong("topic_type_id"));
                            model.setSaved(false);
                            model.setContentArray(object.getJSONArray("contents").toString());
                            model.setFont(object.optInt("font"));
                            JSONArray array = object.getJSONArray("media");
//                            List<MediaModel> mediaModels = new ArrayList<>();
//                            for (int j = 0; j < array.length(); j++) {
                            //TODO Nouman
//                            }
                            model.setContentMedia(array.toString());
                            list.add(model);
                        }
                        EventBus.getDefault().post(list);
                    } else {
                        EventBus.getDefault().post(new ErrorModel("0", reJsonObject.getString("message")));
                    }
                } catch (Exception e) {
                    EventBus.getDefault().post(new ErrorModel("0", e.toString()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppController.getInstance().cancelPendingRequests("getChaptersTopic");
                try {
                    NetworkController.this.onErrorResponse(error);
                } catch (Exception e) {
                    EventBus.getDefault().post(new ErrorModel("0", e.toString()));
                }
            }
        }) {
            @Override
            public Request.Priority getPriority() {
                return priority;
            }
        };
        setTimeOut(request, 90000);
        AppController.getInstance().addToRequestQueue(request, "getChaptersTopic");
    }

    public void getChaptersExercise(long chapterId) {
        String url = UrlConstants.baseUrl + "chapter/exercise/" + chapterId;
        final Request.Priority priority = Request.Priority.IMMEDIATE;
        StringRequest request = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String utfString = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    JSONObject reJsonObject = new JSONObject(utfString);
                    String status = reJsonObject.getString("status");
                    if (status.equalsIgnoreCase("success") ||
                            status.equalsIgnoreCase("1")) {
                        JSONObject data = reJsonObject.getJSONObject("data");
                        JSONArray jsonArray = data.getJSONArray("word_meaning");
                        List<CardsModel> wordMeaning = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            CardsModel model = new CardsModel();
                            model.setId(i + 1);
                            model.setWords(object.getString("word"));
                            model.setMeanings(object.getString("meaning"));
                            model.setDetail(object.getString("details"));
                            model.setFontQuestion(object.optInt("word_font"));
                            model.setFontAnswer(object.optInt("meaning_font"));
                            model.setFontDetail(object.optInt("details_font"));
                            wordMeaning.add(model);
                        }
                        SessionController.getInstance().setWordMeaningList(wordMeaning);

                        jsonArray = data.getJSONArray("flah_cards");
                        List<CardsModel> flashCards = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            CardsModel model = new CardsModel();
                            model.setId(i + 1);
                            model.setWords(object.getString("word_or_question"));
                            model.setMeanings(object.getString("meaning_or_answer"));
                            model.setDetail(object.optString("details"));
                            model.setFontQuestion(object.optInt("word_question_font"));
                            model.setFontAnswer(object.optInt("meaning_answer_font"));
                            flashCards.add(model);
                        }
                        SessionController.getInstance().setFashCardList(flashCards);
                        if (data.has("question_answers")) {
                            jsonArray = data.getJSONArray("question_answers");
                            List<ContentList> qaList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                ContentList content = new ContentList();
                                content.setArabic(object.getString("question"));
                                content.setUrdu(object.getString("answer"));
                                content.setFontQuestion(object.optInt("question_font"));
                                content.setFontAnswer(object.optInt("answer_font"));
                                qaList.add(content);
                            }
                            SessionController.getInstance().setQAList(qaList);
                        }

                        EventBus.getDefault().post(status);
                    } else {
                        EventBus.getDefault().post(new ErrorModel("0", reJsonObject.getString("message")));
                    }
                } catch (Exception e) {
                    EventBus.getDefault().post(new ErrorModel("0", e.toString()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppController.getInstance().cancelPendingRequests("getChaptersExercise");
                try {
                    NetworkController.this.onErrorResponse(error);
                } catch (Exception e) {
                    EventBus.getDefault().post(new ErrorModel("0", e.toString()));
                }
            }
        }) {
            @Override
            public Request.Priority getPriority() {
                return priority;
            }
        };
        setTimeOut(request, 90000);
        AppController.getInstance().addToRequestQueue(request, "getChaptersExercise");
    }

    public void onErrorResponse(VolleyError error) {
        if (error == null) {
            EventBus.getDefault().post(new ErrorModel("0", AppController.getInstance().getString(R.string.NetworkError)));
            return;
        }
        try {
            int statusCode = error.networkResponse.statusCode;
            NetworkResponse response = error.networkResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (error instanceof NetworkError) {
            EventBus.getDefault().post(new ErrorModel("0", AppController.getInstance().getString(R.string.NetworkError)));
        } else if (error instanceof ServerError) {
            EventBus.getDefault().post(new ErrorModel("0", AppController.getInstance().getString(R.string.ServerError)));
        } else if (error instanceof AuthFailureError) {
            EventBus.getDefault().post(new ErrorModel("0", AppController.getInstance().getString(R.string.AuthFailureError)));
        } else if (error instanceof ParseError) {
            EventBus.getDefault().post(new ErrorModel("0", AppController.getInstance().getString(R.string.ParseError)));
        } else if (error instanceof NoConnectionError) {
            EventBus.getDefault().post(new ErrorModel("0", AppController.getInstance().getString(R.string.NO_INTERNET_CONNECTIVITY)));
        } else if (error instanceof TimeoutError) {
            EventBus.getDefault().post(new ErrorModel("0", AppController.getInstance().getString(R.string.TimeoutError)));
        } else {
            EventBus.getDefault().post(new ErrorModel("0", AppController.getInstance().getString(R.string.NetworkError)));
        }
    }

    public void setTimeOut(JsonObjectRequest jsonObjReq, int timeInMiliSeconds) {
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                timeInMiliSeconds,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void setTimeOut(StringRequest stringRequest, int timeInMiliSeconds) {
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeInMiliSeconds,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
