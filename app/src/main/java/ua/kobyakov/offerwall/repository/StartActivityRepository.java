package ua.kobyakov.offerwall.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.kobyakov.offerwall.App;
import ua.kobyakov.offerwall.Client;
import ua.kobyakov.offerwall.model.ServerResponse;
import ua.kobyakov.offerwall.database.AppDatabase;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartActivityRepository {

    private static final String TAG = StartActivityRepository.class.getSimpleName();
    private AppDatabase db = App.get().getDB();
    private LiveData<ServerResponse> responseTypeWithDB;
    private MutableLiveData<Integer> statusCode;

    public StartActivityRepository() {
        responseTypeWithDB = db.responseDao().getLiveData();
        statusCode = new MutableLiveData<>();
    }

    public void networkRequest() {
        Log.d(TAG, "NetworkRequest");
        Disposable disposable = initRetrofitRx().getResponseRx()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            db.responseDao().insert(response.body());
                            if (response.code() != 200) {
                                statusCode.postValue(response.code());
                            }
                        },
                        error -> {
                            error.printStackTrace();
                            if (error.getLocalizedMessage().contains("Unable to resolve host")) {
                                statusCode.postValue(-200);
                            } else {
                                statusCode.postValue(-300);
                            }
                        }
                );
    }

    private static Client initRetrofitRx() {
        return new Retrofit.Builder()
                .baseUrl("https://us-central1-server-f5d8f.cloudfunctions.net")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(Client.class);
    }

    public LiveData<ServerResponse> getResponseWithDB() {
        return responseTypeWithDB;
    }

    public MutableLiveData<Integer> getStatusCode() {
        return statusCode;
    }
}