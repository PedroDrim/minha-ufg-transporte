package com.transporte.ufg.minha.minha_ufgtransporte.view.component;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;
import com.transporte.ufg.minha.minha_ufgtransporte.model.RecyclerClickListener;

/**
 * Created by pedro on 23/11/17.
 */

public class MyPlaceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerClickListener recyclerClickListener;
    private TextView identificador;
    private TextView latitude;
    private TextView longitude;
    private StringBuilder builder;

    public MyPlaceHolder(RecyclerClickListener recyclerClickListener, View itemView) {
        super(itemView);

        this.recyclerClickListener = recyclerClickListener;

        this.identificador = itemView.findViewById(R.id.identificador);
        this.latitude = itemView.findViewById(R.id.latitude);
        this.longitude = itemView.findViewById(R.id.longitude);

        itemView.setOnClickListener(this);
    }

    public void setCard(MyPlace myPlace) {
        this.identificador.setText(myPlace.getIdentificador());

        this.builder = new StringBuilder();
        this.builder.append("Latitude: ").append(myPlace.getLatitude());
        this.latitude.setText(this.builder.toString());

        this.builder = new StringBuilder();
        this.longitude.setText(this.builder.toString());
        this.builder.append("Longitude: ").append(myPlace.getLongitude());

    }

    @Override
    public void onClick(View v) {
        this.recyclerClickListener.onItemClick(getAdapterPosition(), v);
    }
}
