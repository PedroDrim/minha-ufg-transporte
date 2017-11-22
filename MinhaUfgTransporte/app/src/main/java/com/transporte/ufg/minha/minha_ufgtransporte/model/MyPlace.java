package com.transporte.ufg.minha.minha_ufgtransporte.model;

import com.google.firebase.database.Exclude;

/**
 * Created by pedro on 21/11/17.
 */

public class MyPlace {

    @Exclude
    private String pushKey;

    private String identificador;
    private double latitude;
    private double longitude;

    public MyPlace(){}

    public MyPlace(String identificador, double latitude, double longitude) {
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

    public String getPushKey() {
        return pushKey;
    }

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
