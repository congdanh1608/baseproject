package vn.danhtran.baseproject.serverAPI;

import com.google.gson.JsonElement;
import com.halcyon.logger.HttpLogInterceptor;
import com.halcyon.logger.ILogger;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.danhtran.baseproject.enums.Api;
import vn.danhtran.baseproject.enums.Method;
import vn.danhtran.baseproject.enums.Sub;
import vn.danhtran.baseproject.serverAPI.apiconfig.APIConfig;
import vn.danhtran.baseproject.serverAPI.apiconfig.APIServer;
import vn.danhtran.baseproject.serverAPI.errorexception.ErrorHandlingExecutorCallAdapterFactory;

/**
 * Created by SilverWolf on 11/04/2017.
 */

public class RestClient {
    private static final int READ_TIMEOUT = 60;
    private static final int REQUEST_TIMEOUT = 60;
    private static Retrofit retrofit;
    private static APIServer sService;

    private static RestClient restClient;

    public static RestClient instance() {
        if (restClient == null) {
            restClient = new RestClient();
        }
        return restClient;
    }


    private Retrofit getRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(getInterceptor())
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(APIConfig.domainAPI)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new ErrorHandlingExecutorCallAdapterFactory(
                            new ErrorHandlingExecutorCallAdapterFactory.MainThreadExecutor()))
                    .build();
        }
        return retrofit;
    }

    public APIServer getHttpClient() {
        if (sService == null) {
            sService = getRetrofit().create(APIServer.class);
        }
        return sService;
    }

    //print log
    private HttpLogInterceptor getInterceptor() {
//        if (BuildConfig.DEBUG)
//            return new HttpLogInterceptor();

        return new HttpLogInterceptor(new ILogger() {
            @Override
            public void log(String msg) {
                //no print log
                Logger.d(msg);
            }
        });
    }

    //-----------------------

    //use enqueue
    public void request(Method method, Api api, Sub sub, JsonElement jsonData, Callback<JsonElement> callback) {
        switch (method) {
            case POST:
                getHttpClient().postRequest(api.toString(), sub.toString(), jsonData).enqueue(callback);
                break;
            case GET:
                getHttpClient().getRequest(api.toString(), jsonData).enqueue(callback);
                break;
            case DELETE:
                getHttpClient().deleteRequest(api.toString(), jsonData).enqueue(callback);
                break;
            case PUT:
                getHttpClient().putRequest(api.toString(), jsonData).enqueue(callback);
                break;
            default:
                break;
        }
    }

    public void request(Method method, Api api, JsonElement jsonData, Callback<JsonElement> callback) {
        request(method, api, Sub.NONE, jsonData, callback);
    }
}