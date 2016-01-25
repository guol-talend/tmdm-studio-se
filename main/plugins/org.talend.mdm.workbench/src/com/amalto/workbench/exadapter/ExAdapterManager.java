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
package com.amalto.workbench.exadapter;

import org.eclipse.core.runtime.Platform;

/**
 * created by HHB on 2014-1-3 Detailled comment
 * 
 */
public class ExAdapterManager {

    public static <T, E> E getAdapter(T adaptable, Class<E> cls) {
        Object adapter = Platform.getAdapterManager().getAdapter(adaptable, cls);
        if (adapter != null && adapter instanceof IExAdapter) {
            IExAdapter<T> extAdapter = (IExAdapter<T>) adapter;
            extAdapter.setAdaptable(adaptable);
            return (E) extAdapter;
        }
        return null;
    }
}
