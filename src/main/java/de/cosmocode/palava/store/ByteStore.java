package de.cosmocode.palava.store;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface ByteStore {

    String create(ByteBuffer buffer) throws IOException;
    
    ByteBuffer read(String identifier) throws IOException;
    
    void delete(String identifier) throws IOException;
    
}
