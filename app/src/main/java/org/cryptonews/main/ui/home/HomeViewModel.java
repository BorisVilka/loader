package org.cryptonews.main.ui.home;

import static org.cryptonews.main.Utils.getContext;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.cryptonews.main.MyApp;
import org.cryptonews.main.R;
import org.cryptonews.main.Utils;
import org.cryptonews.main.ui.list_utils.ListItem;
import org.cryptonews.main.ui.list_utils.MyExecutor;
import org.cryptonews.main.ui.list_utils.PagedDiffUtilCallback;
import org.cryptonews.main.ui.list_utils.adapters.PagedAdapter;
import org.cryptonews.main.ui.list_utils.data_sources.PagedDataSource;

import java.util.List;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private PagedDataSource dataSource;
    private PagedAdapter adapter;
    private Activity activity;
    private PagedDiffUtilCallback callback;
    private boolean one;
    private PagedList<ListItem> list;
    private Single<PagedList<ListItem>> completable;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        dataSource = new PagedDataSource();
        callback = new PagedDiffUtilCallback();
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .setPrefetchDistance(5)
                .setInitialLoadSizeHint(10)
                .build();
        completable = Single.create((SingleOnSubscribe<PagedList<ListItem>>) emitter -> {
            PagedList<ListItem> list = new PagedList.Builder(dataSource,config)
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .setNotifyExecutor(new MyExecutor())
                    .build();
            emitter.onSuccess(list);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public void iniList(Callback callback) {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .setPrefetchDistance(5)
                .setInitialLoadSizeHint(10)
                .build();
        Single<PagedList<ListItem>> completable = Single.create((SingleOnSubscribe<PagedList<ListItem>>) emitter -> {
            PagedList<ListItem> list = new PagedList.Builder(dataSource,config)
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .setNotifyExecutor(new MyExecutor())
                    .build();
            emitter.onSuccess(list);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        completable.subscribe(new Consumer<PagedList<ListItem>>() {
            @Override
            public void accept(PagedList<ListItem> listItems) throws Throwable {
                list = listItems;
                callback.call();
            }
        });
    }
    public void setList(PagedList<ListItem> list) {
        this.list = list;
    }
    public PagedList<ListItem> getList() {return list;};
    public LiveData<String> getText() {
        return mText;
    }
    public PagedDataSource getDataSource() {return dataSource;}
    public Single<PagedList<ListItem>> getCompletable() {
        return completable;
    }
    public interface Callback {
        void call();
    }
}