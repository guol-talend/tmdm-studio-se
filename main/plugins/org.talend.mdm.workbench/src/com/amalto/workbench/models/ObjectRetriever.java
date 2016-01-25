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
package com.amalto.workbench.models;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.amalto.workbench.availablemodel.AvailableModelUtil;
import com.amalto.workbench.availablemodel.IAvailableModel;
import com.amalto.workbench.i18n.Messages;
import com.amalto.workbench.utils.IConstants;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.utils.XtentisException;
import com.amalto.workbench.webservices.XtentisPort;

public class ObjectRetriever implements IRunnableWithProgress {

    private static Log log = LogFactory.getLog(ObjectRetriever.class);

    TreeParent parentObject = null;

    private String endpointaddress;

    private String username;

    private String password;

    private String universe;

    private String objectName = "";//$NON-NLS-1$

    /**
     * @author ymli refresh the parentObject
     * @param parentObject
     * @param endpointaddress
     * @param username
     * @param password
     * @param universe
     */
    public ObjectRetriever(TreeParent parentObject, String endpointaddress, String username, String password, String universe) {
        super();
        this.parentObject = parentObject;
        this.endpointaddress = endpointaddress;
        this.username = username;
        this.password = password;
        this.universe = universe;
    }

    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        monitor.beginTask(Messages.bind(Messages.ObjectRetriever_TaskName, IConstants.TALEND), "admin".equals(username) ? 12 : 9);//$NON-NLS-1$
        // Access to server and get port

        try {
            switch (parentObject.getType()) {
            case TreeObject.WORKFLOW:
                objectName = "WorkflowAvailableModel";//$NON-NLS-1$
                break;
            case TreeObject.JOB_REGISTRY:
                objectName = "JobAvailableModel";//$NON-NLS-1$
                break;
            }
            XtentisPort port = Util.getPort(new URL(endpointaddress), universe, username, password);
            // port.ping(new WSPing("Hello MDM!"));
            // monitor.worked(1);

            // WSUniverse wUuniverse = null;
            // wUuniverse = port.getCurrentUniverse(new WSGetCurrentUniverse());
            // monitor.subTask("Accessing server....");
            //
            // UserInfo user = new UserInfo();
            // user.setUsername(username);
            // user.setPassword(password);
            // user.setServerUrl(endpointaddress);
            // user.setUniverse(universe);
            // user.setWsUuniverse(wUuniverse);
            // parentObject.setUser(user);

            // commented this by jsxie to fix bug 21371
            // parentObject.getServerRoot().removeChildFromUI(parentObject);
            List<IAvailableModel> availablemodels = AvailableModelUtil.getAvailableModels();
            for (IAvailableModel model : availablemodels) {
                if (model.toString().indexOf(objectName) > 0) {
                    model.addTreeObjects(port, monitor, parentObject.getServerRoot());
                }
            }

        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
        } catch (XtentisException e) {
            log.error(e.getMessage(), e);
        }

    }

}
