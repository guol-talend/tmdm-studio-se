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
package com.amalto.workbench.availablemodel;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IMenuManager;

import com.amalto.workbench.editors.DataClusterBrowserMainPage;
import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.editors.XObjectBrowser;
import com.amalto.workbench.editors.XObjectEditor;
import com.amalto.workbench.models.TreeObject;
import com.amalto.workbench.models.TreeParent;
import com.amalto.workbench.webservices.XtentisPort;

public class AbstractAvailableModel implements IAvailableModel {

    public void addTreeObjects(XtentisPort port, IProgressMonitor monitor, TreeParent serverRoot) {

    }

    public void fillContextMenu(TreeObject xobject, IMenuManager manager) {
        

    }

    public void addPage(TreeObject xobject, XObjectBrowser editor) {
        

    }

    public void addPage(TreeObject xobject, XObjectEditor editor) {
        

    }

    public void menuAboutToShow(IMenuManager manager, DataClusterBrowserMainPage page) {
        

    }

    public void fillContextMenu(Object obj, IMenuManager manager, DataModelMainPage page, String dataModelName) {
        

    }

    public void doubleClickOnElement(int type, DataModelMainPage page, String dataModelName) {
        

    }

    public void deleteTreeObject(XtentisPort port, TreeObject xobject) {
        

    }

    public void doImport(TreeObject obj, String importFolder) {

    }
}
