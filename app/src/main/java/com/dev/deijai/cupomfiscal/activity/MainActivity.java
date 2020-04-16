package com.dev.deijai.cupomfiscal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.deijai.cupomfiscal.R;
import com.dev.deijai.cupomfiscal.model.Login;
import com.dev.deijai.cupomfiscal.webconfig.APIClient;
import com.dev.deijai.cupomfiscal.webconfig.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button botaoLogar;
    private APIInterface apiInterface;
    private TextView matricula;
    private TextView senha;
    private List<Login> loginList;
    public static String codfunc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        botaoLogar = findViewById(R.id.botaoLogarId);
        matricula = findViewById(R.id.matriculaId);
        senha = findViewById(R.id.senhaId);
        apiInterface  = APIClient.getClient().create(APIInterface.class);


        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("matricula", matricula.getText().toString());
                Log.i("senha", senha.getText().toString());

                if ((matricula.length() <= 0 && senha.length() <= 0) || (matricula.length() <= 0 || senha.length() <= 0)){
                    Toast.makeText(getApplicationContext(), "Informar todos os campos!", Toast.LENGTH_LONG).show();
                } else {



                    loginList = new ArrayList<>();
                    final Login login = new Login();
                    login.setMatricula(matricula.getText().toString());
                    login.setSenha(senha.getText().toString());
                    loginList.add(login);



                    Call<Boolean> call = apiInterface.verificarLogin(loginList);

                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            boolean resposta = response.body();
                            if (resposta == true){

                                codfunc = matricula.getText().toString();
                                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Matricula/Senha Incorretos.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.i("onFailure", "caiu aqui");
                        }
                    });

                }



            }
        });
    }
}
