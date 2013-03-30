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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.n52.sos.sir.SirConstants;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public class StreamWriterTest {
    private static final int BUFFER_SIZE = 4096;
    private static final String SOS_SENSOR_ID = "urn:ogc:object:feature:Sensor:EEA:airbase:7.0:FR17016";
    private static final String SIR_SENSOR_ID = "urn:sir:FR17016";
    private static final String SOS_SERVICE_URL = "http://localhost:8080/52n-sos-webapp/sos/kvp";
    private String sensorDescription;

    @Test
    public void testDeleteRequest() throws FileNotFoundException, XMLStreamException {
        new DeleteRequestStreamWriter()
                .setSosProcedureIdentifier(SOS_SENSOR_ID)
                .setSirProcedureIdentifier(SIR_SENSOR_ID)
                .setSosServiceUrl(SOS_SERVICE_URL)
                .write(System.out);
    }

    @Test
    public void testUpdateRequest() throws XMLStreamException, FileNotFoundException {
        new UpdateRequestStreamWriter()
                .setSensorDescription(sensorDescription)
                .setSirProcedureIdentifier(SIR_SENSOR_ID)
                .write(System.out);
    }

    @Test
    public void testInsertRequest() throws FileNotFoundException, XMLStreamException {
        new InsertRequestStreamWriter()
                .setSosProcedureIdentifier(SOS_SENSOR_ID)
                .setSensorDescription(sensorDescription)
                .setSosServiceUrl(SOS_SERVICE_URL)
                .write(System.out);
    }

    @Test
    public void testDeleteRequestOutput() throws FileNotFoundException, XMLStreamException, UnsupportedEncodingException,
                                                 IOException, TransformerConfigurationException, TransformerException {
        String r = toString(new DeleteRequestStreamWriter()
                .setSosProcedureIdentifier(SOS_SENSOR_ID)
                .setSirProcedureIdentifier(SIR_SENSOR_ID)
                .setSosServiceUrl(SOS_SERVICE_URL));
//        IOUtils.write(r, new FileOutputStream("/home/auti/generated-d.xml"));
        String expected = readFile("/delete.xml");
        assertThat(r, is(equalTo(expected)));
    }

    @Test
    public void testUpdateRequestOutput() throws XMLStreamException, FileNotFoundException, UnsupportedEncodingException,
                                                 IOException, TransformerConfigurationException, TransformerException {
        String r = toString(new UpdateRequestStreamWriter()
                .setSensorDescription(sensorDescription)
                .setSirProcedureIdentifier(SIR_SENSOR_ID));
//        IOUtils.write(r, new FileOutputStream("/home/auti/generated-u.xml"));
        String expected = readFile("/update.xml");
        assertThat(r, is(equalTo(expected)));
    }

    @Test
    public void testInsertRequestOutput() throws FileNotFoundException, XMLStreamException, UnsupportedEncodingException,
                                                 IOException, TransformerConfigurationException, TransformerException {
        String r = toString(new InsertRequestStreamWriter()
                .setSosProcedureIdentifier(SOS_SENSOR_ID)
                .setSensorDescription(sensorDescription)
                .setSosServiceUrl(SOS_SERVICE_URL));
//        IOUtils.write(r, new FileOutputStream("/home/auti/generated-i.xml"));
        String expected = readFile("/insert.xml");
        assertThat(r, is(equalTo(expected)));
    }

    private String toString(final StreamWriter w) throws XMLStreamException, UnsupportedEncodingException,
                                                         TransformerConfigurationException, IOException, TransformerException {
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        w.writePretty(s);
        return s.toString(SirConstants.UTF8);
    }

    @Before
    public void setUp() throws IOException {
        this.sensorDescription = readFile("/SensorDescription.xml");
    }

    protected String readFile(String file) throws IOException {
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new BufferedInputStream(StreamWriterTest.class.getResourceAsStream(file));
            out = new ByteArrayOutputStream();
            byte[] buf = new byte[BUFFER_SIZE];
            int l;
            while ((l = in.read(buf)) > 0) {
                out.write(buf, 0, l);
            }
            return out.toString(SirConstants.UTF8);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    
}
