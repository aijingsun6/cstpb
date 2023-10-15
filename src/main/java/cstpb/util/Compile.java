package cstpb.util;

import cstpb.exception.PBException;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;

import java.nio.file.Path;

public interface Compile {

    FileDescriptorSet compile(Path... fileOrDir) throws PBException;

}
