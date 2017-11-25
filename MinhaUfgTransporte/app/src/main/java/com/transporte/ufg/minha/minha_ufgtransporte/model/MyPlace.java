package com.transporte.ufg.minha.minha_ufgtransporte.model;

import com.google.firebase.database.Exclude;

/**
 * Classe referente a um local pre-definido pelo usuario
 */
public class MyPlace {

    /**
     * Chave de no do firebase database
     */
    @Exclude
    private String pushKey;

    /**
     * Identificador do local
     */
    private String identificador;

    /**
     * Latitude do local
     */
    private double latitude;

    /**
     * Longitude do local
     */
    private double longitude;

    /**
     * Construtor para serializacao do dado retornado pelo firebase
     */
    public MyPlace(){}

    /**
     * Construtor para a inicializacao de um novo local
     * @param identificador identificador do novo local
     * @param latitude latitude do novo local
     * @param longitude longitude do novo local
     */
    public MyPlace(String identificador, double latitude, double longitude) {
        this.identificador = identificador;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Obtem o identificador do local
     * @return identificador do local
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Obtem a latitude do local
     * @return latitude do local
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Obtem a longitude do local
     * @return longitude do local
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Obtem a chave de no do objeto
     * @return chave de no do objeto
     */
    public String getPushKey() {
        return pushKey;
    }

    /**
     * Define a chave de no do objeto
     * @param pushKey chave de no do objeto no firebase
     */
    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyPlace myPlace = (MyPlace) o;

        if (Double.compare(myPlace.latitude, latitude) != 0) return false;
        if (Double.compare(myPlace.longitude, longitude) != 0) return false;
        return identificador.equals(myPlace.identificador);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = identificador.hashCode();
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
