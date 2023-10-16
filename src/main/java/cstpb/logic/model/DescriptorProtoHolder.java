/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.model;

import com.google.protobuf.DescriptorProtos.DescriptorProto;
import cstpb.logic.DescriptorContext;

/**
 * DescriptorProtoHolder
 *
 * @author alking
 * @since 2023/10/17
 */
public class DescriptorProtoHolder extends AbsDescriptorHolder<DescriptorProto> {

    public DescriptorProtoHolder(String fileName, String prefix, DescriptorProto value, DescriptorContext context) {
        super(fileName, prefix, value, context);
    }

    @Override
    protected String getName(DescriptorProto descriptorProto) {
        return descriptorProto.getName();
    }
}
