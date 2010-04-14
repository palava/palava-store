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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import com.google.inject.internal.Sets;

import de.cosmocode.junit.UnitProvider;

/**
 * Abstract test for {@link Store} implementations.
 *
 * @author Willi Schoenborn
 */
public abstract class AbstractStoreTest implements UnitProvider<Store> {

    private static final String UTF_8 = "UTF-8";
    
    /**
     * Provides a unit which uses the specified generator.
     * 
     * @param generator the {@link IdGenerator} to use
     * @return a {@link Store}
     */
    protected abstract Store unitWithGenerator(IdGenerator generator);
    
    /**
     * Tests {@link Store#create(InputStream)} with a null stream.
     * 
     * @throws IOException should not happen 
     */
    @Test(expected = NullPointerException.class)
    public void createNull() throws IOException {
        unit().create(null);
    }
    
    /**
     * Tests {@link Store#create(InputStream)} with a custom {@link IdGenerator}.
     * 
     * @throws IOException should not happen
     */
    @Test
    public void createCustomGenerator() throws IOException {
        final String identifier = Long.toBinaryString(System.nanoTime());
        final IdGenerator generator = EasyMock.createMock("identifier", IdGenerator.class);
        EasyMock.expect(generator.generate()).andReturn(identifier);
        EasyMock.replay(generator);
        final Store unit = unitWithGenerator(generator);
        final InputStream stream = getClass().getClassLoader().getResourceAsStream("willi.png");
        Assert.assertNotNull(stream);
        final String generatedIdentifier = unit.create(stream);
        Assert.assertEquals(identifier, generatedIdentifier);
        EasyMock.verify(generator);
    }
    
    /**
     * Tests {@link Store#create(InputStream)}.
     * 
     * @throws IOException should not happen
     */
    @Test
    public void create() throws IOException {
        final Store unit = unit();
        final InputStream stream = getClass().getClassLoader().getResourceAsStream("willi.png");
        Assert.assertNotNull(stream);
        final String identifier = unit.create(stream);
        stream.close();
        Assert.assertTrue(IOUtils.contentEquals(
            getClass().getClassLoader().getResourceAsStream("willi.png"), 
            unit.read(identifier)
        ));
    }
    
    /**
     * Tests {@link Store#create(InputStream, String)} with a null stream.
     * 
     * @throws IOException should not happen
     */
    @Test(expected = NullPointerException.class)
    public void createStreamNull() throws IOException {
        unit().create(null, UUID.randomUUID().toString());
    }
    
    /**
     * Tests {@link Store#create(InputStream, String)} with a null identifier.
     * 
     * @throws IOException should not happen.
     */
    @Test(expected = NullPointerException.class)
    public void createIdentifierNull() throws IOException {
        final InputStream stream = getClass().getClassLoader().getResourceAsStream("willi.png");
        unit().create(stream, null);
    }
    
    /**
     * Tests {@link Store#create(InputStream, String)} with a null stream
     * and a null identifier.
     * 
     * @throws IOException should not happen
     */
    @Test(expected = NullPointerException.class)
    public void createStreamIdentifierNull() throws IOException {
        unit().create(null, null);
    }
    
    /**
     * Tests {@link Store#create(InputStream, String)} with a duplicate
     * identifer.
     * 
     * @throws IOException should not happen
     */
    @Test(expected = IllegalStateException.class)
    public void createStreamIdentifierDuplicate() throws IOException {
        final Store unit = unit();
        final String identifier = Long.toString(System.currentTimeMillis());
        
        InputStream stream;
        
        stream = getClass().getClassLoader().getResourceAsStream("willi.png");
        Assert.assertNotNull(stream);
        
        unit.create(stream, identifier);
        stream.close();
        
        stream = getClass().getClassLoader().getResourceAsStream("willi.png");
        Assert.assertNotNull(stream);
        
        try {
            unit.create(stream, identifier);
        } finally {
            stream.close();
        }
    }
    
    /**
     * Tests {@link Store#create(InputStream, String)}.
     * 
     * @throws IOException should not happen
     */
    @Test
    public void createStreamIdentifier() throws IOException {
        final Store unit = unit();
        final InputStream stream = getClass().getClassLoader().getResourceAsStream("willi.png");
        Assert.assertNotNull(stream);
        final String identifier = Long.toString(System.currentTimeMillis());
        unit.create(stream, identifier);
        stream.close();
        Assert.assertTrue(IOUtils.contentEquals(
            getClass().getClassLoader().getResourceAsStream("willi.png"), 
            unit.read(identifier)
        ));
    }

    /**
     * Tests {@link Store#read(String)} with a null identifier.
     * 
     * @throws IOException should not happen
     */
    @Test(expected = NullPointerException.class)
    public void readNull() throws IOException {
        unit().read(null);
    }
    
    /**
     * Tests {@link Store#read(String)} with a {@link ByteArrayInputStream}.
     * 
     * @throws IOException should not happen
     */
    @Test
    public void read() throws IOException {
        final Store unit = unit();
        final InputStream stream = new ByteArrayInputStream("data".getBytes(UTF_8));
        final String identifier = unit.create(stream);
        final InputStream read = unit.read(identifier);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(read, out);
        Assert.assertEquals("data", out.toString(UTF_8));
    }
    
    /**
     * Tests {@link Store#read(String)} using an identifier which does
     * not point to an existing entry.
     * 
     * @throws IOException expected
     */
    @Test(expected = IOException.class)
    public void readMissing() throws IOException {
        unit().read(UUID.randomUUID().toString());
    }
    
    /**
     * Tests {@link Store#list()} without adding anything.
     * 
     * @throws IOException should not happen 
     */
    @Test
    public void listEmpty() throws IOException {
        Assert.assertTrue(unit().list().isEmpty());
    }
    
    /**
     * Tests {@link Store#list()}.
     * 
     * @throws IOException should not happen
     */
    @Test
    public void list() throws IOException {
        final Store unit = unit();
        final Set<String> identifiers = Sets.newHashSet();
        final byte[] emptyByteArray = {};
        identifiers.add(unit.create(new ByteArrayInputStream(emptyByteArray)));
        identifiers.add(unit.create(new ByteArrayInputStream(emptyByteArray)));
        Assert.assertEquals(2, identifiers.size());
        Assert.assertEquals(identifiers, unit.list());
    }
    
    /**
     * Tests {@link Store#delete(String)} with a null identifier.
     * 
     * @throws IOException should not happen
     */
    @Test(expected = NullPointerException.class)
    public void deleteNull() throws IOException {
        unit().delete(null);
    }

    /**
     * Tests {@link Store#delete(String)} with a {@link ByteArrayInputStream}.
     * 
     * @throws IOException should not happen
     */
    @Test
    public void delete() throws IOException {
        final Store unit = unit();
        final InputStream stream = new ByteArrayInputStream("data".getBytes(UTF_8));
        final String identifier = unit.create(stream);
        unit.delete(identifier);
    }
    
    /**
     * Tests {@link Store#delete(String)} using an identifier which does
     * not point to an existing entry.
     * 
     * @throws IOException expected
     */
    @Test(expected = IOException.class)
    public void deleteMissing() throws IOException {
        unit().delete(UUID.randomUUID().toString());
    }
    
}
