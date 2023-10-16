/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.model;

import com.google.protobuf.DescriptorProtos.ServiceDescriptorProto;
import cstpb.logic.DescriptorContext;

/**
 * ServiceDescriptorProtoHolder
 *
 * @author alking
 * @since 2023/10/17
 */
public class ServiceDescriptorProtoHolder extends AbsDescriptorHolder<ServiceDescriptorProto> {

    public ServiceDescriptorProtoHolder(String fileName, String prefix, ServiceDescriptorProto value, DescriptorContext context) {
        super(fileName, prefix, value, context);
    }

    @Override
    protected String getName(ServiceDescriptorProto serviceDescriptorProto) {
        return serviceDescriptorProto.getName();
    }
}
