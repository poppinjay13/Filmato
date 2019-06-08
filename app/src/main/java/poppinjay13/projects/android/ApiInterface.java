package poppinjay13.projects.android;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ApiInterface {


// For POST request

    @FormUrlEncoded // annotation used in POST type requests
    @POST("/retrofit/register.php")     // API's endpoints
    void registration(@Field("name") String name,
                      @Field("email") String email,
                      @Field("password") String password,
                      @Field("logintype") String logintype,
                      Callback<SignUpPOJO> callback);

// for GET request

    @GET("/demo/countrylist.php") // specify the sub url for our base url
    void getMovieList(Callback<List<MoviePOJO>> callback);

}
