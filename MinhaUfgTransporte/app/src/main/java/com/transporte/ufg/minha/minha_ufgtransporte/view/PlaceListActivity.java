package com.transporte.ufg.minha.minha_ufgtransporte.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.transporte.ufg.minha.minha_ufgtransporte.R;
import com.transporte.ufg.minha.minha_ufgtransporte.model.MyPlace;
import com.transporte.ufg.minha.minha_ufgtransporte.presenter.MyPlaceClickListener;
import com.transporte.ufg.minha.minha_ufgtransporte.view.component.MyPlaceAdapter;
import com.transporte.ufg.minha.minha_ufgtransporte.view.component.MyPlaceRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Set;

public class PlaceListActivity extends AppCompatActivity {

    private String tag = "MINHAUFG";
    private boolean loaded;

    private SwipeRefreshLayout swipeRefreshLayout;
    private MyPlaceRefreshListener myPlaceRefreshListener;
    private RecyclerView mRecyclerView;
    private MyPlaceAdapter mAdapter;

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

    public void addMyPlace(View view){
        Log.d(tag, "Adicionando novo MyPlace");
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

    private void loadActivityComponents(){

        this.myPlaceRefreshListener = new MyPlaceRefreshListener(this);

        this.swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        this.swipeRefreshLayout.setRefreshing(true);
        this.swipeRefreshLayout.setOnRefreshListener(this.myPlaceRefreshListener);

        this.mAdapter = new MyPlaceAdapter();
        this.mAdapter.setOnItemClickListener( new MyPlaceClickListener());

        this.mRecyclerView = findViewById(R.id.myPlace_recycler_view);
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setLayoutManager( new LinearLayoutManager(this) );
        this.mRecyclerView.setAdapter(this.mAdapter);
    }
}
