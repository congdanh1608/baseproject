package vn.danhtran.baseproject.serverAPI.apiservice;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.danhtran.baseproject.SingleResultListener;
import vn.danhtran.baseproject.receiver.ErrorReceiver;
import vn.danhtran.baseproject.serverAPI.models.ErrorModel;


/**
 * Created by danhtran on 14/02/2017.
 */
public class BaseService {
    //default send error
    public <T extends ErrorModel> void getSchedulers(Observable<T> observable, final SingleResultListener<T> singleResultListene) {
        getSchedulers(observable, singleResultListene, true);
    }

    //is send error?
    public <T extends ErrorModel> void getSchedulers(Observable<T> observable, final SingleResultListener<T> singleResultListener,
                                                     final boolean isSendError) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        errorModel -> handleResponse(errorModel, singleResultListener, isSendError),
                        throwable -> handleError(throwable, singleResultListener, isSendError));
    }

    //handler response with default send error
    public <T extends ErrorModel> void handleResponse(T data, SingleResultListener<T> singleResultListener) {
        returnSuccess(data, singleResultListener, true);
    }

    //handler response with is send error?
    public <T extends ErrorModel> void handleResponse(T data, SingleResultListener<T> singleResultListener, boolean isSendError) {
        returnSuccess(data, singleResultListener, isSendError);
    }

    //handler error with default send error
    public void handleError(Throwable error, SingleResultListener singleResultListener) {
        returnFail(error, singleResultListener, true);
    }

    //handler error with is send error?
    public void handleError(Throwable error, SingleResultListener singleResultListener, boolean isSendError) {
        returnFail(error, singleResultListener, isSendError);
    }

    //return with default send error
    protected <T> void returnFail(Object error, SingleResultListener<T> singleResultListener) {
        returnFail(error, singleResultListener, true);
    }

    //return with is send error?
    protected <T> void returnFail(Object error, SingleResultListener<T> singleResultListener, boolean isSendError) {
        singleResultListener.onFailure(error);
        if (isSendError)
            ErrorReceiver.instance().broadcastIntent(error);
    }

    //return with default send error
    protected <T extends ErrorModel> void returnSuccess(T data, SingleResultListener<T> singleResultListener) {
        returnSuccess(data, singleResultListener, true);
    }

    //return with is send error?
    protected <T extends ErrorModel> void returnSuccess(T data, SingleResultListener<T> singleResultListener, boolean isSendError) {
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

    //check error from server
    protected boolean checkFailServer(ErrorModel errorModel) {
        if (errorModel.getError().getCode() == 0)
            return false;
        return true;
    }
}
