package api;

import model.CountryModel;
import model.Result;
import retrofit2.Call;
import retrofit2.http.GET;

public interface API_Interface {

    @GET("/v2/countries/india")
    Call<Result> getTotalOfIndia();
}