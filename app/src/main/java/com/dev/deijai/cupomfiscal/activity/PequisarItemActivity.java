package com.dev.deijai.cupomfiscal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.deijai.cupomfiscal.R;
import com.dev.deijai.cupomfiscal.adapter.AdapterListaItem;
import com.dev.deijai.cupomfiscal.model.CupomFiscal;
import com.dev.deijai.cupomfiscal.webconfig.APIClient;
import com.dev.deijai.cupomfiscal.webconfig.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PequisarItemActivity extends AppCompatActivity {

    private TextView cupom;
    private TextView caixa;
    private EditText editTextPesquisar;
    private List<CupomFiscal> cupomFiscalList;
    private APIInterface apiInterface;
    RecyclerView recyclerView;
    private List<CupomFiscal> lista = new ArrayList<>();
    private Button buttonFinalizar;
    private List<String> string  = new ArrayList<>();
    private  String nCaixa = null;
    private  String nCupom = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pesquisar_item);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        cupom = findViewById(R.id.textViewNCupom);
        caixa = findViewById(R.id.textViewNCaixa);
        editTextPesquisar = findViewById(R.id.editTextPesquisar);
        apiInterface  = APIClient.getClient().create(APIInterface.class);
        recyclerView = findViewById(R.id.recyclerViewId);
        buttonFinalizar = findViewById(R.id.buttonFinalizarId);



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        boolean leitor = bundle.getBoolean("leitor");

        if (leitor){
            nCaixa = bundle.get("caixa").toString();
            nCupom = bundle.get("cupom").toString();

            cupom.setText("CUPOM:  "+ nCupom);
            caixa.setText("CAIXA:  "+ nCaixa);
        } else {
            nCaixa = bundle.get("numCaixa").toString();
            nCupom = bundle.get("numCupom").toString();
            cupom.setText("CUPOM:  "+ nCupom);
            caixa.setText("CAIXA:  "+ nCaixa);
        }





        editTextPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String codigoBarra = editTextPesquisar.getText().toString();

                if (!codigoBarra.isEmpty()){

                    CupomFiscal cupomFiscal = new CupomFiscal();
                    cupomFiscalList = new ArrayList<>();

                    cupomFiscal.setNumcaixa(Integer.parseInt(nCaixa));
                    cupomFiscal.setNumnota(nCupom);
                    cupomFiscal.setCodauxiliar(codigoBarra);

                    cupomFiscalList.add(cupomFiscal);


                    Call<CupomFiscal> call = apiInterface.pegarItenCodAuxiliar(cupomFiscal);


                    call.enqueue(new Callback<CupomFiscal>() {
                        @Override
                        public void onResponse(Call<CupomFiscal> call, Response<CupomFiscal> response) {

                            CupomFiscal c = response.body();



                                lista.add(c);
                                //configurar adapter
                                AdapterListaItem  adapterListaItem= new AdapterListaItem( lista );
                                //configurar recyclerView
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setHasFixedSize( true );
                                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
                                recyclerView.setAdapter( adapterListaItem);
                                editTextPesquisar.setText("");



                        }

                        @Override
                        public void onFailure(Call<CupomFiscal> call, Throwable t) {
                            msg("Produto não encotrado no Cupom Fiscal.");
                            editTextPesquisar.setText("");
                        }
                    });




                }


            }
        });


        buttonFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cupom Checkado com sucesso!", Toast.LENGTH_LONG).show();

                if (!nCaixa.isEmpty() && !nCupom.isEmpty()){

                    CupomFiscal cupomFiscal = new CupomFiscal();
                    cupomFiscal.setNumnota(nCupom);
                    cupomFiscal.setNumcaixa(Integer.parseInt(nCaixa));
                    cupomFiscal.setCodfunc(MainActivity.codfunc);

                    string.add("numcaixa:"+nCaixa);
                    string.add("numcupom:"+nCupom);

                    try {
                        Call<Boolean> call = apiInterface.alterarStatus(cupomFiscal);
                        call.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                boolean resultado =  response.body();

                                if (resultado){
                                    finish();
                                } else {
                                    msg("Cupom não finalizado!");
                                }

                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                msg("Falha na consulta!");

                            }
                        });
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_LONG).show();
                    }




                }
            }
        });


        editTextPesquisar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });


       // Log.i("CUPOM", cupomFiscal.getCodauxiliar());
       // Log.i("CUPOM", cupomFiscal.getNumcaixa().toString());
       // Log.i("CUPOM", cupomFiscal.getNumnota());
    }

    public void msg(String msg){

        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        View toastView = toast.getView(); // This'll return the default View of the Toast.

        /* And now you can get the TextView of the default View of the Toast. */
        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
        toastMessage.setTextSize(20);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setCompoundDrawablePadding(16);
        toastView.setBackgroundColor(Color.RED);
        toast.show();
    }
}
