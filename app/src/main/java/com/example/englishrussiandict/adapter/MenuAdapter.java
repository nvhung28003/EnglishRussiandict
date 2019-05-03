package com.example.englishrussiandict.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.englishrussiandict.R;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> listfile;
    private LayoutInflater inflater;
    private Onitemclicklistener onitemclicklistener;

    public MenuAdapter(Context context, List<String> listfile) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listfile = listfile;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = inflater.inflate(R.layout.item_menu, null);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final String namefile = listfile.get(i);

        ((Viewholder) viewHolder).mTextViewMenu.setText(namefile);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemclicklistener.OnitemClicked(namefile);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listfile.size();
    }

    public void setOnitemclicklistener(Onitemclicklistener onitemclicklistener) {
        this.onitemclicklistener = onitemclicklistener;
    }


    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView mTextViewMenu;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            mTextViewMenu = itemView.findViewById(R.id.txt_menu);
        }
    }
    public interface Onitemclicklistener {
        void OnitemClicked(String namefile);
    }
}
