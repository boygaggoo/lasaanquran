package com.ctandem.lasaanulquran.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ctandem.lasaanulquran.R;
import com.ctandem.lasaanulquran.models.CardsModel;

import java.util.List;

public class FlashCardAdapter extends BaseAdapter {
    private List<CardsModel> list;
    private Context context;

    public FlashCardAdapter(List<CardsModel> modelList, Context context) {
        list = modelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    @Override
//    public View getView(int position, View view, ViewGroup viewGroup) {
//        View v = view;
//        if (v == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            v = inflater.inflate(R.layout.flash_card_item, viewGroup, false);
//        }
//        destHolder holder = new destHolder(v);
////        holder.totalPage.setText(String.valueOf(list.size()));
////        holder.currentPage.setText(String.valueOf(position + 1));
//        holder.wordText.setText(list.get(position).getWords());
////        holder.meaningText.setText(list.get(position).getMeanings());
////        holder.backText.setText(list.get(position).getDetail());
//        holder.backText.setText(list.get(position).getMeanings());
//
//        List<CardsModel> cardsModels = new ArrayList<>();
//
//        return v;
//    }

//    class destHolder {
//        TextView totalPage;
//        TextView currentPage;
//        TextView backText;
//        TextView wordText;
////        TextView meaningText;
//
//        private destHolder(View view) {
////            totalPage = view.findViewById(R.id.total_page);
////            currentPage = view.findViewById(R.id.current_page);
//            backText = view.findViewById(R.id.back_text);
//            wordText = view.findViewById(R.id.word_tv);
////            meaningText = view.findViewById(R.id.meaning_tv);
//
//            wordText.setMovementMethod(new ScrollingMovementMethod());
////            meaningText.setMovementMethod(new ScrollingMovementMethod());
//            backText.setMovementMethod(new ScrollingMovementMethod());
//        }
//    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.flash_card_meaning_item_flash, viewGroup, false);
        }
        final destHolder holder = new destHolder(v);
        holder.totalPage.setText(String.valueOf(list.size()));
        holder.currentPage.setText(String.valueOf(position + 1));


        Typeface typefaceQuestion;
        if(list.get(position).getFontQuestion()== 0)
            typefaceQuestion = Typeface.createFromAsset(context.getAssets(), "font/jameel_noori.ttf");
        else
            typefaceQuestion = Typeface.createFromAsset(context.getAssets(), "font/muhammadi_quranic.ttf");

        Typeface typefaceAnswer;
        if(list.get(position).getFontAnswer() == 0)
            typefaceAnswer = Typeface.createFromAsset(context.getAssets(), "font/jameel_noori.ttf");
        else
            typefaceAnswer = Typeface.createFromAsset(context.getAssets(), "font/muhammadi_quranic.ttf");


//        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "font/jameel_noori.ttf");
        holder.wordText.setTypeface(typefaceQuestion);
        holder.wordText.setText(list.get(position).getWords());

//        Typeface custom_font_muhammadi = Typeface.createFromAsset(context.getAssets(), "font/muhammadi_quranic.ttf");
        holder.meaningText.setTypeface(typefaceAnswer);
        holder.meaningText.setText(list.get(position).getMeanings());
        holder.meaningText.setVisibility(View.GONE);
//        holder.backText.setText(list.get(position).getDetail());
//        holder.backText.setText(list.get(position).getMeanings());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.meaningText.getVisibility() == View.GONE){
                    holder.meaningText.setVisibility(View.VISIBLE);
                } else {
                    holder.meaningText.setVisibility(View.GONE);
                }
            }
        });
        return v;
    }

    class destHolder {
        TextView totalPage;
        TextView currentPage;
        //        TextView backText;
        TextView wordText;
        TextView meaningText;
        CardView card;

        private destHolder(View view) {
            totalPage = view.findViewById(R.id.total_page);
            currentPage = view.findViewById(R.id.current_page);
//            backText = view.findViewById(R.id.back_text);
            wordText = view.findViewById(R.id.word_tv);
            meaningText = view.findViewById(R.id.meaning_tv);
            card = view.findViewById(R.id.card);

//            wordText.setMovementMethod(new ScrollingMovementMethod());
//            meaningText.setMovementMethod(new ScrollingMovementMethod());
//            backText.setMovementMethod(new ScrollingMovementMethod());
        }
    }

}
