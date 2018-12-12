package com.yt98.manager.android_builder.network.rest.normalRepository;

import android.os.Parcelable;
import android.util.Log;

import com.yt98.manager.android_builder.base.ClassInfo;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * BaseRepository root for all classes will send any request to backend and get response using rxJava2
 * @param <Model> the model of the Requests
 * @param <Api> the Service that have the url's and method type
 *
 *             make sure you call destroyRepo() to clear the observable from CompositeDisposable
 */

@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
public abstract class BaseRepository<Model extends Parcelable , Api> {

    public static final String TAG = "BaseRepository";

    private ResponseCallback<Model> callback;
    private CompositeDisposable compositeDisposable;
    private HttpLoggingInterceptor interceptor;
    private OkHttpClient client;
    private Retrofit retrofit;

    /**
     * Initialize Retrofit
     *
     * @param baseUrl
     */
    public BaseRepository(String baseUrl) {
        this.retrofit = initialRetrofit(baseUrl);
        compositeDisposable = new CompositeDisposable();
    }

    private Retrofit initialRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * This Method Here Add The Observable to {@CompositeDisposable} to Add it and Subscribe The Api Request
     * @param observable
     */
    public void addObservableToComposit(Observable<Response<Model>> observable) {
        this.compositeDisposable.add(getReactiveObservable(observable).subscribe(
                this::onSuccessResponse,
                this::onErrorResponse
        ));
    }

    /**
     * Handle the {@Throwable} from The Request if it throws an a Exception
     * @param throwable
     */
    private void onErrorResponse(Throwable throwable) {
        if (callback != null) {
            callback.onError(throwable);
        } else {
            Log.e(TAG, " Callback in OnError Null");
        }
    }

    private void onSuccessResponse(Response<Model> modelResponse) {
        if (callback != null) {
            callback.onSuccess(modelResponse.body(), modelResponse.code());
        } else {
            Log.e(TAG, " Callback in onSuccess Null");
        }
    }

    private Observable<Response<Model>> getReactiveObservable(Observable<Response<Model>> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * logging The Request
     *
     * @return {@Client}
     */
    private OkHttpClient getClient() {
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        return client;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public ResponseCallback<Model> getCallback() {
        return callback;
    }

    public void setCallback(ResponseCallback<Model> callback) {
        this.callback = callback;
    }

    /**
     * Don't Forget call destroyRepo to Clear The Observables
     */
    public void destroyRepo() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    /**
     * This Method will Provide the Api {@Interface} that have the Api's Requests
     */
    protected abstract Api createService();
}
