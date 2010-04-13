package de.cosmocode.palava.store;

/**
 * A id generation strategy used by {@link Store} implementations to
 * generate new identifiers.
 *
 * @author Willi Schoenborn
 */
public interface IdGenerator {

    /**
     * Generates a new identifier.
     * 
     * @return a new identifier
     */
    String generate();
    
}
