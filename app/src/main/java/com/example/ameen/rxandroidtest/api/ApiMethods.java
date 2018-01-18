package com.example.ameen.rxandroidtest.api;

import com.example.ameen.rxandroidtest.response.UsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiMethods {

    @GET(ApiEndPoints.GET_USERS)
    Call<UsersResponse> getUsers(@Query(ApiParam.USER_ID) String userId,
                                 @Query(ApiParam.TERM) String term);
}



