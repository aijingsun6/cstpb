/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.model;

import com.google.protobuf.GeneratedMessageV3;
import cstpb.logic.DescriptorContext;

/**
 * DescriptorMask
 *
 * @author alking
 * @since 2023/10/17
 */
public interface DescriptorHolder<T extends GeneratedMessageV3> {

    String getFileName();

    String getFullName();

    String getSimpleName();

    T getDescriptor();

    DescriptorContext getContext();
}
