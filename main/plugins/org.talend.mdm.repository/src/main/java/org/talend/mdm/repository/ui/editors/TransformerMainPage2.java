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
package org.talend.mdm.repository.ui.editors;

import java.util.LinkedList;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.mdm.repository.core.IServerObjectRepositoryType;
import org.talend.mdm.repository.core.command.CommandManager;
import org.talend.mdm.repository.core.command.CommandStack;
import org.talend.mdm.repository.core.command.ICommand;
import org.talend.mdm.repository.core.service.DeployService;
import org.talend.mdm.repository.core.service.RepositoryWebServiceAdapter;
import org.talend.mdm.repository.core.service.wsimpl.transformplugin.AbstractPluginDetail;
import org.talend.mdm.repository.i18n.Messages;
import org.talend.mdm.repository.model.mdmmetadata.MDMServerDef;
import org.talend.mdm.repository.ui.navigator.MDMRepositoryView;
import org.talend.mdm.repository.ui.widgets.xmleditor.infoholder.RepositoryExternalInfoHolder;
import org.talend.mdm.repository.utils.RepositoryResourceUtil;

import com.amalto.workbench.editors.TransformerMainPage;
import com.amalto.workbench.models.TreeObject;
import com.amalto.workbench.utils.XtentisException;
import com.amalto.workbench.webservices.WSTransformerPluginV2Details;
import com.amalto.workbench.webservices.WSTransformerPluginV2VariableDescriptor;
import com.amalto.workbench.webservices.WSTransformerV2;
import com.amalto.workbench.webservices.XtentisPort;
import com.amalto.workbench.widgets.xmleditor.infoholder.ExternalInfoHolder;

/**
 * DOC hbhong class global comment. Detailled comment
 */
public class TransformerMainPage2 extends TransformerMainPage {

    XObjectEditor2 editor2;

    /**
     * DOC hbhong TransformerMainPage2 constructor comment.
     * 
     * @param editor
     */
    public TransformerMainPage2(FormEditor editor) {
        super(editor);
        this.editor2 = (XObjectEditor2) editor;
    }

    @Override
    protected void initExternalInfoHolder() {

        ExternalInfoHolder<?> allJobInfosHolder = RepositoryExternalInfoHolder.getAllJobInfosHolder(null);
        ExternalInfoHolder<?> mdmServerInfoHolder = RepositoryExternalInfoHolder.getAllMDMServerInfoHolder2(null);
        ExternalInfoHolder<?> allVarCandidatesHolder = RepositoryExternalInfoHolder
                .getProcessAllCallJobVarsCandidatesHolder((WSTransformerV2) getXObject().getWsObject());
        ExternalInfoHolder<?> workflowInfoHolder = RepositoryExternalInfoHolder.getAllWorkflowInfoHolder(null);
        ExternalInfoHolder<?> allDataModelHolderProxy = RepositoryExternalInfoHolder.getAllDataModelInfoHolderProxy(getXObject());

        initExternalInfoHolderForEachType("callJob", new ExternalInfoHolder<?>[] { allJobInfosHolder, mdmServerInfoHolder, //$NON-NLS-1$
                allVarCandidatesHolder });
        initExternalInfoHolderForEachType("workflowtrigger", new ExternalInfoHolder<?>[] { workflowInfoHolder, //$NON-NLS-1$
                allDataModelHolderProxy });

        initExternalInfoHolderForEachType("workflowcontexttrigger", new ExternalInfoHolder<?>[] { workflowInfoHolder, //$NON-NLS-1$
                allDataModelHolderProxy });
    }

    @Override
    protected void initTransformer() throws XtentisException {
        TreeObject xObject = getXObject();
        transformer = (WSTransformerV2) xObject.getWsObject();
    }

    @Override
    protected void initPlugin() {

        for (AbstractPluginDetail detail : RepositoryWebServiceAdapter.findAllTransformerPluginV2Details()) {

            String jndi = detail.getJNDIName();
            pluginsCombo.add(jndi);
            pluginDescriptions.put(jndi, detail.getDescription());
            // add input variables and output variables
            java.util.List<String> input = new LinkedList<String>();
            for (WSTransformerPluginV2VariableDescriptor v : detail.getInputVariableDescriptors()) {
                input.add(v.getVariableName());
            }
            inputVariablesMap.put(jndi, input);
            //
            java.util.List<String> output = new LinkedList<String>();
            for (WSTransformerPluginV2VariableDescriptor v : detail.getOutputVariableDescriptors()) {
                output.add(v.getVariableName());
            }
            outputVariablesMap.put(jndi, output);
        }

    }

    @Override
    protected void executeProcess(FormToolkit toolkit) {
        // if it is deployed before its execution
        if (getLastServerDef() == null) {
            MessageDialog.openWarning(null, Messages.Warning_text, Messages.RepositoryWebServiceAdapter_DeployFirst);
            return;
        }

        super.executeProcess(toolkit);
    }

    @Override
    protected void performSave() {
        if (editor2.isDirty()) {
            boolean isConfirmedToDeploy = openConfirmDialog();

            if (isConfirmedToDeploy) {
                editor2.doSave(new NullProgressMonitor());

                deployAndRefresh();
            }
        } else if (isReferedViewObjModified()) {
            boolean isConfirmedToDeploy = openConfirmDialog();
            if (isConfirmedToDeploy) {
                deployAndRefresh();
            }
        }
    }

    private void deployAndRefresh() {
        DeployService deployService = DeployService.getInstance();
        if (!deployService.isAutoDeploy()) {
            editor2.autoDeployProcess(deployService);

            // refresh after deploy
            MDMRepositoryView view = MDMRepositoryView.show();
            if (view != null) {
                view.refreshRootNode(IServerObjectRepositoryType.TYPE_TRANSFORMERV2);
            }
        }
    }

    private boolean openConfirmDialog() {
        String msg = null;
        if (editor2.isDirty()) {
            msg = Messages.TransformerMainPage_ConfirmContent;
        } else {
            msg = Messages.TransformerMainPage_ConfirmContent1;
        }

        return MessageDialog.openConfirm(getSite().getShell(), Messages.TransformerMainPage_ConfirmTitle, msg);
    }

    @Override
    protected XtentisPort getPort() {
        return RepositoryWebServiceAdapter.getXtentisPort(getSite().getShell(), getLastServerDef());
    }

    @Override
    protected WSTransformerPluginV2Details getWsTransformerPluginV2Details(String jndi) {
        return RepositoryWebServiceAdapter.findTransformerPluginV2Detail(jndi);
    }

    private MDMServerDef getLastServerDef() {
        XObjectEditorInput2 input = (XObjectEditorInput2) editor2.getEditorInput();
        Item item = input.getInputItem();
        MDMServerDef lastServerDef = RepositoryResourceUtil.getLastServerDef(item);
        return lastServerDef;
    }

    private boolean isReferedViewObjModified() {
        IEditorInput editorInput = editor2.getEditorInput();
        if (editorInput instanceof XObjectEditorInput2) {
            XObjectEditorInput2 editorInput2 = (XObjectEditorInput2) editorInput;
            IRepositoryViewObject viewObj = editorInput2.getViewObject();
            CommandStack stack = CommandManager.getInstance().findCommandStack(viewObj.getId());
            if (stack != null) {
                ICommand command = stack.getValidDeployCommand();
                if (command.getCommandType() == ICommand.CMD_MODIFY) {
                    return true;
                }
            }
        }

        return false;
    }
}
