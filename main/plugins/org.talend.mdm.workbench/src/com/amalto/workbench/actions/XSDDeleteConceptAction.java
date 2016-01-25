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
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.xsd.XSDElementDeclaration;

import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.i18n.Messages;
import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;
import com.amalto.workbench.providers.ISchemaContentProvider;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.utils.XtentisException;
import com.amalto.workbench.webservices.XtentisPort;

public class XSDDeleteConceptAction extends UndoAction {

    private static Log log = LogFactory.getLog(XSDDeleteConceptAction.class);

    // private DataModelMainPage page = null;
    private XSDElementDeclaration xsdElem = null;

    public XSDDeleteConceptAction(DataModelMainPage page) {
        super(page);
        // this.page = page;
        setImageDescriptor(ImageCache.getImage(EImage.DELETE_OBJ.getPath()));
        setText(Messages.XSDDeleteConceptAction_Text);
        setToolTipText(Messages.XSDDeleteConceptAction_ActionTip);
    }

    @Override
    public void run(Object toDel) {
        if (!(toDel instanceof XSDElementDeclaration)) {
            return;
        }
        xsdElem = (XSDElementDeclaration) toDel;
        super.run();
    }

    @Override
    public IStatus doAction() {
        try {
            // xsdElem is to support the multiple delete action on key press,
            // which each delete action on concept must be explicit passed a xsdElem to
            // delete
            XSDElementDeclaration decl = xsdElem;
            if (decl == null) {
                ISelection selection = page.getTreeViewer().getSelection();
                decl = (XSDElementDeclaration) ((IStructuredSelection) selection).getFirstElement();
            }

            // check if contains fk
            if (checkContainFK(decl.getName())) {
                boolean confirmed = MessageDialog.openConfirm(page.getSite().getShell(), Messages.XSDDeleteConceptAction_ConfirmDel,
                        Messages.bind(Messages.XSDDeleteConceptAction_ConfirmInfo, decl.getName()));
                if (!confirmed) {
                    return Status.CANCEL_STATUS;
                }
            }

            // check if refered by
            boolean isReferenced = isCommonReferedBy(decl);
            if (isReferenced) {
                boolean confirmed = MessageDialog.openConfirm(page.getSite().getShell(),
                        Messages.XSDDeleteConceptAction_ConfirmDel,
                        Messages.bind(Messages.XSDDeleteConceptAction_ConfirmReferInfo, decl.getName()));
                if (!confirmed) {
                    return Status.CANCEL_STATUS;
                }
            }
            if (schema == null)
                schema = ((ISchemaContentProvider) page.getTreeViewer().getContentProvider()).getXsdSchema();
            schema.getContents().remove(decl);

            schema.update();
            xsdElem = null;
            page.refresh();
            page.markDirtyWithoutCommit();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
			if (!Util.handleConnectionException(page.getSite().getShell(), e, Messages.XSDDeleteConceptAction_ErrorMsg)) {
				MessageDialog.openError(page.getSite().getShell(),
						Messages._Error, Messages.bind(
								Messages.XSDDeleteConceptAction_ErrorMsg,
								e.getLocalizedMessage()));
			}
            return Status.CANCEL_STATUS;
        }
        return Status.OK_STATUS;
    }

    private boolean isCommonReferedBy(XSDElementDeclaration decl) {
        List<Object> objList = new ArrayList<Object>();
        IStructuredContentProvider elementContentProvider = (IStructuredContentProvider) page.getTreeViewer()
                .getContentProvider();
        Object[] objs = Util.getAllObject(page.getSite(), objList, elementContentProvider);

        objList.clear();
        IStructuredContentProvider typeContentProvider = (IStructuredContentProvider) page.getTypesViewer().getContentProvider();
        Object[] typeElems = Util.getAllObject(page.getSite(), objList, typeContentProvider);

        boolean referencedBy = Util.isReferencedBy(decl, objs) || Util.isReferencedBy(decl, typeElems);

        return referencedBy;
    }

    protected boolean checkContainFK(String fkName) throws Exception {
        // add by ymli. fix buy 0010029
        Set<String> list = Util.getForeignKeys();
        if (list == null) {
            XtentisPort port = null;
            try {
                port = Util.getPort(page.getXObject());
            } catch (XtentisException e) {
                log.error(e.getMessage(), e);
            }
            list = new HashSet<String>();
            Util.getForeingKeyInDataModel(list, page.getXObject().getParent(), port);
            Util.setForeignKeys(list);
        }
        return list.contains(fkName);
    }

    public void setXSDTODel(XSDElementDeclaration elem) {
        xsdElem = elem;
    }
}
