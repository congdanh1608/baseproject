package vn.danhtran.baseproject.serverAPI.apiservice;


import com.google.gson.JsonElement;
import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.danhtran.baseproject.SingleResultListener;
import vn.danhtran.baseproject.enums.Api;
import vn.danhtran.baseproject.enums.Method;
import vn.danhtran.baseproject.enums.Sub;
import vn.danhtran.baseproject.serverAPI.JsonParser;
import vn.danhtran.baseproject.serverAPI.RestClient;
import vn.danhtran.baseproject.serverAPI.models.LoginModel;

/**
 * Created by danhtran on 14/02/2017.
 */
public class AuthenticateService {
    private static AuthenticateService authenticateService;

    public static AuthenticateService instance() {
        if (authenticateService == null) {
            synchronized (AuthenticateService.class) {
                if (authenticateService == null) {
                    authenticateService = new AuthenticateService();
                }
            }
        }

        return authenticateService;
    }

    //demo use observable
    public void authenticate1(SingleResultListener<LoginModel> singleResultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("sda", "ASdasd");
        params.put("ASdsa", "ASd");
        RestClient.instance().request(Method.POST, Api.LOGIN, Sub.NONE, JsonParser.fromJsonElement(params), singleResultListener);
    }

    //demo use observable with custom functions in data received
    public void authenticate2(SingleResultListener<LoginModel> singleResultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("sda", "ASdasd");
        params.put("ASdsa", "ASd");
        RestClient.instance().request(Method.POST, Api.LOGIN, Sub.NONE, JsonParser.fromJsonElement(params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(errorModel -> {
                            //Custom functions.
                            Logger.d("Custom functions!");
                            RestClient.instance().handleResponse((LoginModel) errorModel, singleResultListener, true);
                        }
                        , throwable -> RestClient.instance().handleError(throwable, singleResultListener, true));
    }

    //demo use enqueue
    public void authenticate3(SingleResultListener<LoginModel> singleResultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("sda", "ASdasd");
        params.put("ASdsa", "ASd");
        RestClient.instance().request(Method.POST, Api.LOGIN, Sub.NONE, JsonParser.fromJsonElement(params),
                new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        JsonElement body = response.body();
                        if (body != null && body.isJsonObject()) {
                            String json = body.getAsJsonObject().toString();
                            try {
                                LoginModel loginModel = JsonParser.fromJson(json, LoginModel.class);
                                RestClient.instance().returnSuccess(loginModel, singleResultListener, true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        RestClient.instance().returnFail(t, singleResultListener, true);
                    }
                });
    }
}
