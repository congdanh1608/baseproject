package vn.danhtran.baseproject.fragment.recyclerviewtest.item;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import vn.danhtran.baseproject.BR;
import vn.danhtran.baseproject.fragment.recyclerviewtest.ItemModel;

/**
 * Created by vitcon on 2/23/16.
 */
public class PsychicItemData extends BaseObservable {
    private ItemModel psychic;

    public PsychicItemData(ItemModel psychic) {
        this.psychic = psychic;
    }

    @Bindable
    public ItemModel getPsychic() {
        return psychic;
    }

    public void setPsychic(ItemModel psychic) {
        this.psychic = psychic;
        notifyPropertyChanged(BR.psychic);
    }
}
