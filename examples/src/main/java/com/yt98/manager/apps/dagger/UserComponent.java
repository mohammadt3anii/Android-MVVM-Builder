package com.yt98.manager.apps.dagger;

import com.yt98.manager.apps.data.UserRepository;

import dagger.Component;

@Component(modules = UserRepoModule.class)
public interface UserComponent {

    UserRepository getUserRepository();

}
