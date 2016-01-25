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
package org.talend.mdm.repository.ui.markers.datamodel;

import org.eclipse.core.resources.IMarker;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.markers.MarkerItem;

/**
 * created by HHB on 2013-1-15 Detailled comment
 * 
 */
public class ModelField extends AbstractDataModelField {

    public static final String ID = "org.talend.mdm.repository.ui.markers.ModelField"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.markers.MarkerField#getValue(org.eclipse.ui.views.markers.MarkerItem)
     */
    @Override
    public String getValue(MarkerItem item) {
        IMarker marker = item.getMarker();
        return ModelMarkerHelper.getModelName(marker);
    }

    @Override
    public int getDefaultColumnWidth(Control control) {
        return 20 * getFontWidth(control);
    }
}
