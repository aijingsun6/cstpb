/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic;

import com.google.protobuf.DescriptorProtos.*;
import cstpb.logic.model.DescriptorProtoHolder;
import cstpb.logic.model.EnumDescriptorProtoHolder;
import cstpb.logic.model.ServiceDescriptorProtoHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * DescriptorRepo
 *
 * @author alking
 * @since 2023/10/17
 */
public class DescriptorContext {

    private final Map<String, DescriptorProtoHolder> rootMsg = new HashMap<>();

    private final Map<String, DescriptorProtoHolder> allMsg = new HashMap<>();

    private final Map<String, EnumDescriptorProtoHolder> rootEnum = new HashMap<>();

    private final Map<String, EnumDescriptorProtoHolder> allEnum = new HashMap<>();

    private final Map<String, ServiceDescriptorProtoHolder> service = new HashMap<>();

    public Map<String, DescriptorProtoHolder> getRootMsg() {
        return rootMsg;
    }

    public Map<String, DescriptorProtoHolder> getAllMsg() {
        return allMsg;
    }

    public Map<String, EnumDescriptorProtoHolder> getRootEnum() {
        return rootEnum;
    }

    public Map<String, EnumDescriptorProtoHolder> getAllEnum() {
        return allEnum;
    }

    public Map<String, ServiceDescriptorProtoHolder> getService() {
        return service;
    }

    public DescriptorContext() {

    }

    public void addFileSet(FileDescriptorSet fs) {
        for (FileDescriptorProto file : fs.getFileList()) {
            addFile(file);
        }
    }

    public void addFile(FileDescriptorProto file) {
        final String pkg = file.getPackage();
        for (DescriptorProto msg : file.getMessageTypeList()) {
            addMsg(pkg, file, msg, true);
        }
        for (EnumDescriptorProto e : file.getEnumTypeList()) {
            addEnum(pkg, file, e, true);
        }
        for (ServiceDescriptorProto s : file.getServiceList()) {
            addService(pkg, file, s);
        }
    }

    private void addMsg(String prefix, FileDescriptorProto file, DescriptorProto msg, boolean root) {
        DescriptorProtoHolder holder = new DescriptorProtoHolder(file.getName(), prefix, msg, this);
        String name = holder.getFullName();
        if (root) {
            rootMsg.put(name, holder);
        }
        allMsg.put(name, holder);
        for (DescriptorProto nested : msg.getNestedTypeList()) {
            addMsg(name, file, nested, false);
        }
        for (EnumDescriptorProto e : msg.getEnumTypeList()) {
            addEnum(name, file, e, false);
        }
    }

    private void addEnum(String prefix, FileDescriptorProto file, EnumDescriptorProto e, boolean root) {
        EnumDescriptorProtoHolder holder = new EnumDescriptorProtoHolder(file.getName(), prefix, e, this);
        String name = holder.getFullName();
        if (root) {
            rootEnum.put(name, holder);
        }
        allEnum.put(name, holder);
    }

    private void addService(String prefix, FileDescriptorProto file, ServiceDescriptorProto s) {
        ServiceDescriptorProtoHolder holder = new ServiceDescriptorProtoHolder(file.getName(), prefix, s, this);
        String name = holder.getFullName();
        service.put(name, holder);
    }
}
