package com.neueda.homework.devops.infrastructure;

import com.neueda.homework.devops.dto.RegistryResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface RegistryResource {

    @GET("v2/keys/services/{service}")
    Call<RegistryResponse> discover(@Path("service") String service);

    @FormUrlEncoded
    @PUT("v2/keys/services/{service}")
    Call<RegistryResponse> register(@Path("service") String service,
                                    @Field("value") String hostname,
                                    @Field("ttl") int ttl);

}
