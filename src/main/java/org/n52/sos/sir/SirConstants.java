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

/**
 * @author Christian Autermann <c.autermann@52north.org>
 */
public interface SirConstants {
    String UTF8 = "UTF-8";
    String NS_SIR_PREFIX = "sir";
    String NS_SIR = "http://swsl.uni-muenster.de/sir";
    String SIR_SERVICE_VERSION = "0.3.1";
    String SIR_SERVICE_TYPE = "SIR";
    String AN_SERVICE = "service";
    String AN_VERSION = "version";
    String EN_INSERT_SENSOR_INFO_REQUEST = "InsertSensorInfoRequest";
    String EN_INFO_TO_BE_INSERTED = "InfoToBeInserted";
    String EN_SERVICE_REFERENCE = "ServiceReference";
    String EN_SERVICE_URL = "ServiceURL";
    String EN_SERVICE_TYPE = "ServiceType";
    String EN_SERVICE_SPECIFIC_SENSOR_ID = "ServiceSpecificSensorID";
    String EN_SENSOR_DESCRIPTION_TO_BE_UPDATED = "SensorDescriptionToBeUpdated";
    String EN_SENSOR_IDENTIFICATION = "SensorIdentification";
    String EN_SENSOR_ID_IN_SIR = "SensorIDInSIR";
    String EN_UPDATE_SENSOR_DESCRIPTION_REQUEST = "UpdateSensorDescriptionRequest";
    String EN_DELETE_SENSOR_INFO_REQUEST = "DeleteSensorInfoRequest";
    String EN_INFO_TO_BE_DELETED = "InfoToBeDeleted";
    String EN_DELETE_SENSOR = "DeleteSensor";
}
