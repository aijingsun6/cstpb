/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.diff;
import cstpb.logic.DescriptorContext;
import cstpb.logic.model.DescriptorHolder;

import java.util.List;

/**
 * Differ
 *
 * @author alking
 * @since 2023/10/17
 */
public interface Differ {

    DiffMode getMode();

    List<DiffItem<DescriptorHolder>> diff(DescriptorContext left, DescriptorContext right);

}
