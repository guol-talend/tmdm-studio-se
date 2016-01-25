// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.mdm.workbench.serverexplorer.ui.views;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.UIJob;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.IService;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.mdm.repository.model.mdmmetadata.MDMServerDef;
import org.talend.mdm.repository.model.mdmproperties.MDMServerDefItem;
import org.talend.mdm.workbench.serverexplorer.core.ServerDefService;
import org.talend.mdm.workbench.serverexplorer.i18n.Messages;
import org.talend.mdm.workbench.serverexplorer.plugin.MDMServerExplorerPlugin;
import org.talend.mdm.workbench.serverexplorer.ui.actions.IEventMgrService;
import org.talend.mdm.workbench.serverexplorer.ui.actions.ShowServerConsoleAction;
import org.talend.mdm.workbench.serverexplorer.ui.dialogs.ServerDefDialog;
import org.talend.mdm.workbench.serverexplorer.ui.providers.ServerSorter;
import org.talend.mdm.workbench.serverexplorer.ui.providers.TreeContentProvider;
import org.talend.mdm.workbench.serverexplorer.ui.providers.ViewerLabelProvider;

import com.amalto.workbench.exadapter.ExAdapterManager;
import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;
import com.amalto.workbench.service.MissingJarsException;
import com.amalto.workbench.utils.XtentisException;

/**
 * DOC hbhong class global comment. Detailled comment <br/>
 * 
 */
public class ServerExplorer extends ViewPart {

    static final ImageDescriptor IMG_CHECK_CONNECT = MDMServerExplorerPlugin.imageDescriptorFromPlugin(
            MDMServerExplorerPlugin.PLUGIN_ID, "icons/client_network.png"); //$NON-NLS-1$

    static final ImageDescriptor IMG_EVENTMANAGER = MDMServerExplorerPlugin.imageDescriptorFromPlugin(
            MDMServerExplorerPlugin.PLUGIN_ID, "icons/sub_engine.png"); //$NON-NLS-1$

    static final ImageDescriptor IMG_REFRESH = MDMServerExplorerPlugin.imageDescriptorFromPlugin(
            MDMServerExplorerPlugin.PLUGIN_ID, "icons/refresh.gif"); //$NON-NLS-1$

    public static final String ID = "org.talend.mdm.workbench.serverexplorer.ui.views.ServerExplorer"; //$NON-NLS-1$

    private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

    private TreeViewer treeViewer;

    private Tree tree;

    private static final Log log = LogFactory.getLog(ServerExplorer.class);

    private AddServerDefAction addServerDefAction;

    private List<Action> allActions = new LinkedList<Action>();

    private EnableServerDefAction toEnableServerAction;

    private EnableServerDefAction toDisableServerAction;

    private IServerExplorerExAdapter exAdapter;

    public AddServerDefAction getAddServerDefAction() {
        return this.addServerDefAction;
    }

    public ServerExplorer() {
    }

