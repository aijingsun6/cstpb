package cstpb.util;

import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import cstpb.exception.PBException;

import java.nio.file.Path;

public interface Compile {

    FileDescriptorSet compile(Path... fileOrDir) throws PBException;

}
