/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.model;

import com.google.protobuf.DescriptorProtos.MethodDescriptorProto;
import cstpb.logic.DescriptorContext;

/**
 * MethodDescriptorProtoHolder
 *
 * @author alking
 * @since 2023/10/17
 */
public class MethodDescriptorProtoHolder extends AbsDescriptorHolder<MethodDescriptorProto> {

    public MethodDescriptorProtoHolder(String fileName, String prefix, MethodDescriptorProto value, DescriptorContext context) {
        super(fileName, prefix, value, context);
    }

    @Override
    protected String getName(MethodDescriptorProto methodDescriptorProto) {
        return methodDescriptorProto.getName();
    }
}
