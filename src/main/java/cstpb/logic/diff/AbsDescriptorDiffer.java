/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.diff;

import com.google.protobuf.DescriptorProtos.EnumValueDescriptorProto;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto;
import com.google.protobuf.DescriptorProtos.MethodDescriptorProto;
import cstpb.logic.model.*;
import cstpb.util.NamedTools;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AbsDescriptorDiffer
 *
 * @author alking
 * @since 2023/10/17
 */
public abstract class AbsDescriptorDiffer implements Differ {

    private DiffMode mode;

    public AbsDescriptorDiffer(DiffMode mode) {
        this.mode = mode;
    }

    @Override
    public DiffMode getMode() {
        return mode;
    }

    protected void diffMessageMap(Map<String, DescriptorProtoHolder> left, Map<String, DescriptorProtoHolder> right, DescriptorDifferContext context) {
        for (Map.Entry<String, DescriptorProtoHolder> entry : left.entrySet()) {
            if (!right.containsKey(entry.getKey())) {
                String msg = String.format("message %s delete", entry.getKey());
                context.addDiffItem(new DiffItem<>(DiffField.MESSAGE, DiffType.DELETE, entry.getValue(), null, msg));
            } else {
                diffMessage(entry.getValue(), right.get(entry.getKey()), context);
            }
        }
        for (Map.Entry<String, DescriptorProtoHolder> entry : right.entrySet()) {
            if (!left.containsKey(entry.getKey())) {
                String msg = String.format("message %s append", entry.getKey());
                context.addDiffItem(new DiffItem<>(DiffField.MESSAGE, DiffType.ADD, null, entry.getValue(), msg));
            }
        }
    }

    private void diffMessage(DescriptorProtoHolder left, DescriptorProtoHolder right, DescriptorDifferContext context) {
        if (context.getDiffedMessage().contains(left.getFullName())) {
            return;
        }

        Map<String, FieldDescriptorProtoHolder> leftMap = buildMessageFieldMap(left);
        Map<String, FieldDescriptorProtoHolder> rightMap = buildMessageFieldMap(right);
        for (Map.Entry<String, FieldDescriptorProtoHolder> entry : leftMap.entrySet()) {
            if (!rightMap.containsKey(entry.getKey())) {
                String msg = String.format("message field %s delete at %s", entry.getKey(), right.getFileName());
                context.addDiffItem(new DiffItem<>(DiffField.MESSAGE_FIELD, DiffType.DELETE, entry.getValue(), null, msg));
            } else {
                diffMessageField(entry.getValue(), rightMap.get(entry.getKey()), context);
            }
        }

        for (Map.Entry<String, FieldDescriptorProtoHolder> entry : rightMap.entrySet()) {
            if (!leftMap.containsKey(entry.getKey())) {
                String msg = String.format("message field %s append at %s", entry.getKey(), right.getFileName());
                context.addDiffItem(new DiffItem<>(DiffField.MESSAGE_FIELD, DiffType.ADD, null, entry.getValue(), msg));
            }
        }


        context.getDiffedMessage().add(left.getFullName());
    }

    protected Map<String, FieldDescriptorProtoHolder> buildMessageFieldMap(DescriptorProtoHolder message) {
        Map<String, FieldDescriptorProtoHolder> map = new HashMap<>();
        List<FieldDescriptorProto> fs = message.getDescriptor().getFieldList();
        for (FieldDescriptorProto field : fs) {
            FieldDescriptorProtoHolder holder = new FieldDescriptorProtoHolder(message.getFileName(), message.getFullName(), field, message.getContext());
            map.put(holder.getFullName(), holder);
        }
        return map;
    }

    private String fieldTypeDisplay(FieldDescriptorProto proto) {
        FieldDescriptorProto.Type type = proto.getType();
        String name = NamedTools.trimName(proto.getTypeName());
        if (FieldDescriptorProto.Type.TYPE_MESSAGE.equals(type)) {
            return name;
        }

        return type.toString();
    }

