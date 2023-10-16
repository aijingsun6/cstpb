/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.diff;

import cstpb.logic.DescriptorContext;
import cstpb.logic.model.DescriptorHolder;

import java.util.List;

/**
 * diff
 *
 * @author alking
 * @since 2023/10/17
 */
public class DescriptorNormalDiffer extends AbsDescriptorDiffer {

    public DescriptorNormalDiffer() {
        super(DiffMode.NORMAL);
    }

    @Override
    public DiffMode getMode() {
        return DiffMode.NORMAL;
    }

    @Override
    public List<DiffItem<DescriptorHolder>> diff(DescriptorContext left, DescriptorContext right) {
        DescriptorDifferContext context = new DescriptorDifferContext();
        diffMessageMap(left.getRootMsg(), right.getRootMsg(), context);
        diffEnumMap(left.getRootEnum(), right.getRootEnum(), context);
        diffServiceMap(left.getService(), right.getService(), context);
        return context.getAcc();
    }

}
