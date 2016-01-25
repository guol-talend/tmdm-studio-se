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
package com.amalto.workbench.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Event;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDIdentityConstraintDefinition;

import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.i18n.Messages;
import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.utils.inputvalidator.EditXSDEleDecNameValidator;

public class XSDEditElementAction extends UndoAction {

    private static Log log = LogFactory.getLog(XSDEditElementAction.class);

    public XSDEditElementAction(DataModelMainPage page) {
        super(page);
        setImageDescriptor(ImageCache.getImage(EImage.EDIT_OBJ.getPath()));
        setText(Messages.XSDEditElementAction_EditElement);
        setToolTipText(Messages.XSDEditElementAction_EditAElement);
    }

    @Override
    public IStatus doAction() {
        try {
            ISelection selection = page.getTreeViewer().getSelection();
            XSDElementDeclaration decl = (XSDElementDeclaration) ((IStructuredSelection) selection).getFirstElement();
            ArrayList<Object> objList = new ArrayList<Object>();
            IStructuredContentProvider provider = (IStructuredContentProvider) page.getTreeViewer().getContentProvider();
            String oldName = decl.getName();

            InputDialog id = new InputDialog(page.getSite().getShell(), Messages.XSDEditElementAction_EditElement, Messages.XSDEditElementAction_EnterNameForElement,
                    oldName, new EditXSDEleDecNameValidator(schema)
            // new IInputValidator() {
            // public String isValid(String newText) {
            // if ((newText==null) || "".equals(newText)) return "The Entity Name cannot be empty";
            // EList list = schema.getElementDeclarations();
            // for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            // XSDElementDeclaration d = (XSDElementDeclaration) iter.next();
            // if (d.getName().equals(newText)) return "This Entity already exists";
            // }
            // return null;
            // };
            // }
            );

            id.setBlockOnOpen(true);
            int ret = id.open();
            if (ret == Window.CANCEL) {
                return Status.CANCEL_STATUS;
            }

            Object[] objs = Util.getAllObject(page.getSite(), objList, provider);
            Object[] allForeignKeyRelatedInfos = Util.getAllForeignKeyRelatedInfos(page.getSite(), new ArrayList<Object>(),
                    provider, new HashSet<Object>());

            decl.setName(id.getValue());
            decl.updateElement();
            Util.updateReference(decl, objs, allForeignKeyRelatedInfos, oldName, id.getValue());
            // change unique key with new name of concept
            EList list = decl.getIdentityConstraintDefinitions();
            XSDIdentityConstraintDefinition toUpdate = null;
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                XSDIdentityConstraintDefinition icd = (XSDIdentityConstraintDefinition) iter.next();
                if (icd.getName().equals(oldName)) {
                    toUpdate = icd;
                    break;
                }
            }
            if (toUpdate != null) {
                toUpdate.setName(id.getValue());
                toUpdate.updateElement();
            }

            page.refresh();
            page.markDirty();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            MessageDialog.openError(page.getSite().getShell(), Messages._Error,
                    Messages.bind(Messages.XSDEditElementAction_ErrorEditElement, e.getLocalizedMessage()));
            return Status.CANCEL_STATUS;
        }
        return Status.OK_STATUS;
    }

    @Override
    public void runWithEvent(Event event) {
        super.runWithEvent(event);
    }
}
