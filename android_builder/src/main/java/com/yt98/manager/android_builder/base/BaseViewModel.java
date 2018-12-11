package com.yt98.manager.android_builder.base;

import android.os.Parcelable;
import android.util.Log;

import com.yt98.manager.android_builder.network.rest.ResponseCallback;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.UiThread;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel<View extends BaseView, Model extends Parcelable> extends ViewModel
        implements BaseViewModelImpl<View> {

    public static final String TAG = "BaseViewModelTAG";

    /**
     * This boolean return the view status if the view is attached or not
     */
    private AtomicBoolean isViewAttached;

    /**
     * Save the View in WeakReference to save the View from Memory Leaks
     */
    private WeakReference<View> viewRef;

    /**
     * LifeCycleRegistery is a Part Of LifeCycle to tell the ViewModel what is the Status of View or Any UnExpected Exception
     */
    private Lifecycle viewLifeCycle;

    public BaseViewModel() {
        this.isViewAttached = new AtomicBoolean(false);
    }

    /**
     * Here We Pass the View To Each ViewModel This Method is Important to Call From View to attach the View to ViewModel
     *
     * @param view The Current View
     */
    @UiThread
    public void setView(View view) {
        this.viewRef = new WeakReference<>(view);
        if (this.isViewAttached != null) {
            changeViewStatus(true);
        }
    }

    @UiThread
    public View getView() {
        if (viewRef != null) {
            return viewRef.get();
        } else {
            Log.e(TAG, " View Not Attached");
            return null;
        }
    }

    @Override
    public void changeViewStatus(boolean status) {
        if (isViewAttached != null) {
            isViewAttached.set(status);
        }
    }

    public void setViewLifeCycle(Lifecycle viewLifeCycle) {
        this.viewLifeCycle = viewLifeCycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onResume(View view) {
        if (viewLifeCycle != null) {
            if (viewLifeCycle.getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                setView(view);
            }
        }
    }

    protected boolean getViewStatus() {
        return isViewAttached.get();
    }

    public Lifecycle getViewLifeCycle() {
        return viewLifeCycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    @Override
    public void onStop() {
        if (viewRef != null) {
            viewRef.clear();
        }
    }

    protected abstract ResponseCallback<Model> getCallback();

    /**
     * Don't Forget To call onDestroy() for destroy the values from current View
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDestroy() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }
}
