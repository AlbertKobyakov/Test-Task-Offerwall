package ua.kobyakov.offerwall;

import io.reactivex.Single;
import ua.kobyakov.offerwall.model.ServerResponse;
import retrofit2.Response;
import retrofit2.http.GET;

public interface Client {
    @GET("/offerwall")
    Single<Response<ServerResponse>> getResponseRx();
}
