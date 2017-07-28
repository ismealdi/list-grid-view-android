package com.aldieemaulana.president.api;

import com.aldieemaulana.president.response.PresidentResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by aldieemaulana on 7/28/17.
 */

public interface PresidentApi {

    @GET("presidents")
    @Headers({"Content-Type: application/json"})
    Call<PresidentResponse> getPresident();

}
