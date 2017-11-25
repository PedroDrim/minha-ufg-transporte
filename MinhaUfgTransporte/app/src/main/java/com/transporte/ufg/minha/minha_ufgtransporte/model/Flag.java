package com.transporte.ufg.minha.minha_ufgtransporte.model;

import com.transporte.ufg.minha.minha_ufgtransporte.R;

/**
 * Enumerador para definir a acao de uma activiry
 * @see com.transporte.ufg.minha.minha_ufgtransporte.view.CrudMapActivity
 */
public enum Flag {

    INSERT(R.string.enum_insert), UPDATE(R.string.enum_update);

    /**
     * Valor da flag
     */
    public int valor;

    /**
     * Retorna o valor da flag
     * @param valor valor da flag
     */
    Flag(int valor){
        this.valor = valor;
    }
}
