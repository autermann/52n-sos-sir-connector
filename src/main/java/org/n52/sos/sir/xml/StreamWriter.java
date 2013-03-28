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

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.io.IOUtils;

import javanet.staxutils.IndentingXMLEventWriter;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public abstract class StreamWriter {

    private final XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    private final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
    private final XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
    private final String namespace;
    private final String prefix;
    private XMLEventWriter w;

    protected StreamWriter(String defaultNamespace, String defaultNamespacePrefix) {
        this.namespace = defaultNamespace;
        this.prefix = defaultNamespacePrefix;
    }

    protected void attr(String name, String value) throws XMLStreamException {
        w.add(eventFactory.createAttribute(name, value));
    }

    protected void chars(String chars) throws XMLStreamException {
        w.add(eventFactory.createCharacters(chars));
    }

    protected void copy(String xml) throws XMLStreamException {
        ByteArrayInputStream in = null;
        try {
            in = new ByteArrayInputStream(xml.getBytes("utf-8"));
            XMLEventReader r = inputFactory.createXMLEventReader(in);
            while (r.hasNext()) {
                XMLEvent e = r.nextEvent();
                if (!e.isEndDocument() && !e.isStartDocument()) {
                    w.add(e);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    protected void end(String name) throws XMLStreamException {
        w.add(eventFactory.createEndElement(this.prefix, this.namespace, name));
    }

    protected void end() throws XMLStreamException {
        w.add(eventFactory.createEndDocument());
    }

    protected void namespace(String prefix, String namespace) throws XMLStreamException {
        w.add(eventFactory.createNamespace(prefix, namespace));
    }

    protected void start(String name) throws XMLStreamException {
        w.add(eventFactory.createStartElement(this.prefix, this.namespace, name));
    }

    protected void start() throws XMLStreamException {
        w.add(eventFactory.createStartDocument());
    }

    public void write(OutputStream out) throws XMLStreamException {
        this.w = new IndentingXMLEventWriter(outputFactory.createXMLEventWriter(out));
        _write();
    }

    public void write(Writer out) throws XMLStreamException {
        this.w = new IndentingXMLEventWriter(outputFactory.createXMLEventWriter(out));
        _write();
    }

    public void write(XMLEventWriter out) throws XMLStreamException {
        this.w = new IndentingXMLEventWriter(out);
        _write();
    }

    public void writeDocument(OutputStream out) throws XMLStreamException {
        this.w = new IndentingXMLEventWriter(outputFactory.createXMLEventWriter(out));
        _writeDocument();
    }

    public void writeDocument(Writer out) throws XMLStreamException {
        this.w = new IndentingXMLEventWriter(outputFactory.createXMLEventWriter(out));
        _writeDocument();
    }

    public void writeDocument(XMLEventWriter out) throws XMLStreamException {
        this.w = new IndentingXMLEventWriter(out);
        _writeDocument();
    }

    protected void include(StreamWriter sw) throws XMLStreamException {
        sw.write(this.w);
    }

    private void _writeDocument() throws XMLStreamException {
        start();
        write();
        end();
        w.flush();
        w.close();
    }

    private void _write() throws XMLStreamException {
        write();
        w.flush();
        w.close();
    }

    protected abstract void write() throws XMLStreamException;
}
