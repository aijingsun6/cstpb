package cstpb.util;

import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import cstpb.exception.PBException;

import java.nio.file.Path;
import java.util.List;

public interface Compile {

    FileDescriptorSet compile(Path... fileOrDir) throws PBException;

    FileDescriptorSet compile(List<Path> files) throws PBException;

}
