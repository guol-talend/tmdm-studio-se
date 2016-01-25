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
package com.amalto.workbench.detailtabs.exception;

public class CommitValidationException extends Exception {

    private static final long serialVersionUID = -4360609696577298950L;

    public CommitValidationException(String msg, Exception reason) {
        super(msg, reason);
    }

    public CommitValidationException(String msg) {
        super(msg);
    }

}
