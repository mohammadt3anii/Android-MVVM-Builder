package com.yt98.manager.apps.dagger;

import com.yt98.manager.apps.data.UserRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class UserRepoModule {

    public static final String BASE_URL = "https://api.github.com/";

    @Provides
    public UserRepository repository(){
        return new UserRepository(BASE_URL);
    }

}
