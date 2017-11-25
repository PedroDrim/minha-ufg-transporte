package com.transporte.ufg.minha.minha_ufgtransporte.model;

import android.view.View;

/**
 * Interface para implentar uma acao ao clicar em um card do recyclerLayout
 * @see com.transporte.ufg.minha.minha_ufgtransporte.presenter.MyPlaceClickListener
 */
public interface RecyclerClickListener {

    /**
     * Acao de click da View
     * @param position posicao da View clicada
     * @param v View clicada
     * @see com.transporte.ufg.minha.minha_ufgtransporte.presenter.MyPlaceClickListener
     */
    void onItemClick(int position, View v);
}
