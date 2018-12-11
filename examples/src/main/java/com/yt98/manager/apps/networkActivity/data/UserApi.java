package com.yt98.manager.apps.networkActivity.data;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface UserApi {

    @GET("users/Yazan98")
    Observable<Response<UserModel>> getUser();

}
