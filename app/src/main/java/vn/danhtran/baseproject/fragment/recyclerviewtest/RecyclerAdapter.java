package vn.danhtran.baseproject.fragment.recyclerviewtest;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.ViewGroup;

import java.util.List;

import vn.danhtran.baseproject.R;
import vn.danhtran.baseproject.databinding.PsychicItemBinding;
import vn.danhtran.baseproject.baserecyclerview.BaseRecyclerAdapter;
import vn.danhtran.baseproject.baserecyclerview.BaseRecyclerListener;
import vn.danhtran.baseproject.baserecyclerview.BindingViewHolder;
import vn.danhtran.baseproject.fragment.recyclerviewtest.item.PsychicItemData;
import vn.danhtran.baseproject.fragment.recyclerviewtest.item.PsychicItemVM;

/**
 * Created by SilverWolf on 10/06/2017.
 */

public class RecyclerAdapter extends BaseRecyclerAdapter<ItemModel> {
    private final int VIEW_ITEM = 1;

    public RecyclerAdapter(Context context, List<ItemModel> items, BaseRecyclerListener listener) {
        super(context, listener);
        this.items = items;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_PROG) {
            return new BindingViewHolder(
                    DataBindingUtil.inflate(mLayoutInflater, R.layout.item_process_bar, parent, false));
        }
        return new BindingViewHolder(
                DataBindingUtil.inflate(mLayoutInflater, R.layout.psychic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position) != VIEW_PROG) {
            PsychicItemBinding binding = (PsychicItemBinding) holder.getBinding();
            PsychicItemData psychicItemData = new PsychicItemData(items.get(position));
            PsychicItemVM psychicItemVM = new PsychicItemVM(psychicItemData);
            binding.setHandler(psychicItemVM);
            binding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) != null) {
            return VIEW_ITEM;
        } else
            return VIEW_PROG;
    }

    public void add(ItemModel viewModel) {
        items.add(viewModel);
        notifyDataSetChanged();
    }

    public void add(int position, ItemModel viewModel) {
        items.add(position, viewModel);
        notifyDataSetChanged();
    }

    public void set(List<ItemModel> viewModels) {
        items.clear();
        addAll(viewModels);
    }

    public void addAll(List<ItemModel> viewModels) {
        items.addAll(viewModels);
        notifyDataSetChanged();
    }
}