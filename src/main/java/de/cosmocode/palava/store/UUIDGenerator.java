package de.cosmocode.palava.store;

import java.util.UUID;

/**
 * {@link UUID} based implementation of the {@link IdGenerator} interface.
 *
 * @author Willi Schoenborn
 */
public final class UUIDGenerator implements IdGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }

}
