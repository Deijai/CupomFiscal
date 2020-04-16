package com.dev.deijai.cupomfiscal.webconfig;

import com.dev.deijai.cupomfiscal.model.CupomFiscal;
import com.dev.deijai.cupomfiscal.model.Login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {

    @GET("api_gsa/api/nota/{chavenfe}")
    Call<CupomFiscal> buscarPelaChave(@Path("chavenfe") String chavenfe);

    @POST("api_gsa/api/login")
    Call<Boolean> verificarLogin(@Body List<Login> login);

    @POST("api_gsa/api/caixacupom")
    Call<Boolean> verificarCaixaCupom(@Body CupomFiscal caixacupom);

    @Headers("Content-Type: application/json")
    @POST("api_gsa/api/status")
    Call<Boolean> alterarStatus(@Body CupomFiscal dados);

    @POST("api_gsa/api/itens")
    Call<List<CupomFiscal>> pegarItens(@Body List<CupomFiscal> itens);

    @Headers("Content-Type: application/json")
    @POST("api_gsa/api/item")
    Call<CupomFiscal>pegarItenCodAuxiliar(@Body CupomFiscal item);

}
