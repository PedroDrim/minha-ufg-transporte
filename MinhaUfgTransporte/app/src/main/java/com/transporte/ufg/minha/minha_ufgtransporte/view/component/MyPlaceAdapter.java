package com.transporte.ufg.minha.minha_ufgtransporte.view.component;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que gerencia a insercao e remocao dinamica dentro de um LinearLayout
 * @see MyPlaceAdapter
 */
public class MyPlaceAdapter extends RecyclerView.Adapter<MyPlaceHolder> {

    /**
     * Lista de locais
     */
    private List<MyPlace> myPlaceList;

    /**
     * Construtor que inicializa a lista de locais
     */
    public MyPlaceAdapter() {
        this.myPlaceList = new ArrayList<>();
    }

    /**
     * Adiciona novo local ao LinearLayout
     * @param dataObj Local a ser adicionado
     * @param index Indice do local referente
     */
    public void addItem(MyPlace dataObj, int index) {
        if(!this.myPlaceList.contains(dataObj)) {
            this.myPlaceList.add(index, dataObj);
            notifyItemInserted(index);
        }
    }

    /**
     * Remove um local do LinearLayout
     * @param index Indice do local a ser removido
     */
    public void deleteItem(int index) {
        this.myPlaceList.remove(index);
        notifyItemRemoved(index);
    }

    /**
     * Limpa a lista de locais do LinearLayout
     */
    public void clearList(){
        for(int index = 0; index < this.getItemCount(); index++){
            this.deleteItem(index);
        }
    }

    @Override
    public MyPlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_row, parent, false);

        MyPlaceHolder myPlaceHolder = new MyPlaceHolder(this.myPlaceList, view);
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

}