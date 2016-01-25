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

import com.amalto.workbench.exadapter.ExAdapterManager;
import com.amalto.workbench.webservices.XtentisPort;

public class TreeObjectUtil {

    private TreeObjectUtil() {

    }

    static {
        exAdapter = ExAdapterManager.getAdapter(new TreeObjectUtil(), ITreeObjectUtilExAdapter.class);
    }

    private static ITreeObjectUtilExAdapter exAdapter;

    public static void deleteSpecificationFromAttachedRole(XtentisPort port, String displayName, String objectType) {

        if (exAdapter != null) {
            exAdapter.deleteSpecificationFromAttachedRole(port, displayName, objectType);
        }
    }

}
