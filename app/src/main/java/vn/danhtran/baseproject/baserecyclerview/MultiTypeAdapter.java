package vn.danhtran.baseproject.baserecyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.v4.util.ArrayMap;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vn.danhtran.baseproject.R;

/**
 * Created by SilverWolf on 10/06/2017.
 */

public class MultiTypeAdapter extends BaseRecyclerAdapter<Object> {
    private ArrayList<Integer> itemViewType;
    private int VIEW_ITEM = 1;
    private ArrayMap<Integer, Integer> mItemTypeToLayoutMap = new ArrayMap<>();

    public MultiTypeAdapter(Context context, BaseRecyclerListener listener) {
        this(context, null, listener);
    }

    public MultiTypeAdapter(Context context, Map<Integer, Integer> viewTypeToLayoutMap, BaseRecyclerListener listener) {
        super(context, listener);
        items = new ArrayList<>();
        itemViewType = new ArrayList<>();
        if (viewTypeToLayoutMap != null && !viewTypeToLayoutMap.isEmpty()) {
            mItemTypeToLayoutMap.putAll(viewTypeToLayoutMap);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_PROG) {
            return new BindingViewHolder(
                    DataBindingUtil.inflate(mLayoutInflater, R.layout.item_process_bar, parent, false));
        }
        return new BindingViewHolder(
                DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(viewType), parent, false));
    }

    public void addViewTypeToLayoutMap(Integer viewType, Integer layoutRes) {
        mItemTypeToLayoutMap.put(viewType, layoutRes);
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) != null) {
            return VIEW_ITEM;
        } else
            return VIEW_PROG;
    }

    public void set(List viewModels, int viewType) {
        items.clear();
        itemViewType.clear();
        if (viewModels == null) {
            add(null, viewType);
        } else {
            addAll(viewModels, viewType);
        }
    }

    public void set(List viewModels, MultiViewTyper viewTyper) {
        items.clear();
        itemViewType.clear();
        addAll(viewModels, viewTyper);
    }

    public void add(Object viewModel, int viewType) {
        items.add(viewModel);
        itemViewType.add(viewType);
        notifyItemInserted(0);
    }

    public void add(int position, Object viewModel, int viewType) {
        items.add(position, viewModel);
        itemViewType.add(position, viewType);
        notifyItemInserted(position);
    }

    public void addAll(List viewModels, int viewType) {
        items.addAll(viewModels);
        for (int i = 0; i < viewModels.size(); ++i) {
            itemViewType.add(viewType);
        }
        notifyDataSetChanged();
    }

    public void addAll(int position, List viewModels, int viewType) {
        items.addAll(position, viewModels);
        for (int i = 0; i < viewModels.size(); i++) {
            itemViewType.add(position + i, viewType);
        }
        notifyItemRangeChanged(position, viewModels.size() - position);
    }

    public void addAll(List viewModels, MultiViewTyper multiViewTyper) {
        items.addAll(viewModels);
        for (int i = 0; i < viewModels.size(); ++i) {
            itemViewType.add(multiViewTyper.getViewType(viewModels.get(i)));
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        itemViewType.remove(position);
        super.remove(position);
    }

    public void clear() {
        itemViewType.clear();
        super.clear();
    }

    @LayoutRes
    protected int getLayoutRes(int viewType) {
        return mItemTypeToLayoutMap.get(viewType);
    }

    public interface MultiViewTyper {
        int getViewType(Object item);
    }
}
