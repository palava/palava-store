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
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.google.common.base.Preconditions;

import de.cosmocode.commons.io.ByteBuffers;

/**
 * Abstract base implementation of the {@link ByteStore} interface
 * which provides implementations for {@link Store#read(String)} and {@link ByteStore#create(ByteBuffer)}.
 *
 * @author Willi Schoenborn
 */
public abstract class AbstractByteStore implements ByteStore {

    @Override
    public String create(ByteBuffer buffer) throws IOException {
        Preconditions.checkNotNull(buffer, "Buffer");
        final InputStream stream = ByteBuffers.asInputStream(buffer);
        return create(stream);
    }
    
    @Override
    public void create(ByteBuffer buffer, String identifier) throws IOException {
        Preconditions.checkNotNull(buffer, "Buffer");
        Preconditions.checkNotNull(identifier, "Identifier");
        final InputStream stream = ByteBuffers.asInputStream(buffer);
        create(stream, identifier);
    }
    
    @Override
    public InputStream read(String identifier) throws IOException {
        Preconditions.checkNotNull(identifier, "Identifier");
        final ByteBuffer buffer = view(identifier);
        return ByteBuffers.asInputStream(buffer);
    }

}
