package cstpb.util;

import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import cstpb.exception.PBException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CompileImpl implements Compile {

    private static final String PROTOC = "protoc";

    private static final String PROTO_EXT = ".proto";

    private final Exec exec;

    public CompileImpl(Exec exec) {
        this.exec = exec;
    }

    private Path randDescriptorFile() {
        return Paths.get(String.format("%s.bin", UUID.randomUUID()));
    }

    @Override
    public FileDescriptorSet compile(Path... fileOrDir) throws PBException {
        return compile(Arrays.stream(fileOrDir).collect(Collectors.toList()));
    }

    @Override
    public FileDescriptorSet compile(List<Path> files) throws PBException {
        Set<Path> input = new HashSet<>();
        for (Path file : files) {
            try {
                dfs(file, input);
            } catch (IOException e) {
                throw new PBException(e);
            }
        }
        Path descriptor = randDescriptorFile();
        List<String> args = new ArrayList<>();
        args.add("--descriptor_set_out=" + descriptor);

        for (Path file : input) {
            args.add(file.toString());
        }

        ExecResult result = exec.exec(PROTOC, null, args, null);
        if (result.getCode() != 0) {
            throw new PBException(result.getConsole());
        }
        try {
            return FileDescriptorSet.parseFrom(Files.newInputStream(descriptor));
        } catch (IOException e) {
            throw new PBException(e);
        } finally {
            try {
                Files.deleteIfExists(descriptor);
            } catch (IOException e) {
            }
        }
    }

    private void dfs(Path file, Set<Path> acc) throws IOException {
        if (Files.isRegularFile(file) && file.toString().endsWith(PROTO_EXT)) {
           acc.add(file);
            return;
        }
        if (Files.isDirectory(file)) {
            for (Path p : Files.list(file).collect(Collectors.toList())) {
                dfs(p, acc);
            }
        }
    }

}
