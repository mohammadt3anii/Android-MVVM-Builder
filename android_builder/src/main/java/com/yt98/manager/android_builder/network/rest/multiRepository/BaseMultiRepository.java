package com.yt98.manager.android_builder.network.rest.multiRepository;


import com.yt98.manager.android_builder.base.ClassInfo;
import com.yt98.manager.android_builder.ui.utils.LogLevel;
import com.yt98.manager.android_builder.ui.utils.LogUtils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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
 * Class for Manage the Requests to save it from Executing at the same time
 * <p>
 * each Request you add it to {@ProvideRequest} it will add the Request to Current Queue and Execute all of them
 * when you call {@executeRequests()}
 *
 * @param <Api> The Service have Backend Requests
 */

@ClassInfo(
        version = 1,
        created = "12/12/2018",
        createdBy = "Yazan98"
)
public abstract class BaseMultiRepository<Api, Model> {

    private MultiRepositoryCallback<Model> callback;
    private Queue<Observable<Response<Model>>> requests;
    private CompositeDisposable compositeDisposable;
    private HttpLoggingInterceptor interceptor;
    private OkHttpClient client;
    private Retrofit retrofit;

    public BaseMultiRepository(String baseUrl) {
        this.retrofit = initialRetrofit(baseUrl);
        compositeDisposable = new CompositeDisposable();
        this.requests = new ConcurrentLinkedQueue();
    }

    /**
     * Add Factory and Adapter for Retrofit
     *
     * @param baseUrl
     * @return
     */
    private Retrofit initialRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Logging the Request Body
     *
     * @return
     */
    private OkHttpClient getClient() {
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        return client;
    }

    /**
     * Here this method will execute the Observable on Background Thread and Reflect the Changes on Main Thread
     *
     * @param currentRequest
     * @return Background Observable Executable for Network Requests
     */
    protected Observable<Response<Model>> getReactiveObservable(Observable<Response<Model>> currentRequest) {
        return currentRequest
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * This Method will Remove the Request from the Queue after Execute the Request
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    private void deleteRequest() {
        if (this.requests != null) {
            synchronized (requests) {
                requests.poll();
            }
        }
    }

    /**
     * Here we will add Request to Queue
     *
     * @param request
     */
    protected void addRequest(Observable<Response<Model>> request) {
        if (this.requests != null) {
            this.requests.add(request);
        }
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * Here when you finish adding Your Own Requests to the Queue will execute the full Queue
     */
    public void executeRequests() {
        LogUtils.log(LogLevel.ERROR, "Requests Will be Execute");
        if (requests != null) {
            if (checkRequestsStatus()) {
                this.compositeDisposable.add(
                        getReactiveObservable(getCurrentObservable()).subscribe(
                                this::successRequest,
                                this::errorRequest,
                                this::completeRequest
                        )
                );
            }
        }
    }

    // Subscriber Callback
    private void completeRequest() {
        if (getCallback() != null) {
            getCallback().onComplete();
        } else {
            LogUtils.log(LogLevel.ERROR, "BaseMultiRepository Callback Null");
        }

        //Delete the Request when The Request was Complete
        deleteRequest();
    }

    // Subscriber Callback
    private void errorRequest(Throwable throwable) {
        if (getCallback() != null) {
            getCallback().onError(throwable);
        } else {
            LogUtils.log(LogLevel.ERROR, "BaseMultiRepository Callback Null");
        }
    }

    // Subscriber Callback
    private void successRequest(Response<Model> modelResponse) {
        if (getCallback() != null) {
            getCallback().onSuccess(modelResponse.body(), modelResponse.code());
        } else {
            LogUtils.log(LogLevel.ERROR, "BaseMultiRepository Callback Null");
        }
    }

    private Observable<Response<Model>> getCurrentObservable() {
        Observable<Response<Model>> currentRequest = null;
        if (this.requests != null) {
            synchronized (requests) {
                currentRequest = requests.peek();
            }
        } else {
            LogUtils.log(LogLevel.ERROR, "Requests Queue Null");
        }
        return currentRequest;
    }

    private boolean checkRequestsStatus() {
        return requests.isEmpty();
    }

    public MultiRepositoryCallback<Model> getCallback() {
        return callback;
    }

    public void setCallback(MultiRepositoryCallback<Model> callback) {
        this.callback = callback;
    }

    /**
     * Provide Your Own Request from Sub Repository
     *
     * @param request
     */
    protected abstract void provideRequest(Observable<?> request);

    protected abstract Api getService();
}
