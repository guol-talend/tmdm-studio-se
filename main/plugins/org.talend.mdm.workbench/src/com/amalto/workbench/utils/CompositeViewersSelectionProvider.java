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
package com.amalto.workbench.utils;
import java.util.Collection;

import org.eclipse.jface.viewers.Viewer;


public class CompositeViewersSelectionProvider extends CompositeSelectionProvider {

    public CompositeViewersSelectionProvider(Viewer[] viewers) {
        for (Viewer eachViewer : viewers) {
            eachViewer.addSelectionChangedListener(this);
        }
    }

    public CompositeViewersSelectionProvider(Collection<Viewer> viewers) {
        this(viewers.toArray(new Viewer[viewers.size()]));
    }
}
