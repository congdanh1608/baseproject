package vn.danhtran.baseproject;

import java.util.List;

/**
 * Created by SilverWolf on 09/04/2017.
 */

public interface SingleResultListener<T extends Object> {
    void onSuccess(List<T> data);

    void onFailure(Object error);
}
