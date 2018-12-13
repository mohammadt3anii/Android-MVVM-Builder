package com.yt98.manager.android_builder.local;

import com.yt98.manager.android_builder.utils.ClassInfo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

//TODO: NOT COMPLETED YET

@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
public class BaseDaoRepository<E> {

    @Inject
    DaoManager<E> manager;
    private CompositeDisposable compositeDisposable;
    private DaoRepositoryCallback<List<E>> listCallback;
    private DaoRepositoryCallback<E> contentCallback;

    @Inject
    public BaseDaoRepository(DaoManager<E> manager) {
        this.manager = manager;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void getAllContents(String query) {
        if (manager != null) {
            Disposable allContentDisposable = manager
                    .getAllContents(query)
                    .subscribe(
                            this::onAllContentsSuccess,
                            this::onAllContentsFailed
                    );
            compositeDisposable.add(allContentDisposable);
        }
    }

    public void getContentById(String query) {
        if (manager != null) {
            Disposable contentDisposable = manager
                    .getContentById(query)
                    .subscribe(
                            this::onContentSuccess,
                            this::onContentError
                    );
            compositeDisposable.add(contentDisposable);
        }
    }

    public void deleteContent(E content) {
        if (manager != null) {
            Disposable deleteContentDisposable =  manager
                    .deleteContent(content)
                    .subscribe();
            compositeDisposable.add(deleteContentDisposable);
        }
    }

    public void insertContent(E content) {
        if (manager != null) {
            Disposable insertDisposable =  manager
                    .insertContent(content)
                    .subscribe();
            compositeDisposable.add(insertDisposable);
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
