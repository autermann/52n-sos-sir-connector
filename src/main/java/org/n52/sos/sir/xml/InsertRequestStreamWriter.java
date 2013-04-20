/**
 * Copyright (C) 2013 Christian Autermann
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
        start(QN_INSERT_SENSOR_INFO_REQUEST);
        namespaces();
        attr(AN_VERSION, SIR_SERVICE_VERSION);
        attr(AN_SERVICE, SIR_SERVICE_TYPE);
        writeInfoToBeInserted();
        end(QN_INSERT_SENSOR_INFO_REQUEST);
    }

    protected void writeInfoToBeInserted() throws XMLStreamException {
        start(QN_INFO_TO_BE_INSERTED);
        writeSensorDescription(this.sensorDescription);
        writeServiceReference();
        end(QN_INFO_TO_BE_INSERTED);
    }

    protected void writeServiceReference() throws XMLStreamException {
        start(QN_SERVICE_REFERENCE);
        writeServiceURL();
        writeServiceType();
        writeServiceSpecificSensorID();
        end(QN_SERVICE_REFERENCE);
    }

    protected void writeServiceURL() throws XMLStreamException {
        start(QN_SERVICE_URL);
        chars(sosServiceUrl);
        end(QN_SERVICE_URL);
    }

    protected void writeServiceType() throws XMLStreamException {
        start(QN_SERVICE_TYPE);
        chars(SosConstants.SOS);
        end(QN_SERVICE_TYPE);
    }

    protected void writeServiceSpecificSensorID() throws XMLStreamException {
        start(QN_SERVICE_SPECIFIC_SENSOR_ID);
        chars(this.sosProcedureIdentifier);
        end(QN_SERVICE_SPECIFIC_SENSOR_ID);
    }

}
