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
package org.n52.sos.sir.sqlite;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
@Entity
public class Identifier implements Serializable {
    private static final long serialVersionUID = 5866258771706415967L;
    public static final String SOS = "sos";
    public static final String SIR = "sir";
    @Id
    @Column(name = SOS, unique = true)
    private String sos;
    @Column(name = SIR, unique = true)
    private String sir;

    public Identifier(String sos, String sir) {
        this.sos = sos;
        this.sir = sir;
    }

    public Identifier() {
        this(null, null);
    }

    public String getSos() {
        return sos;
    }

    public Identifier setSos(String sos) {
        this.sos = sos;
        return this;
    }

    public String getSir() {
        return sir;
    }

    public Identifier setSir(String sir) {
        this.sir = sir;
        return this;
    }
}
