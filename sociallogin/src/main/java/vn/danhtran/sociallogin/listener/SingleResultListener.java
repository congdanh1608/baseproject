package vn.danhtran.sociallogin.listener;

/**
 * Created by SilverWolf on 05/04/2017.
 */
public interface SingleResultListener<T extends Object> {
    void onSuccess(T datas);

    void onFailure(T data, Object error);
}
