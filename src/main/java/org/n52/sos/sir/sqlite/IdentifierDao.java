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

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public class IdentifierDao {
    private SQLiteSessionFactory sessionFactory = new SQLiteSessionFactory();

    protected Session getSession() {
        return this.sessionFactory.getSession();
    }

    public String getIdentifierForSOS(String sirIdentifier) {
        Identifier id = (Identifier) getSession().createCriteria(Identifier.class)
                .add(Restrictions.eq(Identifier.SIR, sirIdentifier)).uniqueResult();
        return id != null ? id.getSos() : null;
    }

    public String getIdentifierForSIR(String sosIdentifier) {
        Identifier id = (Identifier) getSession().createCriteria(Identifier.class)
                .add(Restrictions.eq(Identifier.SOS, sosIdentifier)).uniqueResult();
        return id != null ? id.getSir() : null;
    }

    public void saveIdentifier(String sir, String sos) {
        Transaction t = null;
        try {
            Session s = getSession();
            t = s.beginTransaction();
            s.saveOrUpdate(new Identifier().setSir(sir).setSos(sos));
            t.commit();
            s.flush();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        }
    }

    public void deleteSirIdentifier(String sirIdentifier) {
        Transaction t = null;
        try {
            Session s = getSession();
            t = s.beginTransaction();
            Identifier id = (Identifier) s.createCriteria(Identifier.class)
                    .add(Restrictions.eq(Identifier.SIR, sirIdentifier)).uniqueResult();
            if (id != null) {
                s.delete(id);
            }
            t.commit();
            s.flush();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        }
    }

    public void deleteSosIdentifier(String sosIdentifier) {

        Transaction t = null;
        try {
            Session s = getSession();
            t = s.beginTransaction();
            Identifier id = (Identifier) s.createCriteria(Identifier.class)
                    .add(Restrictions.eq(Identifier.SOS, sosIdentifier)).uniqueResult();
            if (id != null) {
                s.delete(id);
            }
            t.commit();
            s.flush();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        }
    }
}
