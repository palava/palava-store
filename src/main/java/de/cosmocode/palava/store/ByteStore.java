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
import java.nio.ByteBuffer;

/**
 * An extended {@link Store} which allows random access via {@link ByteBuffer}
 * based methods.
 *
 * @author Willi Schoenborn
 */
public interface ByteStore extends Store {

    /**
     * Stores the given {@link ByteBuffer} in this store.
     * 
     * @param buffer the binary data being stored
     * @return a generated identifier, which can be used to retrieve or delete the stored data
     * @throws NullPointerException if buffer is null
     * @throws IOException if saving failed
     */
    String create(ByteBuffer buffer) throws IOException;
    
    /**
     * Stores the given {@link ByteBuffer} using the specified identifier.
     * 
     * @param buffer the binary data being stored
     * @param identifier the identifier being used
     * @throws NullPointerException if stream or identifier is null
     * @throws IllegalStateException if the identifier is already present
     * @throws IOException if saving failed
     */
    void create(ByteBuffer buffer, String identifier) throws IOException;
    
    /**
     * Returns a {@link ByteBuffer} view on the binary data associated with
     * the given identifier.
     * 
     * @param identifier the identifier of the binary data being retrieved
     * @return the {@link ByteBuffer} assicuated with the given identifier
     * @throws NullPointerException if identifier is null
     * @throws IllegalArgumentException if identifier is not valid, according to restrictions
     *         introduced by sub classes
     * @throws IOException if reading failed
     */
    ByteBuffer view(String identifier) throws IOException;
    
}
