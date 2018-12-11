package com.yt98.manager.apps.networkActivity.dagger;

import com.yt98.manager.apps.networkActivity.data.UserRepository;

import dagger.Component;

@Component(modules = UserRepoModule.class)
public interface UserComponent {

    UserRepository getUserRepository();

}
