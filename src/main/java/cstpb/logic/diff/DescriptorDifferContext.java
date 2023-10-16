/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.diff;

import cstpb.logic.model.DescriptorHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DescriptorDifferContext
 *
 * @author alking
 * @since 2023/10/17
 */
public class DescriptorDifferContext {

    private final List<DiffItem<DescriptorHolder>> acc = new ArrayList<>();

    private final Set<String> diffedMessage = new HashSet<>();

    private final Set<String> diffedEnum = new HashSet<>();

    public List<DiffItem<DescriptorHolder>> getAcc() {
        return acc;
    }

    public void addDiffItem(DiffItem<DescriptorHolder> item) {
        this.acc.add(item);
    }

    public boolean containsMessage(String fullName) {
        return diffedMessage.contains(fullName);
    }

    public void addMessage(String fullName) {
        this.diffedMessage.add(fullName);
    }

    public Set<String> getDiffedMessage() {
        return diffedMessage;
    }

    public boolean containsEnum(String fullName) {
        return this.diffedEnum.contains(fullName);
    }

    public void addEnum(String fullName) {
        this.diffedEnum.add(fullName);
    }

    public Set<String> getDiffedEnum() {
        return diffedEnum;
    }
}
