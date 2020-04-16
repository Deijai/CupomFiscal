package com.dev.deijai.cupomfiscal.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.deijai.cupomfiscal.R;
import com.dev.deijai.cupomfiscal.adapter.AdapterListaItem;
import com.dev.deijai.cupomfiscal.model.CupomFiscal;
import com.dev.deijai.cupomfiscal.webconfig.APIClient;
import com.dev.deijai.cupomfiscal.webconfig.APIInterface;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    private ImageView imageButtonQRCode;
    private Button buttonBuscarPorCupom;
    private APIInterface apiInterface;
    private TextView numCaixa;
    private TextView numCupom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();

        imageButtonQRCode = findViewById(R.id.imageViewBtnQRCode);
        buttonBuscarPorCupom = findViewById(R.id.buttonBtnBuscarPorCupom);
        apiInterface  = APIClient.getClient().create(APIInterface.class);

        imageButtonQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });


        buttonBuscarPorCupom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamarModal();
            }
        });
    }




    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null){
                if (result.getContents() == null){
                    Toast.makeText(getApplicationContext(), "Cancelar o escaneamento", Toast.LENGTH_LONG).show();
                } else {
                    String codigoLido = result.getContents();

                    String formatar = codigoLido.substring(50, 94).trim();
                    if (codigoLido != null){

                        Call<CupomFiscal> call = apiInterface.buscarPelaChave(formatar);
                        call.enqueue(new Callback<CupomFiscal>() {
                            @Override
                            public void onResponse(Call<CupomFiscal> call, Response<CupomFiscal> response) {

                                CupomFiscal cupomFiscal = response.body();
                                final String cupom = cupomFiscal.getNumnota();
                                final String caixa = cupomFiscal.getNumcaixa().toString();



                                Call<Boolean> verificarNota = apiInterface.verificarCaixaCupom(cupomFiscal);

                                verificarNota.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        boolean res = response.body();

                                        if (res){

                                                Intent intent =  new Intent(getApplicationContext(), PequisarItemActivity.class);
                                                intent.putExtra("caixa",  caixa);
                                                intent.putExtra("cupom", cupom);
                                                intent.putExtra("leitor", true);
                                                startActivity(intent);

                                        }else {
                                            msg("Cupom/Caixa inválidos ou já conferidos .");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {

                                    }
                                });


                            }

                            @Override
                            public void onFailure(Call<CupomFiscal> call, Throwable t) {
                                msg("Verificar a data de Emissão do Cupom.");
                            }
                        });



                    } else{
                        Toast.makeText(getApplicationContext(), "Falha na leitura do código de barra, tente novamente", Toast.LENGTH_LONG).show();
                    }

                }

            } else {
                super.onActivityResult(requestCode, resultCode, data );
            }



        }catch (Throwable e){
            // Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }




    private void chamarModal() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_modal, null, false);
        numCaixa = view.findViewById(R.id.editTextNumCaixaId);
        numCupom = view.findViewById(R.id.editTextNumCupomId);


        alert.setView(view).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if ((numCupom.getText().length() <= 0 && numCaixa.getText().length() <= 0) || (numCupom.getText().length() <= 0 || numCaixa.getText().length() <= 0)){
                    Toast.makeText(getApplicationContext(), "Informar todos os campos.", Toast.LENGTH_LONG).show();
                } else {

                    CupomFiscal cupomFiscal = new CupomFiscal();
                    cupomFiscal.setNumcaixa(Integer.parseInt(numCaixa.getText().toString()));
                    cupomFiscal.setNumnota(numCupom.getText().toString());


                    try {
                        Call<Boolean> call = apiInterface.verificarCaixaCupom(cupomFiscal);

                        call.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                // Log.i("response", response.body().toString());

                                boolean res = response.body();

                                if (res){
                                    Intent intent =  new Intent(getApplicationContext(), PequisarItemActivity.class);
                                    intent.putExtra("numCupom",  numCupom.getText().toString());
                                    intent.putExtra("numCaixa", numCaixa.getText().toString());
                                    startActivity(intent);
                                } else {
                                    msg("Verificar a data de Emissão ou Já conferido");
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Log.i("response", t.getMessage());
                            }
                        });

                    } catch (NullPointerException e){
                        msg("Sem comunicação com o caixa");
                    }


                }


            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_LONG).show();
            }
        });

			alert.show();

    }

    //METODO PARA ESCANEAR
    public void escanear(){
        IntentIntegrator intent = new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);


        intent.setPrompt("Escanear Código de Barras");
        intent.setCameraId(0);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }

    public void msg(String msg){

        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        View toastView = toast.getView(); // This'll return the default View of the Toast.

        /* And now you can get the TextView of the default View of the Toast. */
        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
        toastMessage.setTextSize(20);
        toastMessage.setHeight(80);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setCompoundDrawablePadding(16);
        toastView.setBackgroundColor(Color.RED);
        toast.show();
    }
}
