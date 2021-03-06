// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.repository.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.talend.mdm.repository.ui.wizards.process.IMDMJobTemplate;

/**
 * DOC Administrator  class global comment. Detailled comment
 */
public class JobTemplateUtil {

    private static final String CLASS = "class"; //$NON-NLS-1$

    private static final String EXTENSION_POINT = "org.talend.mdm.repository.jobtemplateGenPage"; //$NON-NLS-1$

    private static Log log = LogFactory.getLog(JobTemplateUtil.class);

    public static List<IMDMJobTemplate> getJobTemplateGenPages() {
        try {
            IExtensionRegistry registry = Platform.getExtensionRegistry();
            IConfigurationElement[] configurationElements = registry.getConfigurationElementsFor(EXTENSION_POINT);
            List<IMDMJobTemplate> models = new ArrayList<IMDMJobTemplate>();
            for (int i = 0; i < configurationElements.length; i++) {
                IConfigurationElement element = configurationElements[i];

                IMDMJobTemplate modelcalss = (IMDMJobTemplate) element.createExecutableExtension(CLASS);
                models.add(modelcalss);
            }

            return models;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<IMDMJobTemplate>();
    }

}
