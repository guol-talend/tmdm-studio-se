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
package com.amalto.workbench.dialogs;

import org.eclipse.swt.widgets.Composite;

import com.amalto.workbench.exadapter.IExAdapter;

/**
 * created by HHB on 2014-1-8 Detailled comment
 * 
 */
public interface ISelectImportedModulesDialogExAdapter extends IExAdapter<SelectImportedModulesDialog> {

    void createDialogArea(Composite compositeBtn);
}
