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

package org.n52.sensorweb.sos.sir;

import java.net.URI;

import org.n52.sos.config.annotation.Configurable;
import org.n52.sos.config.annotation.Setting;
import org.n52.sos.request.DeleteSensorRequest;
import org.n52.sos.request.InsertSensorRequest;
import org.n52.sos.request.UpdateSensorRequest;
import org.n52.sos.response.DeleteSensorResponse;
import org.n52.sos.response.InsertSensorResponse;
import org.n52.sos.response.UpdateSensorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
@Configurable
public class SirConnector {

    private static final Logger log = LoggerFactory.getLogger(SirConnector.class);
    private URI serviceUri;


    void onModify(UpdateSensorRequest request, UpdateSensorResponse response) {
        /* TODO implement org.n52.sensorweb.sos.sir.SirConnector.onModify() */
        throw new UnsupportedOperationException("org.n52.sensorweb.sos.sir.SirConnector.onModify() not yet implemented");
    }

    void onDelete(DeleteSensorRequest request, DeleteSensorResponse response) {
        /* TODO implement org.n52.sensorweb.sos.sir.SirConnector.onDelete() */
        throw new UnsupportedOperationException("org.n52.sensorweb.sos.sir.SirConnector.onDelete() not yet implemented");
    }

    void onInsert(InsertSensorRequest request, InsertSensorResponse response) {
        /* TODO implement org.n52.sensorweb.sos.sir.SirConnector.onInsert() */
        throw new UnsupportedOperationException("org.n52.sensorweb.sos.sir.SirConnector.onInsert() not yet implemented");
    }

    @Setting(SirSettings.SERVICE_URL)
    public void setServiceUri(URI serviceUri) {
        this.serviceUri = serviceUri;
    }
}
