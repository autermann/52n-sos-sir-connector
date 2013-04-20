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

import static org.n52.sos.ogc.gml.GMLConstants.NS_GML;
import static org.n52.sos.ogc.gml.GMLConstants.NS_GML_PREFIX;
import static org.n52.sos.ogc.sensorML.SensorMLConstants.NS_SML;
import static org.n52.sos.ogc.sensorML.SensorMLConstants.NS_SML_PREFIX;
import static org.n52.sos.ogc.swe.SWEConstants.NS_SWE;
import static org.n52.sos.ogc.swe.SWEConstants.NS_SWE_PREFIX;
import static org.n52.sos.sir.SirConstants.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import net.opengis.sensorML.x101.SensorMLDocument;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.n52.sos.sir.SirConstants;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public abstract class SirStreamWriter extends StreamWriter implements SirConstants {
    private static final String EN_XML_FRAGMENT = "xml-fragment";
    protected void namespaces() throws XMLStreamException {
        namespace(NS_SML_PREFIX, NS_SML);
        namespace(NS_GML_PREFIX, NS_GML);
        namespace(NS_SIR_PREFIX, NS_SIR);
        namespace(NS_SWE_PREFIX, NS_SWE);
    }

    protected void writeSensorDescription(String sml) throws XMLStreamException {
        try {
            start(QN_SENSOR_DESCRIPTION);
            XmlObject x = XmlObject.Factory.parse(sml, new XmlOptions().setLoadStripWhitespace()
                    .setLoadStripComments().setLoadStripProcinsts());
            XMLEventReader r = getInputFactory().createXMLEventReader(((SensorMLDocument) x).getSensorML()
                    .getMemberArray(0).getProcess().newInputStream());
            XMLEvent e = null;
            while (r.hasNext() && (e = r.nextEvent()) != null) {
                if (!e.isStartDocument() && !e.isEndDocument()
                    && !(e.isStartElement() && e.asStartElement().getName().getLocalPart().equals(EN_XML_FRAGMENT))
                    && !(e.isEndElement() && e.asEndElement().getName().getLocalPart().equals(EN_XML_FRAGMENT))) {
                    getWriter().add(e);
                }
            }
            end(QN_SENSOR_DESCRIPTION);
        } catch (XmlException ex) {
            throw new XMLStreamException(ex);
        }
    }

}
