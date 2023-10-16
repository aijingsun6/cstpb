/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.model;

import com.google.protobuf.DescriptorProtos.EnumValueDescriptorProto;
import cstpb.logic.DescriptorContext;

/**
 * EnumValueDescriptorProtoHolder
 *
 * @author alking
 * @since 2023/10/17
 */
public class EnumValueDescriptorProtoHolder extends AbsDescriptorHolder<EnumValueDescriptorProto> {

    public EnumValueDescriptorProtoHolder(String fileName, String prefix, EnumValueDescriptorProto value, DescriptorContext context) {
        super(fileName, prefix, value, context);
    }

    @Override
    protected String getName(EnumValueDescriptorProto value) {
        return value.getName();
    }
}
