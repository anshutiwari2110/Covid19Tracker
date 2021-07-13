package api;

import model.Result;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true")
    Call<Result> getTotalOfIndia();
}