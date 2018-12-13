package com.yt98.manager.android_builder.base;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.yt98.manager.android_builder.utils.ClassInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * This Class is the Root For the Activities that support MVVM Library
 *
 * just Support AndroidX
 */

@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
public abstract class BaseActivity extends AppCompatActivity {

    private Intent intent;
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private static ProgressDialog dialog;


    protected void startScreen(Context context, Class<?> target, boolean isFinishedEnabled) {
        this.intent = new Intent(context, target);
        startActivity(intent);
        if (isFinishedEnabled) {
            finish();
        }
    }

    public static void showLoader(Context activity , String title , boolean isCancelable) {
        if (dialog == null) {
            dialog = new ProgressDialog(activity);
            dialog.setMessage(title);
            dialog.setCanceledOnTouchOutside(isCancelable);
            dialog.setCancelable(isCancelable);
        }
        dialog.show();
    }

    public static void hideLoader() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    protected void addFragment(Fragment fragment, int frame) {
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(frame, fragment).commit();
    }

    protected void replaceFragment(Fragment fragment, int frame, Integer animOne, Integer animTwo, boolean backStackEnabled, AppCompatActivity context) {
        transaction = getSupportFragmentManager().beginTransaction();
        manager = context.getSupportFragmentManager();
        if (animOne != null && animTwo != null) {
            transaction.setCustomAnimations(animOne, animTwo);
        }
        transaction.replace(frame, fragment);
        if (backStackEnabled) {
            transaction.addToBackStack(fragment.getTag());
        } else {
            manager.popBackStack();
        }
        transaction.commit();
    }


    protected abstract int getLayoutRes();
}
