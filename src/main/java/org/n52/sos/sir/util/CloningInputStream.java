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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public class CloningInputStream extends InputStream {
    private InputStream in;
    private ByteArrayOutputStream out;

    public CloningInputStream(InputStream stream) {
        this.in = stream;
        this.out = new ByteArrayOutputStream();
    }

    @Override
    public String toString() {
        return out.toString();
    }

    public String toString(String charsetName) throws UnsupportedEncodingException {
        return out.toString(charsetName);
    }

    @Override
    public int read() throws IOException {
        int i = in.read();
        out.write(i);
        return i;
    }
}
