package com.transporte.ufg.minha.minha_ufgtransporte.view.component;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;
import com.transporte.ufg.minha.minha_ufgtransporte.model.RecyclerClickListener;
import com.transporte.ufg.minha.minha_ufgtransporte.model.RemoveButtonClickListener;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.MyPlaceClickListener;

import java.util.List;

/**
 * Classe que converte uma Lista de locais para um adapter
 * @see MyPlaceAdapter
 */
public class MyPlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    /**
     * Interface responsavel por implementar a acao de clique em um card
     */
    private RecyclerClickListener recyclerClickListener;

    /**
     * TextView referente ao identificador do local
     */
    private TextView identificador;

    /**
     * TextView referente a latitude do local
     */
    private TextView latitude;

    /**
     * TextView referente a longitude do local
     */
    private TextView longitude;

    /**
     * StringBuilder para facilitar a vida da gente no java.
     */
    private StringBuilder builder;

    /**
     * Construtor que inicializa os cards
     * @param myPlaceList Lista de locais
     * @param itemView View do objeto a ser gerado
     */
    public MyPlaceHolder(List<MyPlace> myPlaceList, View itemView) {
        super(itemView);

        this.recyclerClickListener = new MyPlaceClickListener(myPlaceList);

        this.identificador = itemView.findViewById(R.id.identificador);
        this.latitude = itemView.findViewById(R.id.latitude);
        this.longitude = itemView.findViewById(R.id.longitude);

        itemView.setOnClickListener(this);
    }

    /**
     * Converte um local para um elemento da View
     * @param myPlace Local a ser inserido na View
     */
    public void setCard(MyPlace myPlace) {

        this.identificador.setText(myPlace.getIdentificador());

        this.builder = new StringBuilder();
        this.builder.append("Latitude: ").append(myPlace.getLatitude());
        this.latitude.setText(this.builder.toString());

        this.builder = new StringBuilder();
        this.builder.append("Longitude: ").append(myPlace.getLongitude());
        this.longitude.setText(this.builder.toString());
    }

    @Override
    public void onClick(View v) {
        this.recyclerClickListener.onItemClick(getAdapterPosition(), v);
    }
}
