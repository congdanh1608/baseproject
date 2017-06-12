package vn.danhtran.baseproject.baserecyclerview;

import android.support.v4.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilverWolf on 11/06/2017.
 */

public abstract class BaseRecyclerViewHandler<T> implements BaseRecyclerListener, SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout swipeRefreshLayout;
    protected List<T> items = new ArrayList<>();
    protected BaseRecyclerAdapter<T> adapter;
    protected boolean isRefresh = false;

    public BaseRecyclerViewHandler(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;

        swipeRefreshLayout.setOnRefreshListener(this);
        initData();
    }

    protected void initData() {
        //delay
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    swipeRefreshLayout.setRefreshing(false);
                    isRefresh = false;
                } else {
                    adapter.setProgressMore(false);
                    adapter.setMoreLoading(false);
                }
                loadData();
                adapter.notifyDataSetChanged();
            }
        }, 500);
    }

    protected void loadData() {

    }

    @Override
    public void onLoadMore(int position) {
        adapter.setProgressMore(true);
        adapter.setMoreLoading(true);
        isRefresh = false;
        initData();
    }


    @Override
    public <T> void onClickItem(T item) {

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        isRefresh = true;
        items.clear();
        initData();
    }
}
