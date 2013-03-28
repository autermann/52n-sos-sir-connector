/**
 * Copyright (C) 2013
 * by 52 North Initiative for Geospatial Open Source Software GmbH
 *
 * Contact: Andreas Wytzisk
 * 52 North Initiative for Geospatial Open Source Software GmbH
 * Martin-Luther-King-Weg 24
 * 48155 Muenster, Germany
 * info@52north.org
 *
 * This program is free software; you can redistribute and/or modify it under
 * the terms of the GNU General Public License version 2 as published by the
 * Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; even without the implied
 * WARRANTY OF MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program (see gnu-gpl v2.txt). If not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA or
 * visit the Free Software Foundation web page, http://www.fsf.org.
 */
package org.n52.sos.sir.xml;

import javax.xml.stream.XMLStreamException;

import org.n52.sos.ogc.sos.SosConstants;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public class InsertRequestStreamWriter extends SirStreamWriter {
    private String sosServiceUrl;
    private String sosProcedureIdentifier;
    private String sensorDescription;

    public InsertRequestStreamWriter setSosServiceUrl(String sosServiceUrl) {
        this.sosServiceUrl = sosServiceUrl;
        return this;
    }

    public InsertRequestStreamWriter setSosProcedureIdentifier(String sosProcedureIdentifier) {
        this.sosProcedureIdentifier = sosProcedureIdentifier;
        return this;
    }

    public InsertRequestStreamWriter setSensorDescription(String sensorDescription) {
        this.sensorDescription = sensorDescription;
        return this;
    }

    @Override
    protected void write() throws XMLStreamException {
        writeInsertSensorInfoRequest();
    }

    protected void writeInsertSensorInfoRequest() throws XMLStreamException {
        start(EN_INSERT_SENSOR_INFO_REQUEST);
        namespace(NS_SIR_PREFIX, NS_SIR);
        attr(AN_VERSION, SIR_SERVICE_VERSION);
        attr(AN_SERVICE, SIR_SERVICE_TYPE);
        writeInfoToBeInserted();
        end(EN_INSERT_SENSOR_INFO_REQUEST);
    }

    protected void writeInfoToBeInserted() throws XMLStreamException {
        start(EN_INFO_TO_BE_INSERTED);
        writeServiceReference();
        include(new SensorDescriptionStreamWriter().setSensorDescription(this.sensorDescription));
        end(EN_INFO_TO_BE_INSERTED);
    }

    protected void writeServiceReference() throws XMLStreamException {
        start(EN_SERVICE_REFERENCE);
        writeServiceURL();
        writeServiceType();
        writeServiceSpecificSensorID();
        end(EN_SERVICE_REFERENCE);
    }

    protected void writeServiceURL() throws XMLStreamException {
        start(EN_SERVICE_URL);
        chars(sosServiceUrl);
        end(EN_SERVICE_URL);
    }

    protected void writeServiceType() throws XMLStreamException {
        start(EN_SERVICE_TYPE);
        chars(SosConstants.SOS);
        end(EN_SERVICE_TYPE);
    }

    protected void writeServiceSpecificSensorID() throws XMLStreamException {
        start(EN_SERVICE_SPECIFIC_SENSOR_ID);
        chars(this.sosProcedureIdentifier);
        end(EN_SERVICE_SPECIFIC_SENSOR_ID);
    }
}
