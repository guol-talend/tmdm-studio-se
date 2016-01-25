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
package org.talend.mdm.engines.client.ui.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.talend.commons.ui.swt.preferences.TableEditor;
import org.talend.mdm.engines.client.Activator;
import org.talend.mdm.engines.client.i18n.Messages;
import org.talend.mdm.engines.client.ui.wizards.MDMServerHelper;

/**
 * @deprecated DOC cantoine class global comment. Detailled comment <br/>
 * 
 * $Id: MDMServerEditor.java 2738 2007-04-27 13:12:27Z cantoine $
 * 
 */
@Deprecated
public class MDMServerEditor extends TableEditor {

    /**
     * DOC tguiu MDMServerEditor constructor comment.
     * 
     * @param name
     * @param labelText
     * @param parent
     */
    public MDMServerEditor(String name, String labelText, Composite parent) {
        super(name, labelText, parent);
    }

    @Override
    protected Table createTable(Composite parent) {

        Table contextTable = new Table(parent, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
        contextTable.setLinesVisible(true);
        contextTable.setHeaderVisible(true);

        TableColumn shortDescription = new TableColumn(contextTable, SWT.NONE);
        shortDescription.setText(Messages.MDMServerEditor_descriptionColumnTitle);
        shortDescription.setWidth(150);

        TableColumn host = new TableColumn(contextTable, SWT.NONE);
        host.setText(Messages.MDMServerEditor_hostColumnTitle);
        host.setWidth(150);

        TableColumn engineName = new TableColumn(contextTable, SWT.NONE);
        engineName.setText(Messages.MDMServerDialog_port);
        engineName.setWidth(150);

        return contextTable;
    }

    @Override
    protected IStructuredContentProvider createContentProvider() {
        return new IStructuredContentProvider() {

            public Object[] getElements(Object inputElement) {
                return ((List) inputElement).toArray();
            }

            public void dispose() {
            }

            public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            }

        };
    }

    @Override
    protected ITableLabelProvider createLabelProvider() {
        return new ITableLabelProvider() {

            public Image getColumnImage(Object element, int columnIndex) {
                return null;
            }

            public String getColumnText(Object element, int columnIndex) {
                String value = ((String) element);
                if (columnIndex == 0) {
                    return MDMServerHelper.getShortDescription(value);
                }
                if (columnIndex == 1) {
                    return MDMServerHelper.getHost(value);
                }
                if (columnIndex == 2) {
                    return MDMServerHelper.getPort(value);
                }
                throw new IllegalStateException();
            }

            public void addListener(ILabelProviderListener listener) {
            }

            public void dispose() {
            }

            public boolean isLabelProperty(Object element, String property) {
                return false;
            }

            public void removeListener(ILabelProviderListener listener) {
            }

        };
    }

    @Override
    protected String writeString(List<String> items) {
        return MDMServerHelper.writeString(items);
    }

    @Override
    protected String getNewInputObject() {
        Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
        MDMServerDialog dialog = new MDMServerDialog(shell, computeCodeList());
        if (dialog.open() == Window.OK) {
            return MDMServerHelper.getString(dialog.getShortDescription(), dialog.getHost(), dialog.getPort(), dialog.getLogin(),
                    dialog.getPassword());
        }
        return null;
    }

    @Override
    protected String getExistingInputObject(String obj) {
        Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
        // MDMServerDialog dialog = new MDMServerDialog(shell, computeCodeList(),
        // MDMServerHelper.getEngineName(obj),
        // MDMServerHelper.getShortDescription(obj), MDMServerHelper.getHost(obj),
        // MDMServerHelper.getPort(obj),
        // MDMServerHelper.getLogin(obj), MDMServerHelper.getPassword(obj), MDMServerHelper
        // .getApplicationContext(obj));
        MDMServerDialog dialog = new MDMServerDialog(shell, computeCodeList(), MDMServerHelper.getShortDescription(obj),
                MDMServerHelper.getHost(obj), MDMServerHelper.getPort(obj), MDMServerHelper.getLogin(obj),
                MDMServerHelper.getPassword(obj));

        if (dialog.open() == Window.OK) {
            return MDMServerHelper.getString(dialog.getShortDescription(), dialog.getHost(), dialog.getPort(), dialog.getLogin(),
                    dialog.getPassword());
        }
        return null;
    }

    /**
     * DOC tguiu Comment method "computeCodeList".
     * 
     * @return
     */
    private List computeCodeList() {
        List result = new ArrayList();
        for (String tmp : getList()) {
            result.add(MDMServerHelper.getShortDescription(tmp));
        }
        return result;
    }

    @Override
    protected List<String> readString(String stringList) {
        return MDMServerHelper.readString(stringList);
    }
}
