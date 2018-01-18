package com.example.ameen.rxandroidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Observable<String> observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        observable = Observable.just("hello", "how ", "r ", "you");


        single
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(stringSingleObserver);
    }

    Single<String> single = Single.fromCallable(new Callable<String>() {
        @Override
        public String call() throws Exception {
            return "yo man";
        }
    });

    SingleObserver<String> stringSingleObserver = new DisposableSingleObserver<String>() {
        @Override
        public void onSuccess(String s) {
            Log.d(TAG, "onSuccess() called with: s = [" + s + "]");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "onError() called with: e = [" + e + "]");
        }

    };

    DisposableObserver<String> observer = new DisposableObserver<String>() {
        @Override
        public void onNext(String s) {
            Log.d(TAG, "onNext() called with: s = [" + s + "]");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "onError() called with: e = [" + e + "]");
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete() called");
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (observer != null && !observer.isDisposed()) {
            observer.dispose();
        }
    }
}

