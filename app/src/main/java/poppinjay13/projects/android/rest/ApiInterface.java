package poppinjay13.projects.android.rest;

import poppinjay13.projects.android.model.Result;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("register")
    Call<Result> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<Result> loginUser(
            @Field("email") String email,
            @Field("password") String password);
}

