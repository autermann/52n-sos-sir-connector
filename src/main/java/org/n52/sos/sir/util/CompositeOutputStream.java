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

package org.n52.sos.sir.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public class CompositeOutputStream extends OutputStream {
    private OutputStream[] streams;

    public CompositeOutputStream(OutputStream... streams) {
        this.streams = streams;
    }

    public OutputStream[] getStreams() {
        return this.streams;
    }

    @Override
    public void write(int b) throws IOException {
        for (int i = 0; i < streams.length; ++i) {
            streams[i].write(b);
        }
    }

    @Override
    public void flush() throws IOException {
        for (int i = 0; i < streams.length; ++i) {
            streams[i].flush();
        }
    }

    @Override
    public void close() throws IOException {
        for (int i = 0; i < streams.length; ++i) {
            streams[i].close();
        }
    }
}
