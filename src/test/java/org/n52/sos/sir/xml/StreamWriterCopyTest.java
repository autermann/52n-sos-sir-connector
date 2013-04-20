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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.n52.sos.sir.SirConstants;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public class StreamWriterCopyTest {

    @Test
    public void test1() throws XMLStreamException, UnsupportedEncodingException {
        assertThat(toString(new CopyStreamWriter("\n  <r>\n    <a> \n </a>\n    <b> \n </b>\n  </r>")),
                   is(equalTo("<?xml version='1.0' encoding='UTF-8'?><r><a> \n </a><b> \n </b></r>")));
    }

    @Test
    public void test2() throws XMLStreamException, UnsupportedEncodingException {
        assertThat(toString(new CopyStreamWriter("<a/>")),
                   is(equalTo("<?xml version='1.0' encoding='UTF-8'?><a />")));
    }

    @Test
    public void test3() throws XMLStreamException, UnsupportedEncodingException {
        assertThat(toString(new CopyStreamWriter("<a>b</a>")),
                   is(equalTo("<?xml version='1.0' encoding='UTF-8'?><a>b</a>")));
    }

    @Test
    public void test4() throws XMLStreamException, UnsupportedEncodingException {
        assertThat(toString(new CopyStreamWriter("<a><b/></a>")),
                   is(equalTo("<?xml version='1.0' encoding='UTF-8'?><a><b /></a>")));
    }

    @Test
    public void test5() throws XMLStreamException, UnsupportedEncodingException {
        assertThat(toString(new CopyStreamWriter("<?xml version='1.0' encoding='UTF-8'?><a />")),
                   is(equalTo("<?xml version='1.0' encoding='UTF-8'?><a />")));
    }

    @Test
    public void test6() throws XMLStreamException, UnsupportedEncodingException {
        assertThat(toString(new CopyStreamWriter("<?xml version='1.0' encoding='UTF-8'?>  <a />")),
                   is(equalTo("<?xml version='1.0' encoding='UTF-8'?><a />")));
    }

    private String toString(StreamWriter w) throws XMLStreamException, UnsupportedEncodingException {
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        w.write(s);
        return s.toString(SirConstants.UTF8);
    }

    private class CopyStreamWriter extends StreamWriter {
        private String in;

        CopyStreamWriter(String in) {
            this.in = in;
        }

        @Override
        protected void write() throws XMLStreamException {
            copy(in);
        }
    }

}
