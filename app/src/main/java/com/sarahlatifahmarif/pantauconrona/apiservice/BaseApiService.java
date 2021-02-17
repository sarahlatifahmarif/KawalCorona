package com.sarahlatifahmarif.pantauconrona.apiservice;

import com.sarahlatifahmarif.pantauconrona.model.DayEachKabResponse;
import com.sarahlatifahmarif.pantauconrona.model.GetKabResponse;
import com.sarahlatifahmarif.pantauconrona.model.LatestResponse;
import com.sarahlatifahmarif.pantauconrona.model.PerDayResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {
    @GET("getCoronaLast/sumatera_barat")
    Call<LatestResponse> getLatestInfo();
    @GET("getDayData/sumatera_barat")
    Observable<List<PerDayResponse>> getDaysInfo();
    @GET("getDayeachKab")
    Call<DayEachKabResponse> getDayEachKab();
    @GET("getKab/{kab}")
    Call<GetKabResponse> getKab(
            @Path("kab") String kab
    );


}
