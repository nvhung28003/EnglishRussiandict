package com.example.englishrussiandict.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishrussiandict.R;
import com.example.englishrussiandict.entity.Dictionary;
import com.example.englishrussiandict.view.fragment.SearchFragment;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Dictionary> dictionaryList;
    private OnitemClickListener onitemClickListener;

    public HistoryAdapter(Context context, List<Dictionary> dictionaryList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dictionaryList = dictionaryList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = inflater.inflate(R.layout.item_history,null);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final Dictionary dictionary = dictionaryList.get(i);
        ( (Viewholder) viewHolder).mTextViewHistory.setText(dictionary.getWord());

        ( (Viewholder) viewHolder).mImageViewDeleteHistory.setOnClickListener(new View.OnClickListener() {
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
    public void setOnitemClickListener(OnitemClickListener onitemClickListener)
    {
        this.onitemClickListener = onitemClickListener;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView mTextViewHistory;
        private ImageView mImageViewDeleteHistory;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            mTextViewHistory = itemView.findViewById(R.id.txthistory);
            mImageViewDeleteHistory = itemView.findViewById(R.id.imv_deletehistory);
        }
    }

    public interface OnitemClickListener {
        void OnitemClicked(Dictionary dictionary);
        void OnitemDeleteClick(Dictionary dictionary);
    }
}
