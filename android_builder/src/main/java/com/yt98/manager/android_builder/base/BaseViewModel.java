package com.yt98.manager.android_builder.base;

import android.os.Parcelable;
import android.util.Log;

import com.yt98.manager.android_builder.exception.ViewNotAttatchedException;
import com.yt98.manager.android_builder.network.callback.ResponseCallback;
import com.yt98.manager.android_builder.utils.ClassInfo;
import com.yt98.manager.android_builder.utils.StateType;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.UiThread;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

/**
 * RootViewModel for any ViewModel in the Project to include the status of {@BaseView}
 * Manage the ViewModel
 *
 * @param <View>  for each Screen
 * @param <Model> for each ViewModel
 *                <p>
 *                make sure you send the {@BaseView} to ViewModel to handle the Operations between view and model
 *                also send the {@Lifecycle} for each View to tell the ViewModel what is the status of his View
 *                <p>
 *                the initialize for the ViewModel like this
 *                <p>
 *                model = getViewModel();
 *                model.setView(this);
 */

@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
public abstract class BaseViewModel<View extends BaseView, Model extends Parcelable> extends ViewModel
        implements BaseViewModelImpl {

    public static final String TAG = "BaseViewModelTAG";

    /**
     * This boolean return the view status if the view is attached or not
     */
    private AtomicBoolean isViewAttached;

    /**
     * Save the View in WeakReference to save the View from Memory Leaks
     */
    private WeakReference<View> viewRef;


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
            return null;
        }
    }

    @Override
    public void changeViewStatus(boolean status) {
        if (isViewAttached != null) {
            isViewAttached.set(status);
        }
    }

    @Override
    public void onResume(BaseView view) {
        if (view != null) {
            setView((View) view);
        }
    }

    protected boolean getViewStatus() {
        return isViewAttached.get();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    @Override
    public void onStop() {
        if (viewRef != null) {
            viewRef.clear();
        }
    }

    protected abstract void initialAction(StateType state);

    protected abstract ResponseCallback<Model> getCallback();


    @Override
    protected void onCleared() {
        super.onCleared();
        this.onDestroy();
    }

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
