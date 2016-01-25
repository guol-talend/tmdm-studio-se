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
package org.talend.mdm.repository.core.validate.datamodel.validator;

/**
 * created by HHB on 2013-1-11 Detailled comment
 * 
 */
public class ValidatorRegistry {

    private ValidatorRegistry() {
    }

    private static ValidatorRegistry instance = new ValidatorRegistry();

    /**
     * Getter for instance.
     * 
     * @return the instance
     */
    public static ValidatorRegistry getInstance() {
        return instance;
    }

}