    private void diffMessageField(FieldDescriptorProtoHolder left, FieldDescriptorProtoHolder right, DescriptorDifferContext context) {

        String name = left.getFullName();
        StringBuilder sb = new StringBuilder("modify message field ")
                .append(name);

        boolean same = true;
        FieldDescriptorProto leftDesc = left.getDescriptor();
        FieldDescriptorProto rightDesc = right.getDescriptor();

        FieldDescriptorProto.Type leftType = leftDesc.getType();
        FieldDescriptorProto.Type rightType = rightDesc.getType();

        String leftTypeName = NamedTools.trimName(leftDesc.getTypeName());
        String rightTypeName = NamedTools.trimName(rightDesc.getTypeName());

        if (!leftType.equals(rightType)) {
            same = false;
            sb.append(String.format(", type %s vs %s", fieldTypeDisplay(leftDesc), fieldTypeDisplay(rightDesc)));
        }

        if (leftType.equals(FieldDescriptorProto.Type.TYPE_MESSAGE) && leftTypeName.equals(rightTypeName)) {
            DescriptorProtoHolder leftFieldMsg = left.getContext().getAllMsg().get(leftTypeName);
            DescriptorProtoHolder rightFieldMsg = right.getContext().getAllMsg().get(rightTypeName);
            diffMessage(leftFieldMsg, rightFieldMsg, context);
        }
        if (leftType.equals(FieldDescriptorProto.Type.TYPE_ENUM) && leftTypeName.equals(rightTypeName)) {
            EnumDescriptorProtoHolder leftEnum = left.getContext().getAllEnum().get(leftTypeName);
            EnumDescriptorProtoHolder rightEnum = right.getContext().getAllEnum().get(rightTypeName);
            diffEnum(leftEnum, rightEnum, context);
        }

        int leftNumber = leftDesc.getNumber();
        int rightNumber = rightDesc.getNumber();
        if (leftNumber != rightNumber) {
            same = false;
            sb.append(String.format(", number %s vs %s", leftNumber, rightNumber));
        }

        FieldDescriptorProto.Label leftLabel = leftDesc.getLabel();
        FieldDescriptorProto.Label rightLabel = rightDesc.getLabel();
        if (!leftLabel.equals(rightLabel)) {
            same = false;
            sb.append(String.format(", label %s vs %s", leftLabel, rightLabel));
        }

        String leftDefault = leftDesc.getDefaultValue();
        String rightDefault = rightDesc.getDefaultValue();
        if (!StringUtils.equals(leftDefault, rightDefault)) {
            same = false;
            sb.append(String.format(", default %s vs %s", leftDefault, rightDefault));
        }

        sb.append(" at ").append(right.getFileName());

        if (!same) {
            context.addDiffItem(new DiffItem<>(DiffField.MESSAGE_FIELD, DiffType.MODIFY, left, right, sb.toString()));
        }
    }


    protected void diffEnumMap(Map<String, EnumDescriptorProtoHolder> left, Map<String, EnumDescriptorProtoHolder> right, DescriptorDifferContext context) {
        for (Map.Entry<String, EnumDescriptorProtoHolder> entry : left.entrySet()) {
            if (!right.containsKey(entry.getKey())) {
                String msg = String.format("enum %s delete", entry.getKey());
                context.addDiffItem(new DiffItem<>(DiffField.ENUM, DiffType.DELETE, entry.getValue(), null, msg));
            } else {
                diffEnum(entry.getValue(), right.get(entry.getKey()), context);
            }
        }
        for (Map.Entry<String, EnumDescriptorProtoHolder> entry : right.entrySet()) {
            if (!left.containsKey(entry.getKey())) {
                String msg = String.format("enum %s append", entry.getKey());
                context.addDiffItem(new DiffItem<>(DiffField.ENUM, DiffType.ADD, null, entry.getValue(), msg));
            }
        }
    }

    private void diffEnum(EnumDescriptorProtoHolder left, EnumDescriptorProtoHolder right, DescriptorDifferContext context) {
        if (context.containsEnum(left.getFullName())) {
            return;
        }
        Map<String, EnumValueDescriptorProtoHolder> leftMap = buildEnumValueMap(left);
        Map<String, EnumValueDescriptorProtoHolder> rightMap = buildEnumValueMap(right);
        for (Map.Entry<String, EnumValueDescriptorProtoHolder> entry : leftMap.entrySet()) {
            if (!rightMap.containsKey(entry.getKey())) {
                String msg = String.format("enum value %s delete at %s", entry.getKey(), right.getFileName());
                context.addDiffItem(new DiffItem<>(DiffField.ENUM_VALUE, DiffType.DELETE, entry.getValue(), null, msg));
            } else {
                diffEnumValue(entry.getValue(), rightMap.get(entry.getKey()), context);
            }
        }
        for (Map.Entry<String, EnumValueDescriptorProtoHolder> entry : rightMap.entrySet()) {
            if (!leftMap.containsKey(entry.getKey())) {
                String msg = String.format("enum value %s append at %s", entry.getKey(), right.getFileName());
                context.addDiffItem(new DiffItem<>(DiffField.ENUM_VALUE, DiffType.ADD, null, entry.getValue(), msg));
            }
        }

        context.addEnum(left.getFullName());
    }

