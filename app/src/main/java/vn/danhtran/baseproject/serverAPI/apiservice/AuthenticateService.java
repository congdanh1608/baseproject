package vn.danhtran.baseproject.serverAPI.apiservice;


import com.google.gson.JsonElement;
import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.danhtran.baseproject.SingleResultListener;
import vn.danhtran.baseproject.enums.Api;
import vn.danhtran.baseproject.enums.Method;
import vn.danhtran.baseproject.enums.Sub;
import vn.danhtran.baseproject.serverAPI.RestClient;
import vn.danhtran.baseproject.serverAPI.apiconfig.APIServer;
import vn.danhtran.baseproject.serverAPI.json.JsonParser;
import vn.danhtran.baseproject.serverAPI.models.LoginModel;

/**
 * Created by danhtran on 14/02/2017.
 */
public class AuthenticateService extends BaseService {
    private static AuthenticateService authenticateService;
    private APIServer apiServer = RestClient.instance().getHttpClient();

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
        params.put("phone", "0900000000");
        params.put("password", "123456789");
        Observable<LoginModel> callShare = apiServer.post("", "", JsonParser.fromJsonElement(params));
        getSchedulers(callShare, singleResultListener);
    }

    //demo use observable with custom functions in data received
    public void authenticate2(SingleResultListener<LoginModel> singleResultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", "0900000000");
        params.put("password", "123456789");
        Observable<LoginModel> callShare = apiServer.post("", "", JsonParser.fromJsonElement(params));
        callShare.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(errorModel -> {
                            //Custom functions.
                            Logger.d("Custom functions!");
                            handleResponse(errorModel, singleResultListener);
                        }
                        , throwable -> handleError(throwable, singleResultListener));
    }

    //demo use enqueue -> parse json to model by manual
    public void authenticate3(SingleResultListener<LoginModel> singleResultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", "0900000000");
        params.put("password", "123456789");
        RestClient.instance().request(Method.POST, Api.LOGIN, Sub.NONE, JsonParser.fromJsonElement(params),
                new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        JsonElement body = response.body();
                        if (body != null && body.isJsonObject()) {
                            String json = body.getAsJsonObject().toString();
                            try {
                                LoginModel loginModel = JsonParser.fromJson(json, LoginModel.class);
                                returnSuccess(loginModel, singleResultListener);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        returnFail(t, singleResultListener);
                    }
                });
    }
}
