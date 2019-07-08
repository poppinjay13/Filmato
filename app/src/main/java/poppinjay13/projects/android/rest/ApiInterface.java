package poppinjay13.projects.android.rest;

import poppinjay13.projects.android.model.Result;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("/register")
    Call<Result> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("/login")
    Call<Result> loginUser(
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("/payment")
    Call<Result> logPayment(
            @Field("phone") String phone,
            @Field("amount") String amount);

    @FormUrlEncoded
    @POST("/ticket")
    Call<Result> logTicket(
            @Field("email") String email,
            @Field("movie") String movie,
            @Field("cinema") String cinema,
            @Field("date") String date,
            @Field("time") String time,
            @Field("seat") String seat,
            @Field("price") String price);

    @FormUrlEncoded
    @POST("/myTickets")
    Call<Result> getTickets(
            @Field("email") String email);
}

