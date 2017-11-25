package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.transporte.ufg.minha.minha_ufgtransporte.R;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_locais, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.crud_locais:
                //add the function to perform here
                this.abrirActivityMeusLocais();
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }

    private void abrirActivityMeusLocais(){
        Intent intentAbrirTelaPrincipal = new Intent(
                MapActivity.this,
                PlaceListActivity.class
        );
        startActivity(intentAbrirTelaPrincipal);
    }
}
