package ua.kobyakov.offerwall.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import ua.kobyakov.offerwall.model.ServerResponse;
import ua.kobyakov.offerwall.repository.StartActivityRepository;

public class StartActivityVM extends ViewModel {
    private StartActivityRepository repository;
    private LiveData<ServerResponse> responseTypeDB;
    private LiveData<Integer> statusCode;

    public StartActivityVM() {
        repository = new StartActivityRepository();
        responseTypeDB = repository.getResponseWithDB();
        statusCode = repository.getStatusCode();
    }

    public void sendRequest() {
        repository.networkRequest();
    }

    public LiveData<ServerResponse> getResponseWithDB() {
        return responseTypeDB;
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }
}