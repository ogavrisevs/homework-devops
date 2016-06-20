package com.neueda.homework.devops.infrastructure;

import com.neueda.homework.devops.dto.Event;
import com.neueda.homework.devops.dto.Statistics;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ConsumerResource {

    @POST("event")
    Call<Statistics> postEvent(@Body Event event);

}
