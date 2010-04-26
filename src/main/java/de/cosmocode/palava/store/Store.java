/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.store;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * A {@link Store} allows storing binary data.
 * Stored data can be accessed using a string identifier.
 *
 * @author Willi Schoenborn
 */
public interface Store {

    /**
     * Stores the given stream in this store.
     * 
     * @param stream the binary data being stored
     * @return a generated identifier, which can be used to retrieve or delete the stored data
     * @throws NullPointerException if stream is null
     * @throws IOException if saving failed
     */
    String create(InputStream stream) throws IOException;
    
    /**
     * Stores the given stream in this store using the specified identifier.
     * 
     * @param stream the binary data being stored
     * @param identifier the identifier being used
     * @throws NullPointerException if stream or identifier is null
     * @throws IllegalStateException if the identifier is already present
     * @throws IOException if saving failed
     */
    void create(InputStream stream, String identifier) throws IOException;
    
    /**
     * Retrieves the binary data for a given identifier.
     * 
     * @param identifier the identifier of the binary data being retrieved
     * @return the InputStream assicuated with the given identifier
     * @throws NullPointerException if identifier is null
     * @throws IllegalArgumentException if identifier is not valid, according to restrictions
     *         introduced by sub classes
     * @throws IllegalStateException if the identifier is not present
     * @throws IOException if reading failed
     */
    InputStream read(String identifier) throws IOException;
    
    /**
     * Provides a set of all identifiers currently present in this store.
     * Implementations are free to return a copy or an immutable live view.
     * 
     * @return a set of all identifiers
     * @throws IOException if reading failed
     */
    Set<String> list() throws IOException; 
    
    /**
     * Removes binary data from this store.
     * 
     * @param identifier the identifier of the data being deleted.
     * @throws NullPointerException if identifier is null
     * @throws IllegalArgumentException if identifier is not valid, according to restrictions
     *         introduced by sub classes
     * @throws IllegalStateException if the identifier is not present
     * @throws IOException if deletion failed, caused by unknown identifier or general io issues
     */
    void delete(String identifier) throws IOException;
    
}
