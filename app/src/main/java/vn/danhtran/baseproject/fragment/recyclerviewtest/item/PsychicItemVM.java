package vn.danhtran.baseproject.fragment.recyclerviewtest.item;

/**
 * Created by vitcon on 2/23/16.
 */
public class PsychicItemVM {
    private PsychicItemData psychicItemData;

    public PsychicItemVM(PsychicItemData psychicItemData) {
        this.psychicItemData = psychicItemData;
    }

    public PsychicItemData getPsychicItemData() {
        return psychicItemData;
    }

    public void setPsychicItemData(PsychicItemData psychicItemData) {
        this.psychicItemData = psychicItemData;
    }
}