    private Map<String, EnumValueDescriptorProtoHolder> buildEnumValueMap(EnumDescriptorProtoHolder edph) {
        Map<String, EnumValueDescriptorProtoHolder> map = new HashMap<>();
        List<EnumValueDescriptorProto> es = edph.getDescriptor().getValueList();
        for (EnumValueDescriptorProto e : es) {
            EnumValueDescriptorProtoHolder holder = new EnumValueDescriptorProtoHolder(edph.getFileName(), edph.getFullName(), e, edph.getContext());
            map.put(holder.getFullName(), holder);
        }
        return map;
    }

    private void diffEnumValue(EnumValueDescriptorProtoHolder left, EnumValueDescriptorProtoHolder right, DescriptorDifferContext context) {
        int leftNum = left.getDescriptor().getNumber();
        int rightNum = right.getDescriptor().getNumber();
        if (leftNum != rightNum) {
            String msg = String.format("enum value %s, %d vs %d at %s", left.getFullName(), leftNum, rightNum, right.getFileName());
            context.addDiffItem(new DiffItem<>(DiffField.ENUM_VALUE, DiffType.MODIFY, left, right, msg));
        }
    }

    protected void diffServiceMap(Map<String, ServiceDescriptorProtoHolder> left, Map<String, ServiceDescriptorProtoHolder> right, DescriptorDifferContext context) {
        for (Map.Entry<String, ServiceDescriptorProtoHolder> entry : left.entrySet()) {
            if (!right.containsKey(entry.getKey())) {
                String msg = String.format("service %s delete", entry.getKey());
                context.addDiffItem(new DiffItem<>(DiffField.SERVICE, DiffType.DELETE, entry.getValue(), null, msg));
            } else {
                diffService(entry.getValue(), right.get(entry.getKey()), context);
            }

        }

        for (Map.Entry<String, ServiceDescriptorProtoHolder> entry : right.entrySet()) {
            if (!left.containsKey(entry.getKey())) {
                String msg = String.format("service %s append", entry.getKey());
                context.addDiffItem(new DiffItem<>(DiffField.SERVICE, DiffType.ADD, null, entry.getValue(), msg));
            }
        }

    }

    private void diffService(ServiceDescriptorProtoHolder left, ServiceDescriptorProtoHolder right, DescriptorDifferContext context) {
        Map<String, MethodDescriptorProtoHolder> leftMap = buildMethodMap(left);
        Map<String, MethodDescriptorProtoHolder> rightMap = buildMethodMap(right);
        for (Map.Entry<String, MethodDescriptorProtoHolder> entry : leftMap.entrySet()) {
            if (!rightMap.containsKey(entry.getKey())) {
                String msg = String.format("service method %s delete at %s", entry.getKey(), right.getFileName());
                context.addDiffItem(new DiffItem<>(DiffField.SERVICE_METHOD, DiffType.DELETE, entry.getValue(), null, msg));
            } else {
                diffServiceMethod(entry.getValue(), rightMap.get(entry.getKey()), context);
            }

        }
        for (Map.Entry<String, MethodDescriptorProtoHolder> entry : rightMap.entrySet()) {
            if (!leftMap.containsKey(entry.getKey())) {
                String msg = String.format("service method %s append at %s", entry.getKey(), right.getFileName());
                context.addDiffItem(new DiffItem<>(DiffField.SERVICE_METHOD, DiffType.ADD, null, entry.getValue(), msg));
            }
        }

    }

    private Map<String, MethodDescriptorProtoHolder> buildMethodMap(ServiceDescriptorProtoHolder service) {
        Map<String, MethodDescriptorProtoHolder> map = new HashMap<>();
        for (MethodDescriptorProto m : service.getDescriptor().getMethodList()) {
            MethodDescriptorProtoHolder holder = new MethodDescriptorProtoHolder(service.getFileName(), service.getFullName(), m, service.getContext());
            map.put(holder.getFullName(), holder);
        }
        return map;
    }

    private void diffServiceMethod(MethodDescriptorProtoHolder left, MethodDescriptorProtoHolder right, DescriptorDifferContext context) {
        String leftInput = NamedTools.trimName(left.getDescriptor().getInputType());
        String leftOutput = NamedTools.trimName(left.getDescriptor().getOutputType());

        String rightInput = NamedTools.trimName(right.getDescriptor().getInputType());
        String rightOutput = NamedTools.trimName(right.getDescriptor().getOutputType());

        if (leftInput.equals(rightInput) && leftOutput.equals(rightOutput)) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("service method ")
                .append(left.getFullName())
                .append(" modified");
        if (!leftInput.equals(rightInput)) {
            sb.append(String.format(", input %s vs %s", leftInput, rightInput));
        }
        if (!leftOutput.equals(rightOutput)) {
            sb.append(String.format(", output %s vs %s", leftOutput, rightOutput));
        }
        sb.append(" at ").append(right.getFileName());
        context.addDiffItem(new DiffItem<>(DiffField.SERVICE_METHOD, DiffType.MODIFY, left, right, sb.toString()));
    }
}
