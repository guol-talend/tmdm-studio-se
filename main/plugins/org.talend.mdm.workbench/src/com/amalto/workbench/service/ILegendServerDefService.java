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
package com.amalto.workbench.service;

import java.util.List;

import org.talend.core.IService;

import com.amalto.workbench.utils.MDMServerDef;

/**
 * DOC hbhong class global comment. Detailled comment
 */
public interface ILegendServerDefService extends IService {

    public List<MDMServerDef> getLegendServerDefs();

    public boolean checkServerDefConnection(String endpointaddress, String username, String password, String universe);
}
