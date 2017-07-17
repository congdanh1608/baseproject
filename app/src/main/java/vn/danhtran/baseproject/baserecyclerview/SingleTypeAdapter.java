package vn.danhtran.baseproject.baserecyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.danhtran.baseproject.R;

/**
 * Created by SilverWolf on 10/06/2017.
 */

public class SingleTypeAdapter<T> extends BaseRecyclerAdapter<T> {
    protected int mLayoutRes;

    public SingleTypeAdapter(Context context, BaseRecyclerListener listener) {
        this(context, 0, listener);
    }

    public SingleTypeAdapter(Context context, int layoutRes, BaseRecyclerListener listener) {
        super(context, listener);
        items = new ArrayList<>();
        mLayoutRes = layoutRes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_PROG) {
            return new BindingViewHolder(
                    DataBindingUtil.inflate(mLayoutInflater, R.layout.item_process_bar, parent, false));
        }
        return new BindingViewHolder(
                DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(), parent, false));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) != VIEW_PROG) {
//            holder.getBinding().setVariable()
            holder.getBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(T viewModel) {
        items.add(viewModel);
        notifyDataSetChanged();
    }

    public void add(int position, T viewModel) {
        items.add(position, viewModel);
        notifyDataSetChanged();
    }

    public void set(List<T> viewModels) {
        items.clear();
        addAll(viewModels);
    }

    public void addAll(List<T> viewModels) {
        items.addAll(viewModels);
        notifyDataSetChanged();
    }

    @LayoutRes
    protected int getLayoutRes() {
        return mLayoutRes;
    }
}
