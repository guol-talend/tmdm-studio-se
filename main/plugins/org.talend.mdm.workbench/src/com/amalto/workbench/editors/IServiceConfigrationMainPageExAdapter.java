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
package com.amalto.workbench.editors;

import com.amalto.workbench.exadapter.IExAdapter;
import com.amalto.workbench.webservices.WSServicePutConfiguration;
import com.amalto.workbench.webservices.XtentisPort;

/**
 * created by HHB on 2014-1-8 Detailled comment
 * 
 */
public interface IServiceConfigrationMainPageExAdapter extends IExAdapter<ServiceConfigrationMainPage> {

    void doSaveSVNChange(XtentisPort port, WSServicePutConfiguration ws, String serviceName, String configurationText);
}
