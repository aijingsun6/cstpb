/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.model;

import com.google.protobuf.DescriptorProtos.FieldDescriptorProto;
import cstpb.logic.DescriptorContext;

/**
 * FieldDescriptorProtoHolder
 *
 * @author alking
 * @since 2023/10/17
 */
public class FieldDescriptorProtoHolder extends AbsDescriptorHolder<FieldDescriptorProto> {

    public FieldDescriptorProtoHolder(String fileName, String prefix, FieldDescriptorProto value, DescriptorContext context) {
        super(fileName, prefix, value, context);
    }

    @Override
    protected String getName(FieldDescriptorProto field) {
        return field.getName();
    }
}
