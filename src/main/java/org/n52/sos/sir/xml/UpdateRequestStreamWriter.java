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

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public class UpdateRequestStreamWriter extends SirStreamWriter {
    private String sirProcedureIdentifier;
    private String sensorDescription;

    public UpdateRequestStreamWriter setSirProcedureIdentifier(String sirProcedureIdentifier) {
        this.sirProcedureIdentifier = sirProcedureIdentifier;
        return this;
    }

    public UpdateRequestStreamWriter setSensorDescription(String sensorDescription) {
        this.sensorDescription = sensorDescription;
        return this;
    }

    @Override
    protected void write() throws XMLStreamException {
        writeUpdateSensorDescriptionRequest();
    }

    private void writeSensorDescriptionToBeUpdated() throws XMLStreamException {
        start(QN_SENSOR_DESCRIPTION_TO_BE_UPDATED);
        writeSensorIdentification();
        writeSensorDescription(this.sensorDescription);
        end(QN_SENSOR_DESCRIPTION_TO_BE_UPDATED);
    }

    private void writeSensorIdentification() throws XMLStreamException {
        start(QN_SENSOR_IDENTIFICATION);
        writeSensorIDInSIR();
        end(QN_SENSOR_IDENTIFICATION);
    }

    private void writeSensorIDInSIR() throws XMLStreamException {
        start(QN_SENSOR_ID_IN_SIR);
        chars(this.sirProcedureIdentifier);
        end(QN_SENSOR_ID_IN_SIR);
    }

    protected void writeUpdateSensorDescriptionRequest() throws XMLStreamException {
        start(QN_UPDATE_SENSOR_DESCRIPTION_REQUEST);
        namespaces();
        attr(AN_VERSION, SIR_SERVICE_VERSION);
        attr(AN_SERVICE, SIR_SERVICE_TYPE);
        writeSensorDescriptionToBeUpdated();
        end(QN_UPDATE_SENSOR_DESCRIPTION_REQUEST);
    }
}
