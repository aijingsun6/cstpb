package cstpb.util;

import java.util.List;
import java.util.Map;

public interface Exec {

    ExecResult exec(String executable, String workingDir, List<String> args, Map<String, String> env);
}
