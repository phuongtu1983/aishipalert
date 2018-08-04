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
package dk.dma.ais.message;

import dk.dma.ais.binary.SixbitException;
import dk.dma.ais.sentence.Vdm;

/**
 * AIS message 4
 *
 * Base station report as defined by ITU-R M.1371-4
 *
 */
public class AisMessage11 extends UTCDateResponseMessage {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    public AisMessage11() {
        super(11);
    }

    public AisMessage11(Vdm vdm) throws AisMessageException, SixbitException {
        super(vdm);
    }

}
