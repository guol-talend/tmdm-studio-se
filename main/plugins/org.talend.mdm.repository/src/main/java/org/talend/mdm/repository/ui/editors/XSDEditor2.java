// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.repository.ui.editors;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.wst.xsd.ui.internal.adapters.XSDSchemaAdapter;
import org.eclipse.xsd.XSDDiagnostic;
import org.eclipse.xsd.XSDSchema;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.mdm.repository.core.command.CommandManager;
import org.talend.mdm.repository.core.command.ICommand;
import org.talend.mdm.repository.core.service.DeployService;
import org.talend.mdm.repository.core.service.IModelValidationService;
import org.talend.mdm.repository.core.service.IModelValidationService.IModelValidateResult;
import org.talend.mdm.repository.i18n.Messages;
import org.talend.mdm.repository.model.mdmproperties.MDMServerObjectItem;
import org.talend.mdm.repository.model.mdmserverobject.MDMServerObject;

import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.editors.xsdeditor.XSDEditor;
import com.amalto.workbench.editors.xsdeditor.XSDSelectionListener;
import com.amalto.workbench.models.TreeObject;
import com.amalto.workbench.utils.CompositeViewersSelectionProvider;
import com.amalto.workbench.utils.Util;

/**
 * DOC hbhong class global comment. Detailled comment
 */
public class XSDEditor2 extends XSDEditor implements ISvnHistory {

    public static final String EDITOR_ID = "org.talend.mdm.repository.ui.editors.XSDEditor2"; //$NON-NLS-1$

    static Logger log = Logger.getLogger(XObjectEditor2.class);

    private DataModelMainPage2 dMainPage;

    private CTabFolder folder;

    @Override
    protected void createPages() {

        super.createPages();
        XSDEditorInput2 editorInput = (XSDEditorInput2) getEditorInput();
        TreeObject treeObject = editorInput.getTreeObject();
        IRepositoryViewEditorInput xobjectEditorinput = new XObjectEditorInput2(editorInput.getViewObject(), treeObject,
                treeObject.getDisplayName());
        xobjectEditorinput.setReadOnly(editorInput.isReadOnly());
        IFile xsdFile = null;
        try {
            xsdFile = getXSDFile(treeObject);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        dMainPage = new DataModelMainPage2(treeObject, xsdFile);
        try {
            addPage(dMainPage, xobjectEditorinput);
        } catch (PartInitException e) {
            log.error(e.getMessage(), e);
        }
        // add repository view object in selectionprovider
        IRepositoryViewObject repositoryViewObj = editorInput.getViewObject();// ContainerCacheService.get(editorInput.getInputItem().getProperty());
        CompositeViewersSelectionProvider selectionProvider = (CompositeViewersSelectionProvider) dMainPage
                .getSelectionProvider();
        selectionProvider.setRepositoryViewObj(repositoryViewObj);
        //
        getSite().setSelectionProvider(dMainPage.getSelectionProvider());

        // add XSDSelectionListener
        XSDSelectionListener xsdListener = new XSDSelectionListener(this, dMainPage);
        dMainPage.getTypesViewer().addSelectionChangedListener(xsdListener);
        dMainPage.getElementsViewer().addSelectionChangedListener(xsdListener);
        //

        setXSDInput(xobjectEditorinput);
        setXObject(treeObject);
        //
        folder = (CTabFolder) dMainPage.getMainControl().getParent();
        folder.getItem(2).setText(treeObject.getDisplayName() + " " + Util.getRevision(treeObject));//$NON-NLS-1$
        folder.getItem(0).setText(Messages.XSDEditor2_schemaDesign);
        folder.getItem(1).setText(Messages.XSDEditor2_schemaSource);
        // default use
        activePage(xsdFile);
        this.addPropertyListener(new IPropertyListener() {

            private boolean needValidate = false;

            public void propertyChanged(Object source, int propId) {
                if (propId == PROP_DIRTY) {
                    if (isDirty()) {
                        needValidate = true;

                    } else if (needValidate) {
                        needValidate = false;
                        XSDEditorInput2 editorInput = (XSDEditorInput2) getEditorInput();
                        IRepositoryViewObject viewObject = editorInput.getViewObject();
                        validateModel(viewObject);

                    }
                }

            }
        });
    }

    private void activePage(IFile xsdFile) {
        if (model != null) {
            Notifier target = ((XSDSchemaAdapter) model).getTarget();
            XSDSchema xs = (XSDSchema) target;
            xs.validate();
            EList<XSDDiagnostic> diagnostics = xs.getAllDiagnostics();
            if (!diagnostics.isEmpty()) {
                setActivePage(SOURCE_PAGE_INDEX);
                preActivePageIndex = SOURCE_PAGE_INDEX;
                return;
            }
        }
        setActivePage(2);
        preActivePageIndex = 2;
    }

    @Override
    protected IFile getXSDFile(TreeObject xobject) throws Exception {
        return ((IFileEditorInput) getEditorInput()).getFile();
    }

    @Override
    public boolean isLocalInput() {
        return true;
    }

    @Override
    public boolean isReadOnly() {
        IRepositoryViewEditorInput editorInput = (IRepositoryViewEditorInput) this.getEditorInput();
        return editorInput.isReadOnly();
    }

    @Override
    public void setFocus() {
        super.setFocus();
        if (folder != null) {
            folder.setFocus();
        }
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        super.doSave(monitor);
        XSDEditorInput2 editorInput = (XSDEditorInput2) getEditorInput();
        IRepositoryViewObject viewObject = editorInput.getViewObject();
        Item item = viewObject.getProperty().getItem();
        MDMServerObject serverObject = ((MDMServerObjectItem) item).getMDMServerObject();

        //
        DeployService deployService = DeployService.getInstance();
        if (deployService.isAutoDeploy()) {
            deployService.autoDeploy(getSite().getShell(), viewObject);
        } else if (serverObject.getLastServerDef() != null) {
            CommandManager.getInstance().pushCommand(ICommand.CMD_MODIFY, viewObject);
        }

    }

    private int validateModel(IRepositoryViewObject viewObject) {
        IModelValidationService service = (IModelValidationService) GlobalServiceRegister.getDefault().getService(
                IModelValidationService.class);
        List<IRepositoryViewObject> viewObjs = new ArrayList<IRepositoryViewObject>();
        viewObjs.add(viewObject);
        IModelValidateResult validateResult = service.validate(viewObjs, IModelValidationService.VALIDATE_AFTER_SAVE);
        if (validateResult != null) {
            return validateResult.getSelectedButton();
        }
        return IModelValidationService.BUTTON_CANCEL;
    }

    @Override
    public DataModelMainPage getdMainPage() {
        return dMainPage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.amalto.workbench.editors.xsdeditor.XSDEditor#getAdapter(java.lang.Class)
     */
    @Override
    public Object getAdapter(Class type) {

        if (type == IGotoMarker.class) {

            return dMainPage;
        }
        return super.getAdapter(type);
    }

}