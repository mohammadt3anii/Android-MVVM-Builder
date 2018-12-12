package com.yt98.manager.apps.permissionActivity;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.yt98.manager.android_builder.ui.activity.BaseLocalActivity;
import com.yt98.manager.android_builder.ui.utils.PermissionUtils;
import com.yt98.manager.apps.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PermissionActivity extends BaseLocalActivity implements PermissionUtils.PermissionCallback {

    private Context context;
    private Button takePermissionBtn;

    public static final int PERMISSION_REQUEST_CODE = 123;
    public static final String PERMISSION_NAME = Manifest.permission.READ_EXTERNAL_STORAGE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        this.context = this;

        //Replace this with DataBinding or ButterKnife
        takePermissionBtn = findViewById(R.id.take_permission);

        takePermissionBtn.setOnClickListener(v -> PermissionUtils.checkPermission(
                context,
                PERMISSION_REQUEST_CODE,
                PERMISSION_NAME,
                new PermissionUtils.PermissionCallback() {
                    @Override
                    public void onSuccessPermission() {
                        Toast.makeText(context, "Permission Already Generated", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onErrorPermission() {
                        PermissionUtils.requestPermission(
                                context,
                                PERMISSION_REQUEST_CODE,
                                PERMISSION_NAME
                        );
                    }
                }
        ));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_permission;
    }

    @Override
    public void onSuccessPermission() {
        Toast.makeText(context, "Permission : " + PERMISSION_NAME + " Generated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorPermission() {
        Toast.makeText(context, "Permission Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.onRequestPermissionsResult(requestCode, grantResults, this);
    }
}
