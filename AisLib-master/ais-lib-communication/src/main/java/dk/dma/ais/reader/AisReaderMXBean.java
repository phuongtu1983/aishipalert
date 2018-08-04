/* Copyright (c) 2011 Danish Maritime Authority.
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
package dk.dma.ais.reader;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.net.HostAndPort;

/**
 * 
 * @author Kasper Nielsen
 */
public interface AisReaderMXBean {

    String getHosts();

    void addHost(String hostname, int port);

    long getBytesRead();

    long getPacketsRead();

    String getSource();
}

class AisReaderMXBeanImpl implements AisReaderMXBean {
    final AisTcpReader reader;

    AisReaderMXBeanImpl(AisTcpReader reader) {
        this.reader = requireNonNull(reader);
    }

    /**
     * @return the bytesRead
     */
    public long getBytesRead() {
        return reader.getNumberOfBytesRead();
    }

    /**
     * @return the packetsRead
     */
    public long getPacketsRead() {
        return 0;// reader.g;
    }

    public String getHosts() {
        List<String> l = new ArrayList<>();
        for (HostAndPort hap : reader.hosts) {
            l.add(hap.getHostText());
        }
        return Joiner.on(',').join(l);
    }

    /**
     * @return the source
     */
    public String getSource() {
        return reader.getSourceId();
    }

    /** {@inheritDoc} */
    @Override
    public void addHost(String hostName, int port) {
        HostAndPort hap = HostAndPort.fromParts(hostName, port);
        reader.addHostPort(hap);
    }
}
