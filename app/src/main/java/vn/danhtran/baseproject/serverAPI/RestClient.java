package vn.danhtran.baseproject.serverAPI;

import com.google.gson.JsonElement;
import com.halcyon.logger.HttpLogInterceptor;
import com.halcyon.logger.ILogger;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.danhtran.baseproject.SingleResultListener;
import vn.danhtran.baseproject.enums.Api;
import vn.danhtran.baseproject.enums.Method;
import vn.danhtran.baseproject.enums.Sub;
import vn.danhtran.baseproject.receiver.ErrorReceiver;
import vn.danhtran.baseproject.serverAPI.models.ErrorModel;

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
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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

    //-----------------------

    //use Observable
    //send error
    public <T extends ErrorModel> void request(Method method, Api api, Sub sub, JsonElement jsonData,
                                               SingleResultListener<T> singleResultListener) {
        request(method, api, sub, jsonData, singleResultListener, true);
    }

    //is send error?
    public <T extends ErrorModel> void request(Method method, Api api, Sub sub, JsonElement jsonData,
                                               SingleResultListener<T> singleResultListener, boolean isSendError) {
        Observable<T> callShare = request(method, api, sub, jsonData);
        if (callShare != null)
            callShare.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> handleResponse(response, singleResultListener, isSendError),
                            error -> handleError(error, singleResultListener, isSendError));
    }

    //no sub + send error : true
    public <T extends ErrorModel> void request(Method method, Api api, JsonElement jsonData,
                                               SingleResultListener<T> singleResultListener) {
        request(method, api, Sub.NONE, jsonData, singleResultListener);
    }

    //no sub + is send error?
    public <T extends ErrorModel> void request(Method method, Api api, JsonElement jsonData,
                                               SingleResultListener<T> singleResultListener, boolean isSendError) {
        request(method, api, Sub.NONE, jsonData, singleResultListener, isSendError);
    }

    //return Observable -> custom data receive from server
    public <T extends ErrorModel> Observable<T> request(Method method, Api api, Sub sub, JsonElement jsonData) {
        Observable<T> callShare = null;
        switch (method) {
            case POST:
                callShare = getHttpClient().post(api.toString(), sub.toString(), jsonData);
                break;
            case GET:
                callShare = getHttpClient().get(api.toString(), sub.toString(), jsonData);
                break;
            case DELETE:
                callShare = getHttpClient().delete(api.toString(), sub.toString(), jsonData);
                break;
            case PUT:
                callShare = getHttpClient().put(api.toString(), sub.toString(), jsonData);
                break;
            default:
                break;
        }
        return callShare;
    }

    //-----------------------

    public <T extends ErrorModel> void handleResponse(T data, SingleResultListener<T> singleResultListener, boolean isSendError) {
        returnSuccess(data, singleResultListener, isSendError);
    }

    public void handleError(Throwable error, SingleResultListener singleResultListener, boolean isSendError) {
        returnFail(error, singleResultListener, isSendError);
    }

    //check error from server
    private boolean checkFailServer(ErrorModel errorModel) {
        return errorModel.getError().getCode() != 0;
    }

    public <T> void returnFail(Object error, SingleResultListener<T> singleResultListener, boolean isSendError) {
        singleResultListener.onFailure(error);
        if (isSendError)
            ErrorReceiver.instance().broadcastIntent(error);
    }

    public <T extends ErrorModel> void returnSuccess(T data, SingleResultListener<T> singleResultListener, boolean isSendError) {
        if (checkFailServer(data)) {
            singleResultListener.onFailure(data.getError());
            if (isSendError)
                ErrorReceiver.instance().broadcastIntent(data.getError());
        } else {
            List<T> datas = new ArrayList<>();
            datas.add(data);
            singleResultListener.onSuccess(datas);
        }
    }
}