    /**
     * Create contents of the view part.
     * 
     * @param parent
     */
    @Override
    public void createPartControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new FillLayout(SWT.HORIZONTAL));

        treeViewer = new TreeViewer(container, SWT.BORDER);
        treeViewer.setSorter(new ServerSorter());
        tree = treeViewer.getTree();
        tree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseDoubleClick(MouseEvent e) {
                IRepositoryViewObject viewObject = getCurSelectedViewObject();
                String name = null;
                if (viewObject != null) {
                    MDMServerDefItem serverDefItem = getMDMItem(viewObject);
                    name = serverDefItem.getServerDef().getName();
                }
                editServerDef();
            }
        });
        toolkit.paintBordersFor(tree);
        treeViewer.setContentProvider(new TreeContentProvider());
        treeViewer.setLabelProvider(new ViewerLabelProvider());

        initAdapter();

        initializeToolBar();
        initializeMenu();

        refreshServerDefs();

        reInputPassword();

    }

    private void initAdapter() {
        exAdapter = ExAdapterManager.getAdapter(this, IServerExplorerExAdapter.class);
    }

    private void reInputPassword() {
        List<IRepositoryViewObject> viewObjects = ServerDefService.getAllServerDefViewObjects();

        for (IRepositoryViewObject viewObj : viewObjects) {
            MDMServerDefItem serverDefItem = getMDMItem(viewObj);
            MDMServerDef serverDef = serverDefItem.getServerDef();
            if (serverDef.getPasswd().equals("")) { //$NON-NLS-1$
                MessageDialog.openInformation(null, Messages.ServerExplorer_WarningText,
                        Messages.bind(Messages.Reinput_Password, serverDef.getName()));
                editServerDef(viewObj);
            }
        }
    }

    @Override
    public void dispose() {
        toolkit.dispose();
        super.dispose();
    }

    /**
     * Initialize the toolbar.
     */
    private void initializeToolBar() {
        IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
    }

    /**
     * Initialize the menu.
     */
    private void initializeMenu() {
        initActions();

        // Adds root context menu
        final MenuManager menuManager = new MenuManager();
        menuManager.setRemoveAllWhenShown(true);
        menuManager.addMenuListener(getMenuListener());

        // addAllActions(menuManager);

        // Context
        Menu contextMenu = menuManager.createContextMenu(tree);

        // Publish it
        tree.setMenu(contextMenu);
        getSite().registerContextMenu(menuManager, treeViewer);
    }

    private void initActions() {
        addServerDefAction = new AddServerDefAction();
        allActions.add(addServerDefAction);
        allActions.add(new DeleteServerDefAction());
        allActions.add(new EditServerDefAction());
        allActions.add(new CheckConnectionAction());
        allActions.add(new EventManageAction());
        allActions.add(new RefreshServerCacheAction());

        ShowServerConsoleAction showServerConsoleAction = new ShowServerConsoleAction();
        showServerConsoleAction.initSelectionProvider(treeViewer);
        allActions.add(showServerConsoleAction);
        if (exAdapter != null) {
            exAdapter.initAction(allActions, treeViewer);
        }

        toEnableServerAction = new EnableServerDefAction(true);
        toDisableServerAction = new EnableServerDefAction(false);

    }

    private IMenuListener getMenuListener() {
        return new IMenuListener() {

            public void menuAboutToShow(IMenuManager manager) {
                ISelection selection = treeViewer.getSelection();
                boolean isEmpty = selection.isEmpty();
                if (isEmpty) {
                    manager.add(addServerDefAction);
                } else {
                    addAllActions(manager);
                    IRepositoryViewObject viewObject = getCurSelectedViewObject();
                    if (viewObject != null) {
                        MDMServerDefItem mdmItem = getMDMItem(viewObject);
                        if (mdmItem != null) {
                            MDMServerDef serverDef = mdmItem.getServerDef();
                            manager.add(new Separator());
                            if (serverDef.isEnabled()) {
                                manager.add(toDisableServerAction);
                            } else {
                                manager.add(toEnableServerAction);
                            }
                        }
                    }

                }
            }
        };
    }

    private void addAllActions(IMenuManager menuManager) {
        Iterator<Action> iterator = allActions.iterator();
        while (iterator.hasNext()) {
            menuManager.add(iterator.next());
        }
    }

    @Override
    public void setFocus() {
        if (treeViewer != null) {
            treeViewer.getTree().setFocus();
        }

        refreshServerDefs();
    }

    public void refreshServerDefs() {
        List<IRepositoryViewObject> viewObjects = ServerDefService.getAllServerDefViewObjects(true);
        if (viewObjects != null) {
            treeViewer.setInput(viewObjects);
        }
    }

    private MDMServerDefItem getMDMItem(IRepositoryViewObject viewObject) {
        if (viewObject != null) {
            return (MDMServerDefItem) (viewObject.getProperty().getItem());
        }
        return null;
    }

    private IRepositoryViewObject getCurSelectedViewObject() {
        ISelection selection = treeViewer.getSelection();
        if (!selection.isEmpty()) {
            Object firstElement = ((TreeSelection) selection).getFirstElement();
            return (IRepositoryViewObject) firstElement;
        }
        return null;
    }

    public class AddServerDefAction extends Action {

        public AddServerDefAction() {
            setImageDescriptor(ImageCache.getImage(EImage.ADD_OBJ.getPath()));
            setText(Messages.ServerExplorer_AddServer);
        }

        @Override
        public void run() {
            ServerDefDialog dialog = new ServerDefDialog(getViewSite().getShell(), null);
            if (dialog.open() == IDialogConstants.OK_ID) {
                MDMServerDef serverDef = dialog.getServerDef();
                String id = ServerDefService.createServerDef(serverDef);

                if (id != null) {
                    String tempPasswd = serverDef.getTempPasswd();
                    ServerDefService.updateTempPassword(id, tempPasswd);
                    refreshServerDefs();
                }
            }
        }
    }

    class RefreshServerCacheAction extends Action {

        public RefreshServerCacheAction() {
            setImageDescriptor(IMG_REFRESH);
            setText(Messages.ServerExplorer_RefreshServerCache);
        }

        @Override
        public void run() {
            UIJob refreshServerJob = new UIJob(Messages.ServerExplorer_RefreshServerCache) {

                @Override
                public IStatus runInUIThread(IProgressMonitor monitor) {

                    IRepositoryViewObject viewObject = getCurSelectedViewObject();
                    if (viewObject != null) {
                        MDMServerDefItem mdmItem = getMDMItem(viewObject);
                        if (mdmItem != null) {
                            MDMServerDef serverDef = mdmItem.getServerDef();
                            serverDef = serverDef.getDecryptedServerDef();
                            String returnMsg = null;
                            try {
                                ServerDefService.checkMDMConnection(serverDef);
                                returnMsg = ServerDefService.refreshServerCache(serverDef);
                            } catch (XtentisException e) {
                                returnMsg = Messages.ServerExplorer_ConnectSSLFailed;
                            } catch (MalformedURLException e) {
                                returnMsg = Messages.ServerExplorer_ConnectFailed;
                            }
                            MessageDialog.openInformation(getSite().getShell(), Messages.ServerExplorer_RefreshServerCache,
                                    returnMsg);

                        }
                    }

                    return Status.OK_STATUS;
                }
            };

            refreshServerJob.run(new NullProgressMonitor());
        }

    }

    class CheckConnectionAction extends Action {

        public CheckConnectionAction() {
            setImageDescriptor(IMG_CHECK_CONNECT);
            setText(Messages.ServerExplorer_CheckConnection);
        }

        @Override
        public void run() {
            IRepositoryViewObject viewObject = getCurSelectedViewObject();
            if (viewObject != null) {
                MDMServerDefItem mdmItem = getMDMItem(viewObject);
                if (mdmItem != null) {
                    MDMServerDef serverDef = mdmItem.getServerDef().getDecryptedServerDef();
                    try {
                        ServerDefService.checkMDMConnection(serverDef);
                        MessageDialog.openInformation(getSite().getShell(), Messages.ServerExplorer_CheckConnection,
                                Messages.ServerExplorer_ConnectSuccessful);
                    } catch (MissingJarsException e) {
                        return;
                    } catch (XtentisException e) {
                        MessageDialog.openError(getSite().getShell(), Messages.ServerExplorer_CheckConnection,
                                Messages.ServerExplorer_ConnectSSLFailed);
                    } catch (MalformedURLException e) {
                        MessageDialog.openError(getSite().getShell(), Messages.ServerExplorer_CheckConnection,
                                Messages.ServerExplorer_ConnectFailed);
                    }
                }
            }
        }
    }

    public class EventManageAction extends Action {

        public EventManageAction() {
            setImageDescriptor(IMG_EVENTMANAGER);
            setText(Messages.ServerExplorer_EventManager);
        }

        @Override
        public void run() {
            doOpenEventManagerAction();
        }

        private void doOpenEventManagerAction() {
            IService service = GlobalServiceRegister.getDefault().getService(IEventMgrService.class);
            if (service != null) {
                IEventMgrService mgr = (IEventMgrService) service;
                IRepositoryViewObject curSelectedViewObject = getCurSelectedViewObject();
                MDMServerDefItem item = (MDMServerDefItem) curSelectedViewObject.getProperty().getItem();

                mgr.run(item.getServerDef().getDecryptedServerDef());
            }
        }
    }

    class EditServerDefAction extends Action {

        public EditServerDefAction() {
            setImageDescriptor(ImageCache.getImage(EImage.EDIT_OBJ.getPath()));
            setText(Messages.ServerExplorer_EditServer);
        }

        @Override
        public void run() {
            IRepositoryViewObject viewObject = getCurSelectedViewObject();
            String name = null;
            if (viewObject != null) {
                MDMServerDefItem serverDefItem = getMDMItem(viewObject);
                name = serverDefItem.getServerDef().getName();
            }
            editServerDef();

        }
    }

    private void editServerDef() {
        IRepositoryViewObject viewObject = getCurSelectedViewObject();
        if (viewObject != null) {
            MDMServerDefItem mdmItem = getMDMItem(viewObject);
            if (mdmItem != null) {
                MDMServerDef serverDef = mdmItem.getServerDef();
                ServerDefDialog dialog = new ServerDefDialog(getViewSite().getShell(), serverDef);
                if (dialog.open() == IDialogConstants.OK_ID) {
                    String tempPasswd = serverDef.getTempPasswd();
                    ServerDefService.updateTempPassword(viewObject.getId(), tempPasswd);

                    boolean result = ServerDefService.saveServeDef(mdmItem);
                    if (result) {
                        refreshServerDefs();
                    }
                }
            }
        }
    }

    private void editServerDef(IRepositoryViewObject viewObject) {
        if (viewObject != null) {
            MDMServerDefItem mdmItem = getMDMItem(viewObject);
            if (mdmItem != null) {
                MDMServerDef serverDef = mdmItem.getServerDef();
                ServerDefDialog dialog = new ServerDefDialog(getViewSite().getShell(), serverDef);
                if (dialog.open() == IDialogConstants.OK_ID) {
                    String tempPasswd = serverDef.getTempPasswd();
                    ServerDefService.updateTempPassword(viewObject.getId(), tempPasswd);
                    boolean result = ServerDefService.saveServeDef(mdmItem);
                    if (result) {
                        refreshServerDefs();
                    }
                }
            }
        }
    }

    class DeleteServerDefAction extends Action {

        public DeleteServerDefAction() {
            setImageDescriptor(ImageCache.getImage(EImage.DELETE_OBJ.getPath()));
            setText(Messages.ServerExplorer_RemoveServer);
        }

        @Override
        public void run() {
            IRepositoryViewObject viewObject = getCurSelectedViewObject();
            if (viewObject != null) {
                MDMServerDefItem serverDefItem = getMDMItem(viewObject);
                if (MessageDialog.openQuestion(getViewSite().getShell(), Messages.ServerExplorer_RemoveServer,
                        Messages.bind(Messages.ServerExplorer_RemoveConfirm, serverDefItem.getServerDef().getName()))) {
                    boolean result = ServerDefService.deleteServerDef(viewObject);
                    if (result) {
                        refreshServerDefs();
                    }
                    //
                }
            }
        }
    }

    class EnableServerDefAction extends Action {

        private boolean toEnable;

        public EnableServerDefAction(boolean toEnable) {
            this.toEnable = toEnable;
            if (toEnable) {
                setText(Messages.ServerExplorer_Enable);
            } else {
                setText(Messages.ServerExplorer_Disable);
            }
        }

        @Override
        public void run() {
            IRepositoryViewObject viewObject = getCurSelectedViewObject();
            if (viewObject != null) {
                MDMServerDefItem serverDefItem = getMDMItem(viewObject);
                serverDefItem.getServerDef().setEnabled(toEnable);
                ServerDefService.saveServeDef(serverDefItem);
                refreshServerDefs();
            }
        }
    }
}
