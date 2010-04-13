/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
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
