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
package org.talend.mdm.repository.core.command.param;

import org.talend.mdm.repository.core.service.ModelImpactAnalyseService.ImpactOperation;

/**
 * created by HHB on 2014-3-11 Detailled comment
 * 
 */
public class DataModelCmdParam implements ICommandParameter {

    private ImpactOperation operation;

    public DataModelCmdParam(ImpactOperation operation) {
        this.operation = operation;
    }

    public Object getParameter() {
        return operation;
    }

}
