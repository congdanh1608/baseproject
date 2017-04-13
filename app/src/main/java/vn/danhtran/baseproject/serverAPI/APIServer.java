package vn.danhtran.baseproject.serverAPI;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.danhtran.baseproject.serverAPI.models.ErrorModel;

/**
 * Created by SilverWolf on 11/04/2017.
 */

public interface APIServer {
    String header = "Content-Type: application/json";

    //-----------------Call<>
    @Headers(header)
    @POST("{api}/{sub}")
    Call<JsonElement> postRequest(@Path("api") String api, @Path("sub") String sub, @Body JsonElement jsonData);

    @Headers(header)
    @GET("{api}")
    Call<JsonElement> getRequest(@Path("api") String api, @Body JsonElement jsonData);

    @Headers(header)
    @DELETE("{api}")
    Call<JsonElement> deleteRequest(@Path("api") String api, @Body JsonElement jsonData);

    @Headers(header)
    @PUT("{api}")
    Call<JsonElement> putRequest(@Path("api") String api, @Body JsonElement jsonData);

    //-----------------Observable<>

    @Headers(header)
    @POST("{api}/{sub}")
    <T extends ErrorModel> Observable<T> post(@Path("api") String api, @Path("sub") String sub, @Body JsonElement jsonData);

    @Headers(header)
    @GET("{api}")
    <T extends ErrorModel> Observable<T> get(@Path("api") String api, @Path("sub") String sub, @Body JsonElement jsonData);

    @Headers(header)
    @DELETE("{api}")
    <T extends ErrorModel> Observable<T> delete(@Path("api") String api, @Path("sub") String sub, @Body JsonElement jsonData);

    @Headers(header)
    @PUT("{api}")
    <T extends ErrorModel> Observable<T> put(@Path("api") String api, @Path("sub") String sub, @Body JsonElement jsonData);
}
