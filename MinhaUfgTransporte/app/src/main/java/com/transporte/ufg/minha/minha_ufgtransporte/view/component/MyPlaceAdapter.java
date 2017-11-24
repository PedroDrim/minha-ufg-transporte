package com.transporte.ufg.minha.minha_ufgtransporte.view.component;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;
import com.transporte.ufg.minha.minha_ufgtransporte.model.RecyclerClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedro on 23/11/17.
 */

public class MyPlaceAdapter extends RecyclerView.Adapter<MyPlaceHolder> {

    private List<MyPlace> myPlaceList;
    private RecyclerClickListener clickListener;

    public MyPlaceAdapter() {
        this.myPlaceList = new ArrayList<>();
    }

    public void setOnItemClickListener(RecyclerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public MyPlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_row, parent, false);

        MyPlaceHolder myPlaceHolder = new MyPlaceHolder(this.clickListener, view);
        return myPlaceHolder;
    }

    @Override
    public void onBindViewHolder(MyPlaceHolder holder, int position) {
        holder.setCard(this.myPlaceList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.myPlaceList.size();
    }

    public void addItem(MyPlace dataObj, int index) {
        if(!this.myPlaceList.contains(dataObj)) {
            this.myPlaceList.add(index, dataObj);
            notifyItemInserted(index);
        }
    }

    public void deleteItem(int index) {
        this.myPlaceList.remove(index);
        notifyItemRemoved(index);
    }

    public void clearList(){
        for(int index = 0; index < this.getItemCount(); index++){
            this.deleteItem(index);
        }
    }

}