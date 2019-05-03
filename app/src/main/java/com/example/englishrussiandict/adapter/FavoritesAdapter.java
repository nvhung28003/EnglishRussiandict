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

public class FavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<MyObjDictionary> dictionaryList;
    private OnitemClickListener onitemClickListener;

    public FavoritesAdapter(Context context, List<MyObjDictionary> dictionaryList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dictionaryList = dictionaryList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = inflater.inflate(R.layout.item_favorites, null);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        final MyObjDictionary dictionary = dictionaryList.get(i);
        ((Viewholder) viewHolder).mTextViewFavorites.setText(dictionary.getWord());

        ((Viewholder) viewHolder).mImageViewDeleteFavorites.setOnClickListener(new View.OnClickListener() {
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

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView mTextViewFavorites;
        private ImageView mImageViewDeleteFavorites;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            mTextViewFavorites = itemView.findViewById(R.id.txtfavorites);
            mImageViewDeleteFavorites = itemView.findViewById(R.id.imv_deletefavorites);
        }
    }

    public interface OnitemClickListener {
        void OnitemClicked(MyObjDictionary dictionary);

        void OnitemDeleteClick(MyObjDictionary dictionary);
    }
}
