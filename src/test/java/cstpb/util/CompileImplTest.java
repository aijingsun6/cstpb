package cstpb.util;

import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompileImplTest {

    private static final Logger logger = LoggerFactory.getLogger(CompileImplTest.class);

    @Test
    public void compileTest() {
        Exec exec = new ExecImpl();
        Compile compile = new CompileImpl(exec);

        // String file = getClass().getClassLoader().getResource("addressbook.proto").getFile();
        String file = "src/test/resources/addressbook.proto";
        FileDescriptorSet set = compile.compile(Paths.get(file));
        logger.info("{}", set);

    }
}
