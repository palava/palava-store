package de.cosmocode.palava.store;

import java.util.UUID;

/**
 * {@link UUID} based implementation of the {@link IdGenerator} interface
 * which removes dashes (-) from the uuid.
 *
 * @author Willi Schoenborn
 */
public final class UUIDBaseGenerator implements IdGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
