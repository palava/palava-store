/**
 * palava - a java-php-bridge
 * Copyright (C) 2007  CosmoCode GmbH
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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.cosmocode.palava.services.store;

import de.cosmocode.palava.core.bridge.simple.content.Content;
import de.cosmocode.palava.core.bridge.simple.content.StreamContent;
import de.cosmocode.palava.core.framework.Service;

public interface ContentStore extends Service {

    String store(Content content) throws Exception;
    
    StreamContent load(String key) throws Exception;

    void remove(String key);
    
}
