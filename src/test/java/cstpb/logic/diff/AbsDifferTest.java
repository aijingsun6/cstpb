/*
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package cstpb.logic.diff;

import cstpb.logic.DescriptorContext;
import cstpb.logic.model.DescriptorHolder;
import cstpb.util.Compile;
import cstpb.util.CompileImpl;
import cstpb.util.Exec;
import cstpb.util.ExecImpl;

import java.nio.file.Paths;
import java.util.List;

/**
 * AbsDifferTest
 *
 * @author alking
 * @since 2023/10/18
 */
public abstract class AbsDifferTest {

    protected abstract Differ getDiffer();

    protected List<DiffItem<DescriptorHolder>> diffFiles(String left, String right) {
        Exec exec = new ExecImpl();
        Compile compile = new CompileImpl(exec);

        DescriptorContext context1 = new DescriptorContext();
        context1.addFileSet(compile.compile(Paths.get(left)));

        DescriptorContext context2 = new DescriptorContext();
        context2.addFileSet(compile.compile(Paths.get(right)));

        Differ differ = getDiffer();
        return differ.diff(context1, context2);
    }
}
