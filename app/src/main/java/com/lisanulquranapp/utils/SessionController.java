package com.lisanulquranapp.utils;

import com.lisanulquranapp.models.CardsModel;
import com.lisanulquranapp.models.ContentList;
import com.lisanulquranapp.models.ContentModel;

import java.util.List;

/**
 * @author Nauman Ashraf.
 */
public class SessionController {

    private static SessionController mInstance;

    private String language = "en";

    public static SessionController getInstance() {
        if (mInstance == null) {
            mInstance = new SessionController();
        }
        return mInstance;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    private ContentModel contentModel;
    private List<CardsModel> wordMeaningList;
    private List<CardsModel> fashCardList;
    private List<ContentList> QAList;

    public List<ContentList> getQAList() {
        return QAList;
    }

    public void setQAList(List<ContentList> QAList) {
        this.QAList = QAList;
    }

    public List<CardsModel> getFashCardList() {
        return fashCardList;
    }

    public void setFashCardList(List<CardsModel> fashCardList) {
        this.fashCardList = fashCardList;
    }

    public List<CardsModel> getWordMeaningList() {
        return wordMeaningList;
    }

    public void setWordMeaningList(List<CardsModel> wordMeaningList) {
        this.wordMeaningList = wordMeaningList;
    }

    public ContentModel getContentModel() {
        return contentModel;
    }

    public void setContentModel(ContentModel contentModel) {
        this.contentModel = contentModel;
    }

}
