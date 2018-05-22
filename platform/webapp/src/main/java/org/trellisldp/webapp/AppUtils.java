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

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Optional.of;
import static java.util.ServiceLoader.load;
import static org.apache.jena.query.DatasetFactory.createTxnMem;
import static org.apache.jena.query.DatasetFactory.wrap;
import static org.apache.jena.rdfconnection.RDFConnectionFactory.connect;
import static org.apache.jena.tdb2.DatabaseMgr.connectDatasetGraph;


import java.util.Collection;
import java.util.Iterator;
import java.util.function.Supplier;

import org.apache.jena.rdfconnection.RDFConnection;
import org.trellisldp.api.RuntimeTrellisException;

final class AppUtils {

    public static <T> T loadFirst(final Class<T> service) {
        return of(load(service).iterator()).filter(Iterator::hasNext).map(Iterator::next)
            .orElseThrow(() -> new RuntimeTrellisException("No loadable " + service.getName() + " on the classpath"));
    }

    public static <T> T loadWithDefault(final Class<T> service, final Supplier<? extends T> other) {
        load(service).forEach(s -> System.out.println("Services: " + s.getClass().getName()));
        return of(load(service).iterator()).filter(Iterator::hasNext).map(Iterator::next).orElseGet(other);
    }

    public static Collection<String> asCollection(final String value) {
        return isNull(value) ? emptyList() :  asList(value.trim().split("\\s*,\\s*"));
    }

    public static RDFConnection getRDFConnection(final String location) {
        if (isNull(location)) {
            return connect(createTxnMem());
        } else if (location.startsWith("http://") || location.startsWith("https://")) {
            return connect(location);
        }
        return connect(wrap(connectDatasetGraph(location)));
    }

    private AppUtils() {
        // prevent instantiation
    }
}
