// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.repository.core.validate;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.PlatformUI;
import org.talend.mdm.repository.ui.preferences.IValidationPerferenceConstant;

/**
 * created by HHB on 2013-2-1 Detailled comment
 * 
 */
public class ValidationPreferenceService implements IValidationPerferenceConstant {

    private static ValidationPreferenceService instance = new ValidationPreferenceService();

    /**
     * Getter for instance.
     * 
     * @return the instance
     */
    public static ValidationPreferenceService getInstance() {
        return instance;
    }

    private ValidationPreferenceService() {
    }

    private IPreferenceStore preferenceStore;

    public boolean isShowDlgAfterImmediateChecking() {
        getPreferenceStore().setDefault(SHOW_RESULT_DIALOG_AFTER_IMMEDIATE, true);
        return getPreferenceStore().getBoolean(SHOW_RESULT_DIALOG_AFTER_IMMEDIATE);
    }

    public void setShowDlgAfterImmediateChecking(boolean isShowing) {
        getPreferenceStore().setValue(SHOW_RESULT_DIALOG_AFTER_IMMEDIATE, isShowing);
    }

    private IPreferenceStore getPreferenceStore() {
        if (preferenceStore == null) {
            preferenceStore = PlatformUI.getPreferenceStore();
        }
        return preferenceStore;
    }
}