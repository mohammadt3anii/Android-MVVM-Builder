package com.yt98.manager.apps.networkActivity.data;

import com.yt98.manager.android_builder.network.BaseRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Response;

public class UserRepository extends BaseRepository<UserModel, UserApi> {

    private Observable<Response<UserModel>> userObservable;
    private UserApi userApi;

    @Inject
    public UserRepository(String baseUrl) {
        super(baseUrl);
        createService();
    }

    private Observable<Response<UserModel>> getUserObservable(){
        return userObservable = userApi.getUser();
    }

    public void getUserInfo(){
        addRequest(getUserObservable());
    }

    @Override
    protected UserApi createService() {
        return userApi = getRetrofit().create(UserApi.class);
    }

}
