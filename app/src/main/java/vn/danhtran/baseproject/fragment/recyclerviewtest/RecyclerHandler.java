package vn.danhtran.baseproject.fragment.recyclerviewtest;

import android.support.v4.widget.SwipeRefreshLayout;

import vn.danhtran.baseproject.activity.BaseAppCompatActivity;
import vn.danhtran.baseproject.fragment.BaseFragment;
import vn.danhtran.baseproject.baserecyclerview.BaseRecyclerAdapter;
import vn.danhtran.baseproject.baserecyclerview.BaseRecyclerListener;
import vn.danhtran.baseproject.baserecyclerview.BaseRecyclerViewHandler;

/**
 * Created by SilverWolf on 10/06/2017.
 */

public class RecyclerHandler extends BaseRecyclerViewHandler<ItemModel> implements BaseRecyclerListener {
    private BaseAppCompatActivity activity;
    private BaseFragment fragment;

    public RecyclerHandler(SwipeRefreshLayout swipeRefreshLayout, BaseFragment fragment) {
        super(swipeRefreshLayout);
        this.fragment = fragment;
        this.activity = fragment.getBaseActivity();

        adapter = new RecyclerAdapter(fragment.getContext(), items, this);

        initData();
    }

    public BaseRecyclerAdapter<ItemModel> setAdapter() {
        return adapter;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void loadData() {
        super.loadData();
        items.add(new ItemModel("Ton Duc Thang"));
        items.add(new ItemModel("Vo Van Kiet"));
        items.add(new ItemModel("Vo Thi Sau"));
        items.add(new ItemModel("Bach Dang"));
        items.add(new ItemModel("Vo Nguyen Giap"));
        items.add(new ItemModel("Nguyen Hue"));
        items.add(new ItemModel("Ho Hoc Lam"));
        items.add(new ItemModel("Ly Thuong Kiet"));
        items.add(new ItemModel("Tran Nao"));
    }

    @Override
    public void onLoadMore(int position) {
        super.onLoadMore(position);
    }

    @Override
    public <T> void onClickItem(T item) {
        super.onClickItem(item);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
    }
}
