package com.yt98.manager.android_builder.local;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//TODO: NOT COMPLETED YET
public class BaseDaoRepository<E> {

    private BaseDao<E> dao;
    private CompositeDisposable compositeDisposable;
    private DaoRepositoryCallback<List<E>> listCallback;
    private DaoRepositoryCallback<E> contentCallback;

    @Inject
    public BaseDaoRepository(BaseDao<E> dao) {
        this.dao = dao;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void getAllContents(String query) {
        if (dao != null) {
            Disposable allContentDisposable = dao
                    .getAllContents(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::onAllContentsSuccess,
                            this::onAllContentsFailed);
            compositeDisposable.add(allContentDisposable);
        }
    }

    public void getContentById(String query) {
        if (dao != null) {
            Disposable contentDisposable = dao
                    .getContentById(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::onContentSuccess,
                            this::onContentError);
            compositeDisposable.add(contentDisposable);
        }
    }

    private void onContentError(Throwable throwable) {
        if (contentCallback != null) {
            contentCallback.onDaoRepositoryFailed(throwable);
        }
    }

    private void onContentSuccess(E content) {
        if (contentCallback != null) {
            contentCallback.onDaoRepositorySuccess(content);
        }
    }

    private void onAllContentsFailed(Throwable throwable) {
        if (listCallback != null) {
            listCallback.onDaoRepositoryFailed(throwable);
        }
    }

    private void onAllContentsSuccess(List<E> es) {
        if (listCallback != null) {
            listCallback.onDaoRepositorySuccess(es);
        }
    }

    public void setContentCallback(DaoRepositoryCallback<E> contentCallback) {
        this.contentCallback = contentCallback;
    }

    public void setListCallback(DaoRepositoryCallback<List<E>> callback) {
        this.listCallback = callback;
    }

    public void destroyRepo() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public interface DaoRepositoryCallback<E> {
        void onDaoRepositorySuccess(E content);

        void onDaoRepositoryFailed(Throwable throwable);
    }
}
