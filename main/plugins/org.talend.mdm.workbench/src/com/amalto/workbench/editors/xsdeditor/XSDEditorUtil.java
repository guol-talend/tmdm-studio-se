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
package com.amalto.workbench.editors.xsdeditor;

import java.io.ByteArrayInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.i18n.Messages;
import com.amalto.workbench.models.TreeObject;
import com.amalto.workbench.providers.XObjectEditorInput;
import com.amalto.workbench.utils.EXtentisObjects;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.views.MDMPerspective;
import com.amalto.workbench.webservices.WSDataModel;

public class XSDEditorUtil {

    private static Log log = LogFactory.getLog(XSDEditorUtil.class);

    private static String getXObjectPath(TreeObject xobject) {
        String name = xobject.getDisplayName().replace(" ", "");//$NON-NLS-1$//$NON-NLS-2$
        if (xobject.getParent().getDisplayName().startsWith(EXtentisObjects.DataMODEL.getDisplayName())) {
            // the datamodel root
            return xobject.getParent().getDisplayName().replace(" ", "") + "/" + name;//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        } else {
            return getXObjectPath(xobject.getParent()) + "/" + name;//$NON-NLS-1$
        }
    }

    public static IFile createFile(TreeObject xobject) throws Exception {
        WSDataModel wsDataModel = (WSDataModel) xobject.getWsObject();
        if(wsDataModel==null) return null;
        String filename = xobject.getDisplayName().replace(" ", "") + ".xsd";//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        String content = wsDataModel.getXsdSchema();

        IProject project = createProject(xobject);
        String path = getXObjectPath(xobject);
        int pos = path.indexOf('/');
        String folder = path.substring(0, pos);
        IFolder fold = project.getFolder(folder);
        if (!fold.exists()) {
            fold.create(true, true, null);
        }
        IFile file = fold.getFile(filename);

        if (!file.exists())
            file.create(new ByteArrayInputStream(content.getBytes("utf-8")), IFile.FORCE, null);//$NON-NLS-1$
        else
            sycFileContents(file, content);

        return file;
    }

    private static void sycFileContents(IFile file, String content) throws Exception {
        if (file.exists())
            file.setContents(new ByteArrayInputStream(content.getBytes("utf-8")), IFile.FORCE, new NullProgressMonitor());//$NON-NLS-1$
    }

    private static boolean isEditorOpened(TreeObject xobject) throws Exception {
        WSDataModel wsDataModel = (WSDataModel) xobject.getWsObject();

        String filename = xobject.getDisplayName().replace(" ", "") + ".xsd";//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        String content = wsDataModel.getXsdSchema();

        IProject project = createProject(xobject);
        String path = getXObjectPath(xobject);
        int pos = path.indexOf('/');
        String folder = path.substring(0, pos);
        IFolder fold = project.getFolder(folder);
        if (!fold.exists()) {
            fold.create(true, true, null);
        }
        IFile file = fold.getFile(filename);

        if (!file.exists())
            return true;
        return false;
    }

    public static IProject createProject(TreeObject xobject) {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        String projectname = xobject.getServerRoot().getDisplayName().trim().replace("://", "").replace("/", "").replace(" ", "")//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$//$NON-NLS-6$
                .replace(":", "");//$NON-NLS-1$//$NON-NLS-2$
        IProject prj = root.getProject(projectname);
        if (prj.exists()){
        	try {
				prj.open(null);
			} catch (CoreException e) {
				log.error(e.getMessage(), e);
			}
            return prj;
        }
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();        
        final IProjectDescription desc = workspace.newProjectDescription(projectname);
        desc.setNatureIds(new String[] { "org.talend.mdm.schema.nature" });//$NON-NLS-1$
        desc.setComment(Messages.XSDEditorUtil_Comment);
        try {
            prj.create(desc, null);
            prj.open(IResource.BACKGROUND_REFRESH, null);
        } catch (CoreException e) {
            log.error(e.getMessage(), e);
        }
        return prj;
    }

    public static void openDataModel(TreeObject xobject, boolean markdirty) throws Exception {

        IFile pathToTempFile = XSDEditorUtil.createFile(xobject);
        final XSDEditorInput input = new XSDEditorInput(pathToTempFile);
        final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
                .findEditor("com.amalto.workbench.editors.xsdeditor.XSDEditor");//$NON-NLS-1$
        if (activePage.findEditor(input) != null) {
            activePage.openEditor(input, desc.getId());
            return;
        }

        final XSDEditor part = (XSDEditor) activePage.openEditor(input, desc.getId());// org.eclipse.wst.xsd.ui.internal.editor.InternalXSDMultiPageEditor

        IEditorInput xobjectEditorinput = new XObjectEditorInput(xobject, xobject.getDisplayName());

        final DataModelMainPage dMainPage = new DataModelMainPage(xobject);
        part.addPage(dMainPage, xobjectEditorinput);

        part.getSite().setSelectionProvider(dMainPage.getSelectionProvider());

        // add XSDSelectionListener
        XSDSelectionListener xsdListener = new XSDSelectionListener(part, dMainPage);
        dMainPage.getTypesViewer().addSelectionChangedListener(xsdListener);
        dMainPage.getElementsViewer().addSelectionChangedListener(xsdListener);
       

        part.setXSDInput(xobjectEditorinput);
        part.setXObject(xobject);
        part.setActiveEditor(dMainPage);
        
        //can't add DataModelMainPage the 3rd page, see 0019663
        CTabFolder folder = (CTabFolder) dMainPage.getMainControl().getParent();
        folder.getItem(2).setText(xobject.getDisplayName() + " " + Util.getRevision(xobject));//$NON-NLS-1$
        folder.getItem(0).setText(Messages.XSDEditorUtil_SchemaDesign);
        folder.getItem(1).setText(Messages.XSDEditorUtil_SchemaSource);
        if (markdirty)
            dMainPage.markDirty();

        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(MDMPerspective.VIEWID_PROPERTYVIEW);

        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(MDMPerspective.VIEWID_OUTLINE);
    }
}
