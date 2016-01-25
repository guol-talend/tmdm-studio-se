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

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.mdm.repository.core.IServerObjectRepositoryType;
import org.talend.mdm.repository.core.command.deploy.AbstractDeployCommand;
import org.talend.mdm.repository.core.command.param.ICommandParameter;
import org.talend.mdm.repository.core.service.ModelImpactAnalyseService;
import org.talend.mdm.repository.core.service.ModelImpactAnalyseService.ImpactOperation;
import org.talend.mdm.repository.i18n.Messages;
import org.talend.mdm.repository.model.mdmproperties.MDMServerObjectItem;
import org.talend.mdm.repository.model.mdmproperties.WSDataModelItem;
import org.talend.mdm.repository.model.mdmserverobject.MDMServerObject;
import org.talend.mdm.repository.utils.RepositoryResourceUtil;

import com.amalto.workbench.exadapter.ExAdapterManager;
import com.amalto.workbench.utils.EXtentisObjects;
import com.amalto.workbench.utils.TreeObjectUtil;
import com.amalto.workbench.utils.XtentisException;
import com.amalto.workbench.webservices.WSDataModel;
import com.amalto.workbench.webservices.WSDataModelPK;
import com.amalto.workbench.webservices.WSDeleteDataModel;
import com.amalto.workbench.webservices.WSPutDataModel;
import com.amalto.workbench.webservices.XtentisPort;

/**
 * DOC hbhong class global comment. Detailled comment
 */
public class DataModelInteractiveHandler extends AbstractInteractiveHandler {

    /**
     * 
     */
    private static final String ENCODE = "UTF-8"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String FILE_EXTENSION = "xsd"; //$NON-NLS-1$

    Logger log = Logger.getLogger(DataModelInteractiveHandler.class);

    private IDataModelInteractiveHandlerExAdapter exAdapter;

    public DataModelInteractiveHandler() {
        this.exAdapter = ExAdapterManager.getAdapter(this, IDataModelInteractiveHandlerExAdapter.class);
    }

    public ERepositoryObjectType getRepositoryObjectType() {
        return IServerObjectRepositoryType.TYPE_DATAMODEL;
    }

    public String getLabel() {

        return Messages.DataModelInteractiveHandler_label;
    }

    @Override
    public boolean doDeployWSObject(XtentisPort port, Object wsObj) {
        if (wsObj != null) {
            port.putDataModel(new WSPutDataModel((WSDataModel) wsObj));
            return true;
        }
        return false;
    }

    @Override
    public Object convert(Item item, MDMServerObject serverObj) {
        WSDataModel dataModel = (WSDataModel) super.convert(item, serverObj);
        IFile file = RepositoryResourceUtil.findReferenceFile(getRepositoryObjectType(), item, FILE_EXTENSION);
        String schema = RepositoryResourceUtil.getTextFileContent(file, ENCODE);
        dataModel.setXsdSchema(schema);
        return dataModel;
    }

    @Override
    public void assertPropertyIsInited(Item item) {
        IFile file = RepositoryResourceUtil.findReferenceFile(getRepositoryObjectType(), item, FILE_EXTENSION);
        String schema = RepositoryResourceUtil.getTextFileContent(file, ENCODE);
        ((WSDataModelItem) item).getWsDataModel().setXsdSchema(schema);
    }

    @Override
    public boolean doRemove(XtentisPort port, AbstractDeployCommand cmd) throws XtentisException {
        WSDataModelPK pk = new WSDataModelPK();
        String name = cmd.getObjName();
        pk.setPk(name);
        port.deleteDataModel(new WSDeleteDataModel(pk));
        TreeObjectUtil.deleteSpecificationFromAttachedRole(port, name, EXtentisObjects.DataMODEL.getName());
        return true;
    }

    @Override
    public List<IRepositoryViewObject> getAssociatedObjects(IRepositoryViewObject obj) {
        if (exAdapter != null) {
            return exAdapter.getAssociatedObjects(obj);
        }
        return super.getAssociatedObjects(obj);
    }

    @Override
    public boolean deploy(AbstractDeployCommand cmd) throws XtentisException {
        IRepositoryViewObject viewObj = cmd.getViewObject();
        Item item = viewObj.getProperty().getItem();
        MDMServerObject serverObject = ((MDMServerObjectItem) item).getMDMServerObject();
        Object wsObj = convert(item, serverObject);
        //
        ICommandParameter parameter = cmd.getParameter();
        if (parameter != null) {
            callModelService(cmd);
            return true;
        } else {
            XtentisPort port = getPort(cmd.getServerDef());
            return doDeployWSObject(port, wsObj);
        }
    }

    private void callModelService(AbstractDeployCommand cmd) throws XtentisException {
        ICommandParameter parameter = cmd.getParameter();
        if (parameter != null) {
            ImpactOperation operation = (ImpactOperation) parameter.getParameter();
            Boolean force = null;
            if (operation == ImpactOperation.APPLY_LOW_CHANGE) {
                force = false;
            } else if (operation == ImpactOperation.RECREATE_TABLE) {
                force = true;
            }

            ModelImpactAnalyseService.updateModel(cmd.getServerDef(), cmd.getViewObject(), force);

        }
    }
}
