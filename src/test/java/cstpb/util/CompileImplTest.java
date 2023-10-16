package cstpb.util;


import org.apache.commons.lang3.StringUtils;
import org.junit.Test;


import java.nio.file.Paths;
import java.util.List;

import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.ServiceDescriptorProto;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto;
import com.google.protobuf.DescriptorProtos.EnumDescriptorProto;
import com.google.protobuf.DescriptorProtos.EnumValueDescriptorProto;
import com.google.protobuf.DescriptorProtos.MethodDescriptorProto;
import com.google.protobuf.Descriptors.Descriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompileImplTest {

    private static final Logger logger = LoggerFactory.getLogger(CompileImplTest.class);

    @Test
    public void compileTest() {
        Exec exec = new ExecImpl();
        Compile compile = new CompileImpl(exec);
        String proto = "src/test/resources/addressbook.proto";
        FileDescriptorSet set = compile.compile(Paths.get(proto));
        //logger.info("{}", set);
        FileDescriptorProto file = set.getFile(0);
        List<DescriptorProto> msgList = file.getMessageTypeList();
        for (DescriptorProto msg : msgList) {
            showMsgType(file.getPackage(), msg);

        }

        List<EnumDescriptorProto> enums = file.getEnumTypeList();
        for(EnumDescriptorProto e: enums){
            displayEnum(file.getPackage(), e);
        }


        List<ServiceDescriptorProto> serviceList = file.getServiceList();
        for (ServiceDescriptorProto svc : serviceList) {
            displayService(file.getPackage(), svc);
        }


    }

    private void showMsgType(String prefix, DescriptorProto msg) {
        if (StringUtils.isEmpty(prefix)) {
            prefix = msg.getName();
        } else {
            prefix = prefix + "." + msg.getName();
        }
        logger.info("{}", prefix);
        List<DescriptorProto> nestedTypes = msg.getNestedTypeList();
        for (DescriptorProto p : nestedTypes) {

            showMsgType(prefix, p);
        }
        List<EnumDescriptorProto> enums = msg.getEnumTypeList();
        for (EnumDescriptorProto e : enums) {
            displayEnum(prefix, e);

        }
        List<FieldDescriptorProto> fields = msg.getFieldList();
        for (FieldDescriptorProto field : fields) {
            displayField(prefix, field);
        }
    }

    private void displayEnum(String prefix, EnumDescriptorProto e){
        if (StringUtils.isEmpty(prefix)) {
            prefix = e.getName();
        } else {
            prefix = prefix + "." + e.getName();
        }
        logger.info("enum: {}", prefix);
        for(EnumValueDescriptorProto ep: e.getValueList()){
            logger.info("{}.{}={}",prefix,ep.getName(),ep.getNumber());
        }
    }

    private void displayField(String prefix, FieldDescriptorProto field) {
        logger.info("field_name: {}, {}", prefix, field.getName());
        logger.info("field_type: {}", field.getType());
        logger.info("field_type_name: {}", field.getTypeName());
        displayDescriptor(field.getDescriptorForType());
        logger.info("field_number:{}", field.getNumber());
        logger.info("field_label:{}", field.getLabel());
        logger.info("field_default:{}", field.getDefaultValue());
    }

    private void displayDescriptor(Descriptor descriptor) {
        logger.info("descriptor_name: {}", descriptor.getName());
        logger.info("descriptor_full_name: {}", descriptor.getFullName());
    }

    private void displayService(String prefix, ServiceDescriptorProto svc) {
        if (StringUtils.isEmpty(prefix)) {
            prefix = svc.getName();
        } else {
            prefix = prefix + "." + svc.getName();
        }
        logger.info("service: {}", prefix);

        List<MethodDescriptorProto> methods = svc.getMethodList();
        for (MethodDescriptorProto m : methods) {
            logger.info("{}", m);
        }
    }
}
