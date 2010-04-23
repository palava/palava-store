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
