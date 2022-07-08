package com.example.firsttask.data.retrofit;

import com.example.firsttask.data.retrofit.entities.details.DetailsResponse;
import com.example.firsttask.data.retrofit.entities.history.HistoryResponse;
import com.example.firsttask.data.retrofit.entities.link.LinkResponse;
import com.example.firsttask.data.retrofit.entities.login.LoginResponse;
import com.example.firsttask.data.retrofit.entities.recent.UserResponse;

import java.util.Date;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @FormUrlEncoded
    @POST("/auth")
    Single<Response<LoginResponse>> login(
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("merchant_code") String merchantCode
    );

    @GET("/api/Transaction/Recent")
    Single<UserResponse> getTransactionRecent();

    @GET("api/Transaction/History")
    Single<HistoryResponse> getTransactionsHistory(
            @Query("pStartDate") String pStartDate,
            @Query("pEndDate") String pEndDate,
            @Query("pStatus") String pStatus,
            @Query("pSearch") String pSearch
            );

    @GET("/api/Transaction/Details")
    Single<DetailsResponse> getDetails(@Query("pTransactionKey") String transactionKey);

    @GET("api/Invoicing/PayLink/{id}")
    Single<LinkResponse> getInvoiceLink(@Path("id") String transactionKey);

    @GET("api/Statement/DownloadPdf?pFactID=31932")
    Call<ResponseBody> getPDF();
}
