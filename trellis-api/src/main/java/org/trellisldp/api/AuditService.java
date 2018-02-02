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
package org.trellisldp.api;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.apache.commons.rdf.api.Dataset;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Quad;

/**
 * A service for producing audit-related {@link Quad} values for creation,
 * deletion and modification operation.
 *
 * @author acoburn
 */
public interface AuditService extends AppendService<IRI, Dataset> {

    /**
     * Generate the audit quads for a Create event.
     *
     * @param identifier the resource identifier
     * @param session the session data
     * @return the list of quads
     */
    default List<Quad> creation(IRI identifier, Session session) {
        return emptyList();
    }

    /**
     * Generate the audit quads for a Delete event.
     *
     * @param identifier the resource identifier
     * @param session the session data
     * @return the list of quads
     */
    default List<Quad> deletion(IRI identifier, Session session) {
        return emptyList();
    }

    /**
     * Generate the audit quads for an Update event.
     *
     * @param identifier the resource identifier
     * @param session the session data
     * @return the list of quads
     */
    default List<Quad> update(IRI identifier, Session session) {
        return emptyList();
    }

    @Override
    default Future<Boolean> add(IRI identifier, Dataset dataset) {
        return CompletableFuture.completedFuture(false);
    }

    /**
     * Singleton.
     * TODO make private in Java 9
     */
    AuditService nullInstance = new AuditService() {};

    /**
     * @return an {@code AuditService} that does nothing and throws away all inputs
     */
    static AuditService none() {
        return nullInstance;
    }
}
