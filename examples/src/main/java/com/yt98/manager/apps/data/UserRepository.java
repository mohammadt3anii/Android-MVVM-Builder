package com.yt98.manager.apps.data;

import com.yt98.manager.android_builder.network.rest.BaseSingleRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Response;

public class UserRepository extends BaseSingleRepository<UserModel, UserApi> {

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
        addObservableToComposit(getUserObservable());
    }

    @Override
    protected UserApi createService() {
        return userApi = getRetrofit().create(UserApi.class);
    }

}
