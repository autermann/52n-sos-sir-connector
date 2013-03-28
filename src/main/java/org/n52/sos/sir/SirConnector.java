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
package org.n52.sos.sir;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.n52.sos.config.annotation.Configurable;
import org.n52.sos.config.annotation.Setting;
import org.n52.sos.exception.ConfigurationException;
import org.n52.sos.request.DeleteSensorRequest;
import org.n52.sos.request.InsertSensorRequest;
import org.n52.sos.request.UpdateSensorRequest;
import org.n52.sos.response.DeleteSensorResponse;
import org.n52.sos.response.InsertSensorResponse;
import org.n52.sos.response.UpdateSensorResponse;
import org.n52.sos.service.ServiceSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
@Configurable
public class SirConnector {
    private static final Logger log = LoggerFactory.getLogger(SirConnector.class);
    private URL sirServiceUrl;
    private URL sosServiceUrl;

    @Setting(SirSettings.SERVICE_URL)
    public void setServiceUri(URI serviceUri) {
        if (serviceUri != null) {
            try {
                this.sirServiceUrl = serviceUri.toURL();
            } catch (MalformedURLException ex) {
                throw new ConfigurationException("Invalid SIR service URL", ex);
            }
        } else {
            this.sirServiceUrl = null;
        }
        
    }

    @Setting(ServiceSettings.SERVICE_URL)
    public void setSosServiceUri(URI serviceUri) {
        if (serviceUri != null) {
            try {
                this.sosServiceUrl = serviceUri.toURL();
            } catch (MalformedURLException ex) {
                throw new ConfigurationException("Invalid SOS service URL", ex);
            }
        } else {
            this.sosServiceUrl = null;
        }
    }

    void onModify(UpdateSensorRequest request, UpdateSensorResponse response) {
    }

    void onDelete(DeleteSensorRequest request, DeleteSensorResponse response) {
    }

    void onInsert(InsertSensorRequest request, InsertSensorResponse response) {
    }
}
