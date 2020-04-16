package com.dev.deijai.cupomfiscal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.dev.deijai.cupomfiscal.R;
import com.dev.deijai.cupomfiscal.adapter.AdapterListaItem;
import com.dev.deijai.cupomfiscal.model.CupomFiscal;

import java.util.List;

public class ListaItemActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    List<CupomFiscal> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_item);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.recyclerViewId);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        lista = (List<CupomFiscal>) bundle.get("lista");





        //configurar adapter
        AdapterListaItem  adapterListaItem= new AdapterListaItem( lista );


        //configurar recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize( true );
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter( adapterListaItem);


    }
}
