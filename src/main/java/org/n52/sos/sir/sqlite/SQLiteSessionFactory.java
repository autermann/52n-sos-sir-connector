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

import java.io.File;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.n52.sos.exception.ConfigurationException;
import org.n52.sos.service.SosContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public class SQLiteSessionFactory {
    private static final Logger log = LoggerFactory.getLogger(SQLiteSessionFactory.class);
    public static final String HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";
    public static final String HIBERNATE_CONNECTION_DRIVER_CLASS = "hibernate.connection.driver_class";
    public static final String HIBERNATE_UPDATE_SCHEMA = "hibernate.hbm2ddl.auto";
    public static final String HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";
    public static final String HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";
    public static final String HIBERNATE_CONNECTION_POOL_SIZE = "hibernate.connection.pool_size";
    public static final String HIBERNATE_CONNECTION_RELEASE_MODE = "hibernate.connection.release_mode";
    public static final String HIBERNATE_CURRENT_SESSION_CONTEXT = "hibernate.current_session_context_class";
    public static final String RELEASE_MODE_AFTER_TRANSACTION = "after_transaction";
    public static final String RELEASE_MODE_AFTER_STATEMENT = "after_statement";
    public static final String RELEASE_MODE_ON_CLOSE = "on_close";
    public static final String RELEASE_MODE_AUTO = "auto";
    public static final String THREAD_LOCAL_SESSION_CONTEXT = "thread";
    private static final int SQLITE_CONNECTION_POOL_SIZE = 1;
    private static final String CONNECTION_URL_TEMPLATE = "jdbc:sqlite:%s.db";
    private static final String DEFAULT_DATABASE_NAME = "sir";
    private static final String SQLITE_HIBERNATE_DIALECT = HibernateSQLiteDialect.class.getName();
    private static final String UPDATE_SCHEMA_VALUE = "update";
    private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";
    public static final String EMPTY = "";
    private static final Properties DEFAULT_PROPERTIES = new Properties() {
        private static final long serialVersionUID = 3109256773218160485L;

        {
            String path = null;
            try {
                path = SosContextListener.getPath();
            } catch (Throwable t) {
            }
            if (path == null) {
                path = System.getProperty("user.dir");
                log.warn("Context path is not set; using {} instead", path);
            } else {
                path = new File(path).getAbsolutePath();
            }
            path = path + File.separator + DEFAULT_DATABASE_NAME;

            put(HIBERNATE_CONNECTION_URL, String.format(CONNECTION_URL_TEMPLATE, path));
            put(HIBERNATE_UPDATE_SCHEMA, UPDATE_SCHEMA_VALUE);
            put(HIBERNATE_DIALECT, SQLITE_HIBERNATE_DIALECT);
            put(HIBERNATE_CONNECTION_DRIVER_CLASS, SQLITE_JDBC_DRIVER);
            put(HIBERNATE_CONNECTION_USERNAME, EMPTY);
            put(HIBERNATE_CONNECTION_PASSWORD, EMPTY);
            put(HIBERNATE_CONNECTION_POOL_SIZE, String.valueOf(SQLITE_CONNECTION_POOL_SIZE));
            put(HIBERNATE_CONNECTION_RELEASE_MODE, RELEASE_MODE_AFTER_TRANSACTION);
            put(HIBERNATE_CURRENT_SESSION_CONTEXT, THREAD_LOCAL_SESSION_CONTEXT);
        }
    };
    private final ReentrantLock lock = new ReentrantLock();
    private SessionFactory sessionFactory;

    protected SessionFactory getSessionFactory() {
        if (this.sessionFactory == null) {
            lock.lock();
            try {
                if (this.sessionFactory == null) {
                    this.sessionFactory = createSessionFactory(null);
                }
            } finally {
                lock.unlock();
            }

        }
        return this.sessionFactory;
    }

    private SessionFactory createSessionFactory(Properties properties) {
        Configuration cfg = new Configuration()
                .addAnnotatedClass(Identifier.class);

        if (properties != null) {
            cfg.mergeProperties(properties);
        }
        cfg.mergeProperties(DEFAULT_PROPERTIES);
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(cfg.getProperties()).buildServiceRegistry();
        return cfg.buildSessionFactory(serviceRegistry);
    }

    public Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            lock.lock();
            try {
                if (this.sessionFactory != null) {
                    try {
                        this.sessionFactory.close();
                        log.info("Connection provider closed successfully!");
                    } catch (HibernateException he) {
                        log.error("Error while closing connection provider!", he);
                    }
                }
            } finally {
                lock.unlock();
            }
        } finally {
            super.finalize();
        }
    }

    public void initialize(Properties properties) throws ConfigurationException {
        lock.lock();
        try {
            this.sessionFactory = createSessionFactory(properties);
        } finally {
            lock.unlock();
        }
    }

}
