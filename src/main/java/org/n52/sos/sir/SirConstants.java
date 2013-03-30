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
package org.n52.sos.sir;

import javax.xml.namespace.QName;

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public interface SirConstants {
    String UTF8 = "UTF-8";
    String NS_SIR_PREFIX = "sir";
    String NS_SIR = "http://52north.org/sir/0.3.2";
    String SIR_SERVICE_VERSION = "0.3.1";
    String SIR_SERVICE_TYPE = "SIR";
    String AN_SERVICE = "service";
    String AN_VERSION = "version";
    QName QN_INSERT_SENSOR_INFO_REQUEST = new QName(NS_SIR, "InsertSensorInfoRequest", NS_SIR_PREFIX);
    QName QN_INFO_TO_BE_INSERTED = new QName(NS_SIR, "InfoToBeInserted", NS_SIR_PREFIX);
    QName QN_SERVICE_REFERENCE = new QName(NS_SIR, "ServiceReference", NS_SIR_PREFIX);
    QName QN_SERVICE_URL = new QName(NS_SIR, "ServiceURL", NS_SIR_PREFIX);
    QName QN_SERVICE_TYPE = new QName(NS_SIR, "ServiceType", NS_SIR_PREFIX);
    QName QN_SERVICE_SPECIFIC_SENSOR_ID = new QName(NS_SIR, "ServiceSpecificSensorID", NS_SIR_PREFIX);
    QName QN_SENSOR_DESCRIPTION_TO_BE_UPDATED = new QName(NS_SIR, "SensorDescriptionToBeUpdated", NS_SIR_PREFIX);
    QName QN_SENSOR_IDENTIFICATION = new QName(NS_SIR, "SensorIdentification", NS_SIR_PREFIX);
    QName QN_SENSOR_ID_IN_SIR = new QName(NS_SIR, "SensorIDInSIR", NS_SIR_PREFIX);
    QName QN_UPDATE_SENSOR_DESCRIPTION_REQUEST = new QName(NS_SIR, "UpdateSensorDescriptionRequest", NS_SIR_PREFIX);
    QName QN_DELETE_SENSOR_INFO_REQUEST = new QName(NS_SIR, "DeleteSensorInfoRequest", NS_SIR_PREFIX);
    QName QN_INFO_TO_BE_DELETED = new QName(NS_SIR, "InfoToBeDeleted", NS_SIR_PREFIX);
    QName QN_DELETE_SENSOR = new QName(NS_SIR, "DeleteSensor", NS_SIR_PREFIX);
    QName QN_SENSOR_DESCRIPTION = new QName(NS_SIR, "SensorDescription", NS_SIR_PREFIX);
}
