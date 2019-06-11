package poppinjay13.projects.android.rest;

import poppinjay13.projects.android.model.configuration.ConfigurationResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Configurations {
    //CONFIGURATIONS
    @GET("/configuration")
    Call<ConfigurationResponse> configurations(@Query("api_key") String apiKey);
}
