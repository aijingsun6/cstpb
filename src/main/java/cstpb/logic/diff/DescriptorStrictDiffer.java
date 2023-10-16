package cstpb.logic.diff;

import cstpb.logic.DescriptorContext;
import cstpb.logic.model.DescriptorHolder;

import java.util.List;

public class DescriptorStrictDiffer extends AbsDescriptorDiffer {
    public DescriptorStrictDiffer() {
        super(DiffMode.STRICT);
    }

    @Override
    public List<DiffItem<DescriptorHolder>> diff(DescriptorContext left, DescriptorContext right) {
        DescriptorDifferContext context = new DescriptorDifferContext();
        diffMessageMap(left.getAllMsg(), right.getAllMsg(), context);
        diffEnumMap(left.getAllEnum(), right.getAllEnum(), context);
        diffServiceMap(left.getService(), right.getService(), context);
        return context.getAcc();
    }

}
