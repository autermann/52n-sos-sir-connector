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
package org.n52.sos.sir;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.xml.stream.XMLStreamException;

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
import org.n52.sos.sir.sqlite.IdentifierDao;
import org.n52.sos.sir.util.CloningOutputStream;
import org.n52.sos.sir.xml.DeleteRequestStreamWriter;
import org.n52.sos.sir.xml.InsertRequestStreamWriter;
import org.n52.sos.sir.xml.StreamWriter;
import org.n52.sos.sir.xml.UpdateRequestStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
@Configurable
public class SirConnector {
    private static final Logger log = LoggerFactory.getLogger(SirConnector.class);
    private final IdentifierDao identifiers = new IdentifierDao();
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

    public void onModify(UpdateSensorRequest request, UpdateSensorResponse response) {
        StreamWriter writer = new UpdateRequestStreamWriter()
                .setSirProcedureIdentifier(identifiers.getIdentifierForSIR(response.getUpdatedProcedure()))
                .setSensorDescription(request.getProcedureDescriptions().iterator().next().getSensorDescriptionXmlString());
        try {
            InputStream in = post(writer);
        } catch (IOException ex) {
            log.error("Error modifing sensor in SIR", ex);
        } catch (XMLStreamException ex) {
            log.error("Error modifing sensor in SIR", ex);
        }
    }

    public void onDelete(DeleteSensorRequest request, DeleteSensorResponse response) {
        StreamWriter writer = new DeleteRequestStreamWriter()
                .setSirProcedureIdentifier(identifiers.getIdentifierForSIR(response.getDeletedProcedure()))
                .setSosProcedureIdentifier(response.getDeletedProcedure())
                .setSosServiceUrl(this.sosServiceUrl.toString());
        try {
            InputStream in = post(writer);
        } catch (IOException ex) {
            log.error("Error deleting sensor in SIR", ex);
        } catch (XMLStreamException ex) {
            log.error("Error deleting sensor in SIR", ex);
        }
    }

    public void onInsert(InsertSensorRequest request, InsertSensorResponse response) {
        StreamWriter writer = new InsertRequestStreamWriter()
                .setSosServiceUrl(this.sosServiceUrl.toString())
                .setSosProcedureIdentifier(response.getAssignedProcedure())
                .setSensorDescription(request.getProcedureDescription().getSensorDescriptionXmlString());
        try {
            InputStream in = post(writer);
        } catch (IOException ex) {
            log.error("Error inserting sensor in SIR", ex);
        } catch (XMLStreamException ex) {
            log.error("Error inserting sensor in SIR", ex);
        }
    }

    protected InputStream post(StreamWriter w) throws IOException, XMLStreamException {
        HttpURLConnection conn = (HttpURLConnection) this.sirServiceUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        CloningOutputStream out = new CloningOutputStream(conn.getOutputStream());
        w.write(out);
        return conn.getInputStream();
    }
}
