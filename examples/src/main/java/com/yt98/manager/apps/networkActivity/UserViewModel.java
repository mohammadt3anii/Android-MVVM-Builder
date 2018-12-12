package com.yt98.manager.apps.networkActivity;

import com.yt98.manager.android_builder.base.BaseViewModel;
import com.yt98.manager.android_builder.network.rest.normalRepository.ResponseCallback;
import com.yt98.manager.apps.networkActivity.dagger.DaggerUserComponent;
import com.yt98.manager.apps.networkActivity.dagger.UserComponent;
import com.yt98.manager.apps.networkActivity.dagger.UserRepoModule;
import com.yt98.manager.apps.networkActivity.data.UserModel;
import com.yt98.manager.apps.networkActivity.data.UserRepository;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends BaseViewModel<UserView , UserModel> {

    @Inject
    UserRepository repository;
    private UserComponent component;
    private MutableLiveData<UserModel> user;

    @Inject
    public UserViewModel() {
        repository = getComponent().getUserRepository();
    }

    public void getUserInfo() {
        if (getUser().getValue() != null) {
            if(getViewStatus()){
                getView().onSuccess(getUser().getValue());
            }
            return;
        }

        if (getViewStatus()) {
            getView().showLoading();
        }
        repository.getUserInfo();
        repository.setCallback(getCallback());
    }

    @Override
    public ResponseCallback<UserModel> getCallback() {
        return new ResponseCallback<UserModel>() {
            @Override
            public void onSuccess(UserModel content, Integer code) {
                if (getViewStatus()) {
                    if (code != null && (code >= 200 && code <= 250)) {
                        getUser().setValue(content);
                        getView().onSuccess(getUser().getValue());
                    } else {
                        getView().onError("Something Error : " + code);
                    }
                    getView().hideLoading();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (getViewStatus()) {
                    getView().onError(throwable.getMessage());
                    getView().hideLoading();
                }
            }
        };
    }

    public MutableLiveData<UserModel> getUser() {
        if (user == null) {
            user = new MutableLiveData<>();
        }
        return user;
    }

    public UserComponent getComponent() {
        if (component == null) {
            component = DaggerUserComponent
                    .builder()
                    .userRepoModule(new UserRepoModule())
                    .build();

        }
        return component;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (repository != null) {
            repository.destroyRepo();
        }

    }
}
