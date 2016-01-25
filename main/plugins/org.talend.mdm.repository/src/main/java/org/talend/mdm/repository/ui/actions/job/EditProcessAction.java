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
package org.talend.mdm.repository.ui.actions.job;

import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.designer.core.ui.action.EditProcess;
import org.talend.mdm.repository.core.bridge.AbstractBridgeRepositoryAction;

/**
 * DOC hbhong class global comment. Detailled comment
 */
public class EditProcessAction extends AbstractBridgeRepositoryAction {

    private static class EditProcessAdapter extends EditProcess {

        public EditProcessAdapter() {
            super();
        }

        @Override
        public void refresh(Object obj) {
        }

    }

    /**
     * DOC hbhong EditProcessAction constructor comment.
     * 
     * @param cAction
     */
    public EditProcessAction() {
        super(new EditProcessAdapter());
    }

    @Override
    public String getGroupName() {
        return GROUP_EDIT;
    }

    @Override
    public boolean isVisible(IRepositoryViewObject viewObj) {
        if (getSelectedObject().size() > 1) {
            return false;
        }

        return super.isVisible(viewObj);
    }
}
