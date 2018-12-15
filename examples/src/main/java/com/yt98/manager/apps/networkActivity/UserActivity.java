package com.yt98.manager.apps.networkActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yt98.manager.android_builder.base.BaseView;
import com.yt98.manager.android_builder.utils.StateType;
import com.yt98.manager.android_builder.ui.activity.BaseNetworkActivity;
import com.yt98.manager.apps.R;
import com.yt98.manager.apps.networkActivity.data.UserModel;

import javax.inject.Inject;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProviders;

public class UserActivity extends BaseNetworkActivity<UserView, UserModel, UserViewModel> implements UserView {

    @Inject
    UserViewModel model;

    private ProgressBar loader;
    private ImageView image;
    private TextView name;
    private TextView bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());

        /**
         * Replace This with Butterknife or DataBinding
         */
        loader = findViewById(R.id.loading);
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        bio = findViewById(R.id.bio);

        if (model == null) {
            model = getViewModel();
            model.setView(this);
        }

        model.getUser().observe(this, user -> {

            Toast.makeText(this, "Observing Data...", Toast.LENGTH_SHORT).show();
            if (model.getUser().getValue() == null) {
                model.getUserInfo(StateType.CURRENT_STATE);
            } else {
                name.setText(user.getName());
                bio.setText(user.getBio());
                Picasso.get()
                        .load(user.getAvatarUrl())
                        .into(image);
            }
        });

        if (model.getUser().getValue() == null) {
           model.initialAction(StateType.INITIAL_STATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (model != null) {
            model.onStop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (model != null) {
            model.onResume(reAttachView());
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void showLoading() {
        loader.setVisibility(View.VISIBLE);
        name.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
        bio.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        loader.setVisibility(View.GONE);
        name.setVisibility(View.VISIBLE);
        image.setVisibility(View.VISIBLE);
        bio.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(UserModel userModel) {
        name.setText(userModel.getName());
        bio.setText(userModel.getBio());
        Picasso.get()
                .load(userModel.getAvatarUrl())
                .into(image);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public BaseView reAttachView() {
        return this;
    }

    @Override
    protected UserViewModel getViewModel() {
        return ViewModelProviders.of(this).get(UserViewModel.class);
    }

    public void refresh(View view) {
            model.getUserInfo(StateType.REFRESH_STATE);
    }
}
