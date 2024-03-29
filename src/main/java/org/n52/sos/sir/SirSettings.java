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
package org.n52.sos.sir;

import java.util.Collections;
import java.util.Set;

import org.n52.sos.config.SettingDefinition;
import org.n52.sos.config.SettingDefinitionGroup;
import org.n52.sos.config.SettingDefinitionProvider;
import org.n52.sos.config.settings.UriSettingDefinition;


/**
 * TODO JavaDoc
 * @author Christian Autermann <c.autermann@52north.org>
 */
public class SirSettings implements SettingDefinitionProvider {

    public static final String SERVICE_URL = "sir.serviceUrl";
    public static final SettingDefinitionGroup GROUP = new SettingDefinitionGroup().setTitle("SIR");
    public static final UriSettingDefinition SERVICE_URL_DEFINITION = new UriSettingDefinition()
            .setTitle("SIR Service URL")
            .setDescription("The URL of the SIR instance.")
            .setDefaultValue(null)
            .setOptional(true).setKey(SERVICE_URL).setGroup(GROUP);

    public Set<SettingDefinition<?, ?>> getSettingDefinitions() {
        return Collections.<SettingDefinition<?, ?>>singleton(SERVICE_URL_DEFINITION);
    }
}
