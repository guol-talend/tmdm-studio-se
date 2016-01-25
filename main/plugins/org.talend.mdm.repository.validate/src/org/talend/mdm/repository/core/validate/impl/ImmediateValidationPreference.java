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
package org.talend.mdm.repository.core.validate.impl;

import org.eclipse.wst.validation.internal.ValidationResultSummary;
import org.talend.mdm.repository.core.service.IModelValidationService;
import org.talend.mdm.repository.core.validate.IValidationPreference;
import org.talend.mdm.repository.core.validate.ValidationPreferenceService;
import org.talend.mdm.repository.ui.preferences.IValidationPerferenceConstant;

/**
 * created by HHB on 2013-1-31 Detailled comment
 * 
 */
public class ImmediateValidationPreference implements IValidationPreference, IValidationPerferenceConstant {

    ValidationPreferenceService service = ValidationPreferenceService.getInstance();

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.mdm.repository.core.validate.IValidationPreference#shouldShowResults()
     */
    @Override
    public boolean shouldShowResults(ValidationResultSummary result) {

        return service.isShowDlgAfterImmediateChecking();

    }

    @Override
    public void updateLastSelectedBun(int selectedBun, ValidationResultSummary resultSummary) {
        // do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.mdm.repository.core.validate.IValidationPreference#setShowResults(java.lang.Boolean)
     */
    @Override
    public void setShowResults(Boolean showing, ValidationResultSummary resultSummary) {
        service.setShowDlgAfterImmediateChecking(showing);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.mdm.repository.core.validate.IValidationPreference#getValidationCondition()
     */
    @Override
    public int getValidationCondition() {
        return IModelValidationService.VALIDATE_IMMEDIATE;
    }

}
