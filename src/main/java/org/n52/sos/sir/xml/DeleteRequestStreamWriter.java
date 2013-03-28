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

import static org.n52.sos.sir.SirConstants.*;

import javax.xml.stream.XMLStreamException;

import org.n52.sos.ogc.sos.SosConstants;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public class DeleteRequestStreamWriter extends SirStreamWriter {
    private String sirProcedureIdentifier;
    private String sosProcedureIdentifier;
    private String sosServiceUrl;

    public DeleteRequestStreamWriter setSirProcedureIdentifier(String sirProcedureIdentifier) {
        this.sirProcedureIdentifier = sirProcedureIdentifier;
        return this;
    }

    public DeleteRequestStreamWriter setSosServiceUrl(String sosServiceUrl) {
        this.sosServiceUrl = sosServiceUrl;
        return this;
    }

    public DeleteRequestStreamWriter setSosProcedureIdentifier(String procedureIdentifier) {
        this.sosProcedureIdentifier = procedureIdentifier;
        return this;
    }

    @Override
    protected void write() throws XMLStreamException {
        writeDeleteSensorInfoRequest();
    }

    private void writeDeleteSensorInfoRequest() throws XMLStreamException {
        start(EN_DELETE_SENSOR_INFO_REQUEST);
        namespace(NS_SIR_PREFIX, NS_SIR);
        attr(AN_VERSION, SIR_SERVICE_VERSION);
        attr(AN_SERVICE, SIR_SERVICE_TYPE);
        writeInfoToBeDeleted();
        end(EN_DELETE_SENSOR_INFO_REQUEST);
    }

    private void writeInfoToBeDeleted() throws XMLStreamException {
        writeSirInfoToBeDeleted();
        writeSosInfoToBeDeleted();
    }

    private void writeSirSensorIdentification() throws XMLStreamException {
        start(EN_SENSOR_IDENTIFICATION);
        writeSensorIDInSIR();
        end(EN_SENSOR_IDENTIFICATION);
    }

    private void writeSensorIDInSIR() throws XMLStreamException {
        start(EN_SENSOR_ID_IN_SIR);
        chars(this.sirProcedureIdentifier);
        end(EN_SENSOR_ID_IN_SIR);
    }

    private void writeDeleteSensor() throws XMLStreamException {
        start(EN_DELETE_SENSOR);
        chars(String.valueOf(true));
        end(EN_DELETE_SENSOR);
    }

    private void writeSosSensorIdentification() throws XMLStreamException {
        start(EN_SENSOR_IDENTIFICATION);
        writeServiceReference();
        end(EN_SENSOR_IDENTIFICATION);
    }

    private void writeServiceReference() throws XMLStreamException {
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

    protected void writeSosInfoToBeDeleted() throws XMLStreamException {
        start(EN_INFO_TO_BE_DELETED);
        writeSosSensorIdentification();
        writeDeleteSensor();
        end(EN_INFO_TO_BE_DELETED);
    }

    protected void writeSirInfoToBeDeleted() throws XMLStreamException {
        start(EN_INFO_TO_BE_DELETED);
        writeSirSensorIdentification();
        writeDeleteSensor();
        end(EN_INFO_TO_BE_DELETED);
    }
}
