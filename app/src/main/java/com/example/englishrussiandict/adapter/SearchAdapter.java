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
import com.example.englishrussiandict.view.activity.MainActivity;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<MyObjDictionary> dictionaryList;
    private OnitemClickListener onitemClickListener;
    private boolean checkFavorite;
    public SearchAdapter(Context context, List<MyObjDictionary> dictionaryList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dictionaryList = dictionaryList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = inflater.inflate(R.layout.item_search, viewGroup, false);

        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        final MyObjDictionary dictionary = dictionaryList.get(i);

        ((Viewholder) viewHolder).txt_wordsearch.setText(dictionary.getWord());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemClickListener.OnitemClicked(dictionary);
            }
        });
        ((Viewholder) viewHolder).imv_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFavorite == true)
                {
                    MainActivity.database_favorites.addfavorite(dictionary);
                    ((Viewholder) viewHolder).imv_star.setBackgroundResource(R.drawable.ic_favorited);
                    checkFavorite = false;
                }
                else {
                    MainActivity.database_favorites.deletedata(dictionary.get_id());
                    ((Viewholder) viewHolder).imv_star.setBackgroundResource(R.drawable.ic_not_favorite);
                    checkFavorite = true;
                }
            }
        });
    }

    public void setOnitemClickListener(OnitemClickListener onitemClickListener) {
        this.onitemClickListener = onitemClickListener;
    }
    @Override
    public int getItemCount() {
        return dictionaryList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView txt_wordsearch;
        private ImageView imv_star;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txt_wordsearch = itemView.findViewById(R.id.txt_wordsearch);
            imv_star = itemView.findViewById(R.id.imv_star);
        }
    }

    public interface OnitemClickListener {
        void OnitemClicked(MyObjDictionary dictionary);
        void Onitemchoosefavorites(MyObjDictionary dictionary);
    }

}
