package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.Flag;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.MyPlaceDAO;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.OpenActivity;
import com.transporte.ufg.minha.minha_ufgtransporte.view.component.MyPlaceAdapter;
import com.transporte.ufg.minha.minha_ufgtransporte.view.component.MyPlaceRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Set;

/**
 * Activity para listagem do locais obtidos no banco de dados
 */
public class PlaceListActivity extends AppCompatActivity {

    /**
     * Flag para gerenciar os componentes da pagina
     */
    private boolean loaded;

    /**
     * Instancia do SwipeRefreshLayout
     */
    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Instancia do RecyclerView
     */
    private RecyclerView mRecyclerView;

    /**
     * Objeto que gerencia os eventos de recarga da pagina
     * @see MyPlaceRefreshListener
     */
    private MyPlaceRefreshListener myPlaceRefreshListener;

    /**
     * Objeto responsavel por converter os Locais obtidos no banco de dados em elementos da View
     */
    private MyPlaceAdapter mAdapter;

    private MyPlaceDAO myPlaceDAO;

    /**
     * Construtor que inicializa a flag de gerenciamento
     */
    public PlaceListActivity(){
        this.loaded = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_list);

        if(!loaded) {
            this.loadActivityComponents();
            this.loaded = true;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        this.swipeRefreshLayout.setRefreshing(false);
        super.onStop();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        this.loadActivityComponents();
    }

    /**
     * Evento que sera realizado quando um Set de locais for Inscrito
     * @param setMyPlace Set dos locais obtidos do banco de dados
     */
    @Subscribe
    public void onEvent(Set<MyPlace> setMyPlace){

        this.mAdapter.clearList();

        int index = 0;
        for(MyPlace myPlace: setMyPlace){
            this.mAdapter.addItem(myPlace, index);
            index++;
        }

        this.swipeRefreshLayout.setRefreshing(false);
    }

    public void removeMyPlace(View view){
        RelativeLayout layout = (RelativeLayout) view.getParent();
        MyPlace myPlace = this.getMyPlaceByView(layout);
        this.myPlaceDAO.removeMyPlace(myPlace.getPushKey());
        this.mAdapter.deleteItem(myPlace);
    }

    /**
     * Adiciona um novo local ao banco
     * @param view View referente ao FloatingButton do .xml
     */
    public void addMyPlace(View view){
        OpenActivity.openCrudMapActivity(view.getContext(), Flag.INSERT);
    }

    private MyPlace getMyPlaceByView(RelativeLayout relativeLayout){
        TextView viewIdentificador = (TextView) relativeLayout.getChildAt(0);
        TextView viewLatitude = (TextView) relativeLayout.getChildAt(1);
        TextView viewLongitude = (TextView) relativeLayout.getChildAt(2);

        String identificador = viewIdentificador.getText().toString();

        String textLatitude = viewLatitude.getText().toString();
        textLatitude = textLatitude.split(" ")[1];

        String textLongitude = viewLongitude.getText().toString();
        textLongitude = textLongitude.split(" ")[1];

        double latitude = Double.valueOf(textLatitude);
        double longitude = Double.valueOf(textLongitude);

        MyPlace myPlace = new MyPlace(identificador, latitude, longitude);

        String pushKey = this.mAdapter.getPushKey(myPlace);
        myPlace.setPushKey(pushKey);

        return(myPlace);
    }

    /**
     * Inicializa os componentes da activity
     */
    private void loadActivityComponents(){

        this.myPlaceDAO = new MyPlaceDAO(this);
        this.myPlaceRefreshListener = new MyPlaceRefreshListener(this);

        this.swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        this.swipeRefreshLayout.setRefreshing(true);
        this.swipeRefreshLayout.setOnRefreshListener(this.myPlaceRefreshListener);

        this.mAdapter = new MyPlaceAdapter();

        this.mRecyclerView = findViewById(R.id.myPlace_recycler_view);
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setLayoutManager( new LinearLayoutManager(this) );
        this.mRecyclerView.setAdapter(this.mAdapter);
    }
}
