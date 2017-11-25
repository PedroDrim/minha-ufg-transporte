package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.Flag;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;
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

    /**
     * Adiciona um novo local ao banco
     * @param view View referente ao FloatingButton do .xml
     */
    public void addMyPlace(View view){
        OpenActivity.openCrudMapActivity(view.getContext(), Flag.INSERT);
    }

    /**
     * Inicializa os componentes da activity
     */
    private void loadActivityComponents(){

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
