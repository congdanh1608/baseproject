package vn.danhtran.baseproject.baserecyclerview;

import android.os.Handler;
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
    protected boolean isLoading = false;
    protected int currentPage = 1;
    protected int maxPage = 0;                          //use if server return total pages load.
    protected boolean isLoadMore = false;               //use if server return loadMore ? true : false.

    public BaseRecyclerViewHandler(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;

        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setOnRefreshListener(this);
    }

    public BaseRecyclerViewHandler() {
    }

    protected void initData() {
        loadData();
    }

    protected void loadData() {
    }

    //use it if don't load data in background.
    protected void addItemsInHandler(final List<T> items) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isRefresh) {
                            isRefresh = false;
                            BaseRecyclerViewHandler.this.items.clear();
                            if (swipeRefreshLayout != null)
                                swipeRefreshLayout.setRefreshing(false);
                        } else {
                            adapter.setProgressMore(false);
                            adapter.setMoreLoading(false);
                        }

                        BaseRecyclerViewHandler.this.items.addAll(items);
                        isLoading = false;
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).run();
    }

    //if  it if load data in background.
    protected void addItems(List<T> items) {
        if (isRefresh) {
            isRefresh = false;
            this.items.clear();
            if (swipeRefreshLayout != null)
                swipeRefreshLayout.setRefreshing(false);
        } else {
            adapter.setProgressMore(false);
            adapter.setMoreLoading(false);
        }

        this.items.addAll(items);
        isLoading = false;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(int position) {
        if ((currentPage < maxPage || isLoadMore) && !isLoading) {
            currentPage++;
            isLoading = true;
            isLoadMore = false;
            adapter.setProgressMore(true);
            adapter.setMoreLoading(true);
            isRefresh = false;
            loadData();
        }
    }


    @Override
    public <T> void onClickItem(T item) {

    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        isLoadMore = false;

        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(true);
        isRefresh = true;
        loadData();
    }
}
