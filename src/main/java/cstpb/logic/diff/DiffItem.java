/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.diff;

import cstpb.logic.model.DescriptorHolder;

/**
 * DiffResult
 *
 * @author alking
 * @since 2023/10/17
 */
public class DiffItem<T extends DescriptorHolder> {
    private DiffField field;
    private DiffType type;
    private T left;
    private T right;
    private String msg;
    private Object ext;

    public DiffItem(DiffField field, DiffType type,T left, T right, String msg) {
        this.field = field;
        this.type = type;
        this.left = left;
        this.right = right;
        this.msg = msg;
    }

    public DiffField getField() {
        return field;
    }

    public void setField(DiffField field) {
        this.field = field;
    }

    public DiffType getType() {
        return type;
    }

    public void setType(DiffType type) {
        this.type = type;
    }

    public T getLeft() {
        return left;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public T getRight() {
        return right;
    }

    public void setRight(T right) {
        this.right = right;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }
}
