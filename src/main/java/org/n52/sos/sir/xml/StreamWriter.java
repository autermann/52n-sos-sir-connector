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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public abstract class StreamWriter {
    private final XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    private final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
    private final XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
    private XMLEventWriter writer;

    protected void attr(String name, String value) throws XMLStreamException {
        writer.add(eventFactory.createAttribute(name, value));
    }

    protected void chars(String chars) throws XMLStreamException {
        writer.add(eventFactory.createCharacters(chars));
    }

    protected void copy(String xml) throws XMLStreamException {
        ByteArrayInputStream in = null;
        try {
            in = new ByteArrayInputStream(xml.trim().getBytes("utf-8"));
            XMLEventReader r = inputFactory.createXMLEventReader(in);
            XMLEvent c = null, p = null;
            while (r.hasNext() && (c = r.nextEvent()) != null) {
                // ignore end -> space and start -> space -> start & <?xml?> -> space
                if (!(p != null && c.isCharacters() && (p.isStartDocument() || p.isEndElement()
                                                        || (p.isStartElement() && r.peek() != null && r.peek()
                        .isStartElement())))) {
                    switch (c.getEventType()) {
                        case XMLEvent.DTD:
                        case XMLEvent.PROCESSING_INSTRUCTION:
                        case XMLEvent.COMMENT:
                        case XMLEvent.SPACE:
                        case XMLEvent.START_DOCUMENT:
                        case XMLEvent.END_DOCUMENT:
                            break;
                        default:
                            writer.add(c);
                    }
                }
                p = c;
            }
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    protected void end(QName name) throws XMLStreamException {
        writer.add(eventFactory.createEndElement(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart()));

    }

    protected void end() throws XMLStreamException {
        writer.add(eventFactory.createEndDocument());
    }

    protected void namespace(String prefix, String namespace) throws XMLStreamException {
        writer.add(eventFactory.createNamespace(prefix, namespace));
    }

    protected void start(QName name) throws XMLStreamException {
        writer.add(eventFactory.createStartElement(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart()));

    }

    protected void start() throws XMLStreamException {
        writer.add(eventFactory.createStartDocument());
    }

    protected void addTo(XMLEventWriter out) throws XMLStreamException {
        this.writer = out;
        write();
    }

    protected void include(StreamWriter w) throws XMLStreamException {
        w.addTo(this.writer);
    }

    public void write(OutputStream out) throws XMLStreamException {
        this.writer = outputFactory.createXMLEventWriter(out);
        _write();
    }

    public void write(Writer out) throws XMLStreamException {
        this.writer = outputFactory.createXMLEventWriter(out);
        _write();
    }

    public void write(XMLEventWriter out) throws XMLStreamException {
        this.writer = out;
        _write();
    }

    private void _write() throws XMLStreamException {
        start();
        write();
        end();
        writer.flush();
        writer.close();
    }

    public void writePretty(OutputStream out) throws TransformerConfigurationException, TransformerException,
                                                     IOException, XMLStreamException {
        PipedInputStream in = new PipedInputStream();
        final PipedOutputStream tout = new PipedOutputStream(in);
        final List<Throwable> exceptions = Collections.synchronizedList(new LinkedList<Throwable>());
        Thread writerThread = new Thread(new WriterRunnable(tout));
        writerThread.setUncaughtExceptionHandler(new CollectionExceptionHandler(exceptions));
        writerThread.start();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new StreamSource(in), new StreamResult(out));
        for (Throwable t : exceptions) {
            if (t instanceof XMLStreamException) {
                throw (XMLStreamException) t;
            } else if (t instanceof IOException) {
                throw (IOException) t;
            } else {
                throw new RuntimeException(t);
            }
        }
    }

    protected abstract void write() throws XMLStreamException;

    public XMLEventFactory getEventFactory() {
        return eventFactory;
    }

    public XMLInputFactory getInputFactory() {
        return inputFactory;
    }

    public XMLOutputFactory getOutputFactory() {
        return outputFactory;
    }

    public XMLEventWriter getWriter() {
        return writer;
    }

    private static class CollectionExceptionHandler implements UncaughtExceptionHandler {
        private final List<Throwable> exceptions;

        CollectionExceptionHandler(
                List<Throwable> exceptions) {
            this.exceptions = exceptions;
        }

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            exceptions.add(e);
        }
    }

    private class WriterRunnable implements Runnable {
        private final PipedOutputStream tout;

        WriterRunnable(PipedOutputStream tout) {
            this.tout = tout;
        }

        @Override
        public void run() {
            try {
                write(tout);
                tout.flush();
                tout.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (XMLStreamException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
