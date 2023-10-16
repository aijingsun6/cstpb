/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.model;

import com.google.protobuf.DescriptorProtos.EnumDescriptorProto;
import cstpb.logic.DescriptorContext;

/**
 * EnumDescriptorProtoHolder
 *
 * @author alking
 * @since 2023/10/17
 */
public class EnumDescriptorProtoHolder extends AbsDescriptorHolder<EnumDescriptorProto> {

    public EnumDescriptorProtoHolder(String fileName, String prefix, EnumDescriptorProto value, DescriptorContext context) {
        super(fileName, prefix, value, context);
    }

    @Override
    protected String getName(EnumDescriptorProto enumDescriptorProto) {
        return enumDescriptorProto.getName();
    }
}
