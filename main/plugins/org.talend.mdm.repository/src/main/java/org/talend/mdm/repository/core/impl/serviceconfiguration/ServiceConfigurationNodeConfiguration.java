// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.mdm.repository.core.impl.serviceconfiguration;

import org.talend.mdm.repository.core.impl.RepositoryNodeConfigurationAdapter;

/**
 * DOC jsxie class global comment. Detailled comment <br/>
 * 
 */
public class ServiceConfigurationNodeConfiguration extends RepositoryNodeConfigurationAdapter {

    public ServiceConfigurationNodeConfiguration() {
        setResourceProvider(new ServiceConfigurationNodeResourceProvider());

        setActionProvider(new ServiceConfigurationActionProvider());
    }

    @Override
    protected void initLabelProvider() {
        setLabelProvider(new ServiceConfigurationLabelProvider());
    }

    @Override
    protected void initContentProvider() {
        setContentProvider(new ServiceConfigurationContentProvider());
    }
}
