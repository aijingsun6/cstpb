/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.model;

import com.google.protobuf.GeneratedMessageV3;
import cstpb.logic.DescriptorContext;
import cstpb.util.NamedTools;

/**
 * AbsDescriptorHolder
 *
 * @author alking
 * @since 2023/10/17
 */
public abstract class AbsDescriptorHolder<T extends GeneratedMessageV3> implements DescriptorHolder<T> {

    private String fileName;

    private String simpleName;

    private String fullName;

    private T value;

    private DescriptorContext context;

    public AbsDescriptorHolder(String fileName, String prefix, T value, DescriptorContext context) {
        this.fileName = fileName;
        this.value = value;
        this.context = context;
        this.simpleName = getName(value);
        this.fullName = NamedTools.fullName(prefix, this.simpleName);
    }

    protected abstract String getName(T t);

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public T getDescriptor() {
        return value;
    }

    @Override
    public DescriptorContext getContext() {
        return this.context;
    }

}
