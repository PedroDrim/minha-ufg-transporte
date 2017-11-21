package com.transporte.ufg.minha.minha_ufgtransporte.model;

/**
 * Created by pedro on 21/11/17.
 */

public class Local {

    private String identificador;
    private double latitude;
    private double longitude;

    public Local(){}

    public Local(String identificador, double latitude, double longitude) {
        this.identificador = identificador;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIdentificador() {
        return identificador;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
