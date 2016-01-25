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
package org.talend.mdm.repository.core.service.interactive;

import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.mdm.repository.core.IServerObjectRepositoryType;
import org.talend.mdm.repository.core.command.deploy.AbstractDeployCommand;
import org.talend.mdm.repository.i18n.Messages;

import com.amalto.workbench.utils.EXtentisObjects;
import com.amalto.workbench.utils.TreeObjectUtil;
import com.amalto.workbench.utils.XtentisException;
import com.amalto.workbench.webservices.WSDeleteView;
import com.amalto.workbench.webservices.WSPutView;
import com.amalto.workbench.webservices.WSView;
import com.amalto.workbench.webservices.WSViewPK;
import com.amalto.workbench.webservices.XtentisPort;

/**
 * DOC hbhong class global comment. Detailled comment
 */
public class ViewInteractiveHandler extends AbstractInteractiveHandler {

    public ERepositoryObjectType getRepositoryObjectType() {
        return IServerObjectRepositoryType.TYPE_VIEW;
    }

    public String getLabel() {

        return Messages.ViewInteractiveHandler_label;
    }

    @Override
    public boolean doDeployWSObject(XtentisPort port, Object wsObj) {
        if (wsObj != null) {
            port.putView(new WSPutView((WSView) wsObj));
            return true;
        }
        return false;
    }

    @Override
    public boolean doRemove(XtentisPort port, AbstractDeployCommand cmd) throws XtentisException {
        WSViewPK pk = new WSViewPK();
        String name = cmd.getObjName();
        pk.setPk(name);
        port.deleteView(new WSDeleteView(pk));
        TreeObjectUtil.deleteSpecificationFromAttachedRole(port, name, EXtentisObjects.View.getName());
        return true;
    }

}
