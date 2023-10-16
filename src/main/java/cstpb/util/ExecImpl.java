package cstpb.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ExecImpl implements Exec {

    private static final Logger logger = LoggerFactory.getLogger(ExecImpl.class);

    private static final int EXEC_TIMEOUT = 5000;


    public ExecResult exec(String executable, String workingDir, List<String> args, Map<String, String> env) {
        int code = -1;
        String console = "";
        List<String> command = new ArrayList<>();
        command.add(executable);
        if (!CollectionUtils.isEmpty(args)) {
            command.addAll(args);
        }
        logger.info("{} exec {}", workingDir, String.join(" ", command));
        ProcessBuilder pb = new ProcessBuilder(command);
        if (!StringUtils.isEmpty(workingDir)) {
            File wk = new File(workingDir);
            if (wk.exists()) {
                pb.directory(wk);
            }
        }
        if (!MapUtils.isEmpty(env)) {
            pb.environment().putAll(env);
        }
        pb.redirectErrorStream(true);
        Process proc = null;
        try {
            proc = pb.start();
            console = IOUtils.toString(proc.getInputStream(), StandardCharsets.UTF_8);
            proc.waitFor(EXEC_TIMEOUT, TimeUnit.SECONDS);
            code = proc.exitValue();
            logger.info("console: {}", console);
        } catch (InterruptedException | IOException e) {
            logger.error("exec cmd error", e);
        } finally {
            if (proc != null && proc.isAlive()) {
                proc.destroy();
            }
        }

        return new ExecResultImpl(code, console);
    }

    private static class ExecResultImpl implements ExecResult {

        private final int code;

        private final String console;

        public ExecResultImpl(int code, String console) {
            this.code = code;
            this.console = console;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getConsole() {
            return console;
        }
    }

}
