package com.example.englishrussiandict.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.englishrussiandict.R;
import com.example.englishrussiandict.entity.MyObjDictionary;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<MyObjDictionary> dictionaryList;
    private OnitemClickListener onitemClickListener;

    public HistoryAdapter(Context context, List<MyObjDictionary> dictionaryList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dictionaryList = dictionaryList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = inflater.inflate(R.layout.item_history, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MyObjDictionary dictionary = dictionaryList.get(i);
        ((ViewHolder) viewHolder).mTextViewHistory.setText(dictionary.getWord());

        ((ViewHolder) viewHolder).mImageViewDeleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemClickListener.OnitemDeleteClick(dictionary);
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemClickListener.OnitemClicked(dictionary);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dictionaryList.size();
    }

    public void setOnitemClickListener(OnitemClickListener onitemClickListener) {
        this.onitemClickListener = onitemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewHistory;
        private ImageView mImageViewDeleteHistory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewHistory = itemView.findViewById(R.id.txthistory);
            mImageViewDeleteHistory = itemView.findViewById(R.id.imv_deletehistory);
        }
    }

    public interface OnitemClickListener {
        void OnitemClicked(MyObjDictionary dictionary);

        void OnitemDeleteClick(MyObjDictionary dictionary);
    }
}
