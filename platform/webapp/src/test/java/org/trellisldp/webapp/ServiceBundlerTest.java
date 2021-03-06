/*
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
package org.trellisldp.webapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.trellisldp.api.ServiceBundler;

public class ServiceBundlerTest {

    private static final RandomStringGenerator generator = new RandomStringGenerator.Builder()
        .withinRange('a', 'z').build();

    @BeforeAll
    public static void setUp() {
        final String id = "-" + generator.generate(5);
        System.setProperty("trellis.rdf.location", System.getProperty("trellis.rdf.location") + id);
    }

    @AfterAll
    public static void cleanUp() {
        System.clearProperty("trellis.rdf.location");
    }

    @Test
    public void testServiceBundler() {
        final ServiceBundler bundler = new WebappServiceBundler();

        assertEquals(bundler.getResourceService(), bundler.getAuditService(), "Incorrect audit/resource services!");
        assertNotNull(bundler.getBinaryService(), "Missing binary service!");
        assertNotNull(bundler.getEventService(), "Missing event service!");
    }
}
